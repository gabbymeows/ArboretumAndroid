package fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import java.util.List;
import java.util.Set;
import adapters.GridViewAdapter;
import models.GridTile;
import models.PlantMap;
import seniorproject.arboretumapp.MainActivity;
import seniorproject.arboretumapp.R;
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
    private LocationListener locationListener;
    private View rootView;
    private Dialog dialog;


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

        locationManager = MainActivity.getLocationManager();
        initializeLocationListener();
        startLocationListening();

        try {

            List<GridTile> favs = PlantMap.getInstance().getFavTiles();
            List<GridTile> tiles = PlantMap.getInstance().getNearTiles();

            // Here we inflate the layout we created above
            gridView = (GridView) rootView.findViewById(R.id.plantgridview);
            adapter = new GridViewAdapter(this.getActivity().getApplicationContext(), tiles);
            PlantMap.getInstance().setAdapter(adapter);
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
            dialog.show();
            Log.v("asdf", "num instances = " + PlantMap.getInstance().getPlantCount(tileClicked.getPlantCode()));
            ((Button)dialog.findViewById(R.id.closeButton)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fav.isChecked()) {
                        gridView.setAdapter(favAdapter);
                        gridView.refreshDrawableState();
                        rootView.refreshDrawableState();
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
        startLocationListening();
    }


    @Override
    public void onPause(){
        super.onPause();
        stopLocationListening();
    }

    private void initializeLocationListener(){

        this.locationListener = new LocationListener() {
            String lat, longi;
            @Override
            public void onLocationChanged(Location location) {

                lat = location.getLatitude()+"";
                longi = location.getLongitude()+"";

                Log.v("gab", lat +" is lat");
                Log.v("gab", longi +" is long");

                PlantMap.getInstance().getNearPlants(lat, longi);
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
    }

    private void startLocationListening(){
        if(locationManager != null && locationListener != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0,
                    locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0,
                    locationListener);
            Log.v("gab", "starting location listen");
        }
    }

    private void stopLocationListening(){
        if(locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
            Log.v("gab", "pausing location listen");
        }
    }




}
