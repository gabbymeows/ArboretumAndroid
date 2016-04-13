package fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import adapters.GridViewAdapter;
import models.GridTile;
import models.Plant;
import models.PlantMap;
import seniorproject.arboretumapp.R;
import plantsAPI.*;
import views.PlantDetails;


/**
 * Created by Gabby on 2/3/2016.
 */
public class PlantGridFragment extends Fragment {

    final boolean UPDATE_DATABASE = false;

    private static final String ARG_SECTION_NUMBER = "plant_grid_view";

    private Set<String> tilesAdded;


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
            String[] names = new String[jsonObj.length()];
            Iterator<String> it = jsonObj.keys();
            for(int i = 0; i < jsonObj.length(); i++){




                names[i] = jsonObj.getJSONObject(it.next()).get("code").toString();


            }



            //PlantMap.getInstance().populatePlantMap(getContext());
            tilesAdded = new HashSet<String>();
            System.out.println(names.length);
            if (!UPDATE_DATABASE)
            {
                for(int i = 0; i < names.length; i++) {

                    //Log.d("plant_tag", names.names().get(i).toString());
                    String plantCodeName = names[i];

                    if(!tilesAdded.contains(plantCodeName) && !plantCodeName.equals("") && plantCodeName != null && !PlantMap.getInstance().getSciName(plantCodeName).equals("")){
                        tiles.add(new GridTile(PlantMap.getInstance().getSciName(plantCodeName).replace("<i>","").replace("</i> x", "").replace("</i>", ""), PlantMap.getInstance().getThumbnail(plantCodeName), plantCodeName) );
                        tilesAdded.add(plantCodeName);
                    }
    /*
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

    */


                }

            }


            // Here we inflate the layout we created above
            GridView gridView = (GridView) rootView.findViewById(R.id.plantgridview);
            gridView.setAdapter(new GridViewAdapter(this.getActivity().getApplicationContext(), tiles));
            gridView.setOnItemClickListener(onListClick);

            return rootView;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return rootView;
    }



    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            GridTile tileClicked = ((GridTile) ((parent.getAdapter()).getItem(position)));
            PlantDetails.getDialog(tileClicked.getPlantCode(), parent.getContext(), view).show();

        }
    };




    private static void downloadDatabase(Context ctx){



                //JSONObject plantInfo = new JSONObject(new GetPlantInfo().execute(names.names().get(i).toString()).get());
                //plantInfo.getJSONObject("habit").getJSONArray("images").length() > 0


        try {


                /*

                */
        }
        catch(Exception e){
            e.printStackTrace();

        }

    }

}
