package fragments;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import adapters.GridViewAdapter;
import models.GridTile;
import models.Plant;
import seniorproject.arboretumapp.R;
import plantsAPI.*;



/**
 * Created by Gabby on 2/3/2016.
 */
public class PlantGridFragment extends Fragment {

    final boolean UPDATE_DATABASE = true;

    private static final String ARG_SECTION_NUMBER = "plant_grid_view";
    protected HashMap<String, Plant> plantsMap;

    @Override
    public void onSaveInstanceState(final Bundle state){
        super.onSaveInstanceState(state);

    }

    public PlantGridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_plant_grid, container, false);

        List<GridTile> tiles = new ArrayList<>();


        try {

            String a = new GetPlantNames().execute().get();

            JSONObject jsonObj = new JSONObject(a);
            JSONObject names= jsonObj.getJSONObject("data");



            if (!UPDATE_DATABASE)
            {
            for(int i = 0; i < names.length(); i++) {

                //Log.d("plant_tag", names.names().get(i).toString());
                String plantCodeName = names.names().get(i).toString();
                if (plantsMap.get(plantCodeName) != null)
                    plantsMap.put(plantCodeName, null);

                JSONObject plantInfo = new JSONObject(new GetPlantInfo().execute(names.names().get(i).toString()).get());
                StringBuffer imageURL = null;
                if (plantInfo.getJSONObject("habit").getJSONArray("images").length() > 0) {
                    imageURL = new StringBuffer(plantInfo.getJSONObject("habit").getJSONArray("images").get(0).toString());
                } else if (plantInfo.getJSONObject("flowers").getJSONArray("images").length() > 0) {
                    imageURL = new StringBuffer(plantInfo.getJSONObject("flowers").getJSONArray("images").get(0).toString());
                } else if (plantInfo.getJSONObject("leaves").getJSONArray("images").length() > 0) {
                    imageURL = new StringBuffer(plantInfo.getJSONObject("leaves").getJSONArray("images").get(0).toString());
                } else if (plantInfo.getJSONObject("fruits").getJSONArray("images").length() > 0) {
                    imageURL = new StringBuffer(plantInfo.getJSONObject("fruits").getJSONArray("images").get(0).toString());
                } else {
                    continue;
                }


                tiles.add(new GridTile(names.getJSONObject(names.names().get(i).toString()).get("com_name").toString(), imageURL.toString()));

            }

            }


/*
            tiles.add(new GridTile("Blackhaw Plant", "blackhaw_viburnum_tile"));
            tiles.add(new GridTile("Black Locust", "black_locust_tile"));
            tiles.add(new GridTile("Gingko", "gingko_tile"));
            tiles.add(new GridTile("Dragon Eye Pine", "pine_grid_tile_image"));
            tiles.add(new GridTile("Blackhaw Plant", "blackhaw_viburnum_tile"));
            tiles.add(new GridTile("Black Locust", "black_locust_tile"));
            tiles.add(new GridTile("Gingko", "gingko_tile"));
            tiles.add(new GridTile("Dragon Eye Pine", "pine_grid_tile_image"));
            tiles.add(new GridTile("Blackhaw Plant", "blackhaw_viburnum_tile"));
            tiles.add(new GridTile("Black Locust", "black_locust_tile"));
            tiles.add(new GridTile("Gingko", "gingko_tile"));
            tiles.add(new GridTile("Dragon Eye Pine", "pine_grid_tile_image"));
            tiles.add(new GridTile("Blackhaw Plant", "blackhaw_viburnum_tile"));
            tiles.add(new GridTile("Black Locust", "black_locust_tile"));
            tiles.add(new GridTile("Gingko", "gingko_tile"));
            tiles.add(new GridTile("Black Locust", "black_locust_tile"));
            tiles.add(new GridTile("Gingko", "gingko_tile"));

*/
            // Here we inflate the layout we created above
            GridView gridView = (GridView) rootView.findViewById(R.id.plantgridview);
            gridView.setAdapter(new GridViewAdapter(this.getActivity().getApplicationContext(), tiles));
            gridView.setOnItemClickListener(onListClick);
            if(UPDATE_DATABASE)
                downloadDatabase(getContext());
            return rootView;
        }
        catch(Exception e){

        }
        return rootView;
    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {





            GridViewAdapter workingAdapter = (GridViewAdapter)parent.getAdapter();
            GridTile tileClicked = ((GridTile)((parent.getAdapter()).getItem(position)));
            String plantname = workingAdapter.getPlantNameFromPosition(position);

            //todo figure out how to show the plant details fragment from here
            // THE FOLLOWING CODE IS BULLSHIT.... MIGHT BE USEFUL.... DUNNO....

            final Dialog dialog = new Dialog(parent.getContext());
            dialog.setContentView(R.layout.plant_detail_view);
            dialog.setTitle(plantname);


            ImageView plantImage = (ImageView) dialog.findViewById(R.id.largePlantImage);
            plantImage.setImageDrawable(((ImageView) view.findViewById(R.id.imageView)).getDrawable());

            int imageId = workingAdapter.getImageIdFromPosition(position);
            new DownloadImageTask(plantImage).execute(tileClicked.getImageResource());

            dialog.show();
            // END OF BULLSHIT CODEs

            //getFragmentManager().findFragmentById(R.id.fra)

        }
    };




    private static void downloadDatabase(Context ctx){
        HashMap<String, Plant> plants = new HashMap<String, Plant>();

        try {
            String a = new GetPlantLocations().execute().get();
            JSONObject jsonObj = new JSONObject(a);
            JSONObject names = jsonObj.getJSONObject("data");



            Iterator<String> it = names.keys();
            for(int i = 0; i < names.length(); i++) {
                JSONObject plantData = names.getJSONObject(it.next());
                String code = plantData.get("code").toString();
                Plant currPlant = plants.get(code);
                if( currPlant == null){
                    Plant newplant = new Plant(code, plantData.get("coords").toString());

                    JSONObject plantInfo = new JSONObject(new GetPlantInfo().execute(code).get());
                    newplant.getFeaturesFromJson(plantInfo);
                    plants.put(code,newplant);

                }
                else{
                    currPlant.addNewLocation(plantData.get("coords").toString());
                }
                //JSONObject plantInfo = new JSONObject(new GetPlantInfo().execute(names.names().get(i).toString()).get());
                //plantInfo.getJSONObject("habit").getJSONArray("images").length() > 0
            }


            JSONObject database = new JSONObject();
            Set<Map.Entry<String, Plant>> set = plants.entrySet();
            Iterator<Map.Entry<String,Plant>> it2 = set.iterator();
            while (it2.hasNext()){
                Map.Entry<String, Plant> current = it2.next();
                database.put(current.getKey(),current.getValue().getJSON());
            }

            System.out.println(database.toString(4));
            /*
            FileOutputStream outputStream;

            try {
                outputStream = ctx.openFileOutput("data.txt", Context.MODE_PRIVATE);
                outputStream.write(database.toString(1).getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(ctx.getFilesDir().toString());
            File filenew = new File("/data/data/seniorproject.arboretumapp/files/data.txt");
            int file_size = Integer.parseInt(String.valueOf(filenew.length()/1024));
            System.out.println(file_size);
            */
        }
        catch(Exception e){
            e.printStackTrace();

        }

    }

}
