package models;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import adapters.GridViewAdapter;
import plantsAPI.GetPlantInfo;
import plantsAPI.GetPlantLocations;
import plantsAPI.GetPlantNames;
import seniorproject.arboretumapp.R;

/**
 * Created by dquea on 4/6/2016.
 */
public class PlantMap {

    private static PlantMap mInstance = null;
    private HashMap<String, Plant> mPlants;
    private List<String> namesList;
    private HashMap<String, String> nameToCodeMap;
    private Set<String> favoritePlantsList;
    private List<GridTile> favTiles;
    private List<GridTile> nearTiles;
    private GridViewAdapter adapter;
    private HashMap<String, Integer> nearPlantsCount;
    private HashMap<String, Map<String, Feature>> plantsFeaturesMaps;



    private PlantMap(){
        this.mPlants=new HashMap<String, Plant>();
        this.nearPlantsCount = new HashMap<String, Integer>();
    }

    public static PlantMap getInstance(){
        if (mInstance == null) {
            mInstance = new PlantMap();
        }
        return mInstance;
    }

    public HashMap<String, Plant> getPlantMap(){

        return mPlants;
    }

    public Map<String, Map<String, Feature>> getPlantsFeaturesMaps(){
        if (this.plantsFeaturesMaps == null)
            this.plantsFeaturesMaps = new HashMap<String, Map<String, Feature>>();
        return this.plantsFeaturesMaps;
    }

    public void setFavoritePlantsList(Set<String> favs){
        this.favoritePlantsList = favs;
    }

    public List<GridTile> getFavTiles(){
        if(favTiles == null)
            favTiles = new ArrayList<GridTile>();
        return favTiles;
    }

    public void updateFavGridTiles(){
        Iterator<GridTile> it = favTiles.iterator();

        while(it.hasNext()){
            String tileCode = it.next().getPlantCode();
            if (!favoritePlantsList.contains(tileCode)){
                it.remove();
            }
        }
    }

    public void reloadSavedFavs(){
        this.favTiles = new ArrayList<GridTile>();
        for(String code: favoritePlantsList){
            favTiles.add(new GridTile(PlantMap.getInstance().getSciName(code), PlantMap.getInstance().getThumbnail(code), code));
        }
    }

    public Set<String> getFavoritePlantsList(){
        if (favoritePlantsList == null)
            favoritePlantsList = new HashSet<String>();
        return favoritePlantsList;
    }

    public List<String> getDisplayNamesList(){
        namesList = new ArrayList<String>();
        for( Plant p : mPlants.values()){
            if(!p.getComName().equals(""))
                namesList.add(p.getSciName() + " (" + p.getComName() + ")");
            else
                namesList.add(p.getSciName());
        }

        return namesList;
    }

    public HashMap<String, String> getNameToCodeMap(){
        nameToCodeMap = new HashMap<String, String>();
        for( Plant p : mPlants.values()){
            nameToCodeMap.put(p.getSciName() + " (" + p.getComName() + ")", p.code);
        }

        return nameToCodeMap;
    }

    public void updatePlantData(Context ctx)   {

        Log.v("gab", "update started PLANT");
        try {

            String a = new GetPlantLocations().execute().get();
            JSONObject jsonObj = new JSONObject(a);
            JSONObject names = jsonObj.getJSONObject("data");


            Iterator<String> it = names.keys();
            for (int i = 0; i < names.length(); i++) {
                JSONObject plantData = names.getJSONObject(it.next());
                String code = plantData.get("code").toString();
                Plant currPlant = mPlants.get(code);
                if (currPlant == null) {
                    Plant newplant = new Plant(code, plantData.get("coords").toString());
                    JSONObject plantInfo = new JSONObject(new GetPlantInfo().execute(code).get());
                    newplant.getFeaturesFromJson(plantInfo);
                    mPlants.put(code, newplant);

                } else {
                    currPlant.addNewLocation(plantData.get("coords").toString());
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        getInstance().writeToFile(getInstance().getJSON(),ctx );

        Log.v("gab", "update finished PLANT");
    }

    public JSONObject getJSON(){
        JSONObject database = new JSONObject();
        try {

            Set<Map.Entry<String, Plant>> set = PlantMap.getInstance().getPlantMap().entrySet();
            Iterator<Map.Entry<String, Plant>> it2 = set.iterator();
            while (it2.hasNext()) {
                Map.Entry<String, Plant> current = it2.next();
                database.put(current.getKey(), current.getValue().getJSON());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return database;
    }

    public void writeToFile(JSONObject json, Context ctx){

        FileOutputStream outputStream;

        try {
            outputStream = ctx.openFileOutput("data.txt", Context.MODE_PRIVATE);
            outputStream.write(json.toString(1).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(ctx.getFilesDir().toString());
        File filenew = new File("/data/data/seniorproject.arboretumapp/files/data.txt");
        int file_size = Integer.parseInt(String.valueOf(filenew.length()/1024));
        System.out.println(file_size);
    }

    public boolean cacheExists(){
        File file = new File("/data/data/seniorproject.arboretumapp/files/data.txt");
        return file.exists();
    }

    public void populatePlantMap(Context ctx){

        if (!getInstance().cacheExists()){

            getInstance().updatePlantData(ctx);
        }
        else{
            getInstance().readPlantDataFromFile();
        }


    }

    public void updateData(Context ctx){
        getInstance().updatePlantData(ctx);
    }

    private void readPlantDataFromFile(){
        System.out.println("Reading Plant Data From File");
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("/data/data/seniorproject.arboretumapp/files/data.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();


            JSONObject data = new JSONObject(result);
            System.out.println("FILE READ, BEGIN PARSING");


            Iterator<String> it = data.keys();

            for (int i = 0; i < data.length(); i++) {

                JSONObject plantData = data.getJSONObject(it.next());

                String code = plantData.get("code").toString();

                Plant currPlant = mPlants.get(code);
                if (currPlant == null) {
                    Plant newplant = new Plant(code, plantData.getJSONArray("coords"));
                    JSONObject plantInfo = data.getJSONObject(code);
                    newplant.getFeaturesFromJson(plantInfo);
                    mPlants.put(code, newplant);

                } else {
                    currPlant.addNewLocation(plantData.getJSONArray("coords"));
                }

            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public String getThumbnail(String code){

        if (mPlants.get(code) == null)

        return "http://plantgenera.org/ILLUSTRATIONS_thumbnails/159888.jpg";
        else {
            String image = mPlants.get(code).getThumbnail();
            if(image.equals(""))
                return "http://plantgenera.org/ILLUSTRATIONS_thumbnails/159888.jpg";
            else return image;
        }
    }


    public String getSciName(String code) {
        if (mPlants.get(code) == null){
            return code;
        }
        else return mPlants.get(code).getSciName();
    }

    public Date getLastUpdated(){
        File file = new File("/data/data/seniorproject.arboretumapp/files/data.txt");
        if (file.exists())
        return new Date(file.lastModified());
        return null;

    }

    public void getNearPlants(String lat, String lon){
        new GetPlantNames().execute(lat + "," + lon);

    }


    public void redrawGridview(){
        adapter.setListStorage(this.nearTiles);

    }

    public void setNearPlants(ArrayList<String> plants){

        HashMap<String, Integer> tilesAdded = new HashMap<String, Integer>();
        ArrayList<GridTile> tiles = new ArrayList<GridTile>();
        for(int i = 0; i < plants.size(); i++) {
            String plantCodeName = plants.get(i);
            if(!plantCodeName.equals("null") && !plantCodeName.equals("") && plantCodeName != null && !PlantMap.getInstance().getSciName(plantCodeName).equals("")){

                Integer timesAdded = tilesAdded.get(plantCodeName);

                if (timesAdded == null ){
                    tiles.add(new GridTile(PlantMap.getInstance().getSciName(plantCodeName).replace("<i>","").replace("</i> x", "").replace("</i>", ""), PlantMap.getInstance().getThumbnail(plantCodeName), plantCodeName));
                    tilesAdded.put(plantCodeName, 1);
                }
                else {
                    tilesAdded.put(plantCodeName, timesAdded.intValue()+1);
                }

            }
        }
        this.nearTiles = tiles;
        this.nearPlantsCount = tilesAdded;
    }


    public List<GridTile> getNearTiles(){
        if(nearTiles == null)
            nearTiles = new ArrayList<GridTile>();
        return nearTiles;
    }

    public void setAdapter(GridViewAdapter adapter){
        this.adapter = adapter;
    }

    public int getPlantCount(String plant){

        Integer count = this.nearPlantsCount.get(plant);
        if(count == null){
            return 0;
        }
        else{
            return count.intValue();

        }
    }

}
