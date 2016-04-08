package models;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import plantsAPI.GetPlantInfo;
import plantsAPI.GetPlantLocations;

/**
 * Created by dquea on 4/6/2016.
 */
public class PlantMap {

    private static PlantMap mInstance = null;
    private HashMap<String, Plant> mPlants;
    private List<String> namesList;
    private HashMap<String, String> nameToCodeMap;

    private PlantMap(){
        this.mPlants=new HashMap<String, Plant>();
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

    public List<String> getDisplayNamesList(){
        namesList = new ArrayList<String>();
        for( Plant p : mPlants.values()){
            namesList.add(p.getSciName() + " (" + p.getComName()+")");
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

    public void updatePlantData(Context ctx){

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

            //getInstance().updatePlantData(ctx);
            getInstance().readPlantDataFromFile();
        }


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
            return "Something is wrong";
        }
        else return mPlants.get(code).getSciName();
    }
}
