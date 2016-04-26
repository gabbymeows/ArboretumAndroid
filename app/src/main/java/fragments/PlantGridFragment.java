package fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import seniorproject.arboretumapp.MainActivity;
import seniorproject.arboretumapp.R;
import plantsAPI.*;
import views.PlantDetails;


/**
 * Created by Gabby on 2/3/2016.
 */
public class PlantGridFragment extends Fragment {

    final boolean UPDATE_DATABASE = false;
    private RadioButton closest;
    private RadioButton fav;
    private GridView gridView;
    private GridViewAdapter adapter;
    private GridViewAdapter favAdapter;
    private LocationManager locationManager;
    private View rootView;
    private Dialog dialog;
    private String lat;
    private String longi;


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
        rootView = inflater.inflate(R.layout.fragment_plant_grid, container, false);
        List<GridTile> tiles = new ArrayList<>();
        List<GridTile> favTiles = new ArrayList<>();

        locationManager = MainActivity.getLocationManager();

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

                    if(!tilesAdded.contains(plantCodeName) && !plantCodeName.equals("null") && !plantCodeName.equals("") && plantCodeName != null && !PlantMap.getInstance().getSciName(plantCodeName).equals("")){
                        tiles.add(new GridTile(PlantMap.getInstance().getSciName(plantCodeName).replace("<i>","").replace("</i> x", "").replace("</i>", ""), PlantMap.getInstance().getThumbnail(plantCodeName), plantCodeName) );
                        tilesAdded.add(plantCodeName);
                    }
                }

            }



            List<GridTile> favs = PlantMap.getInstance().getFavTiles();

            // Here we inflate the layout we created above
            gridView = (GridView) rootView.findViewById(R.id.plantgridview);
            adapter = new GridViewAdapter(this.getActivity().getApplicationContext(), tiles);
            favAdapter = new GridViewAdapter(this.getActivity().getApplicationContext(), favs);

            closest = (RadioButton) rootView.findViewById(R.id.closestRadio);
            fav = (RadioButton) rootView.findViewById(R.id.favRadio);

            gridView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
            gridView.setOnItemClickListener(onListClick);



            closest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (closest.isChecked()){
                        gridView.setAdapter(adapter);
                    }
                    else{
                        gridView.setAdapter(favAdapter);
                    }
                    gridView.setOnItemClickListener(onListClick);
                    rootView.refreshDrawableState();
                }
            });

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fav.isChecked()){
                        gridView.setAdapter(favAdapter);
                    }
                    else{
                        gridView.setAdapter(adapter);
                    }
                    gridView.setOnItemClickListener(onListClick);
                    rootView.refreshDrawableState();
                }
            });


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
            dialog = PlantDetails.getDialog(gridView, tileClicked.getPlantCode(), parent.getContext(), parent.getRootView());
            Log.v("gab", "fav size before is " + adapter.getCount());
            dialog.show();



            ((Button)dialog.findViewById(R.id.closeButton)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fav.isChecked()) {
                        gridView.setAdapter(favAdapter);
                        gridView.refreshDrawableState();
                        rootView.refreshDrawableState();
                        Log.v("gab", "fav is checked");
                    }
                    dialog.dismiss();
                }
            });

            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                    if (fav.isChecked()){
                        gridView.setAdapter(favAdapter);
                        gridView.refreshDrawableState();
                        rootView.refreshDrawableState();
                        Log.v("gab", "fav is checked");
                    }
                }
            });

        }
    };

    @Override
    public void onResume(){
        super.onResume();
        if(fav != null && fav.isChecked())
            gridView.setAdapter(favAdapter);
        else
            gridView.setAdapter(adapter);
    }


    private void getInitialLocation(){
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);
        //Location loc = locationManager.requestSingleUpdate(provider, );



        final LocationListener initialListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude()+"";
                longi = location.getLongitude()+"";
                Log.v("gab", "yo initial is "+lat+" "+longi);
                //make api call
                //update gridview
                 //turn off listener

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(provider, 0, 0,
                initialListener);
        locationManager.removeUpdates(initialListener);

    }

    private void listenForLocationChanges(){
        //location listeneing stuff

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String lat = location.getLatitude()+"";
                String longi = location.getLongitude()+"";

                Log.v("gab", lat +" is lat");
                Log.v("gab", longi +" is long");

                try {
                    String a = new GetPlantNames().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 5,
                locationListener);

    }

}
