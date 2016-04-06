package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import seniorproject.arboretumapp.R;


public class MapFragment extends Fragment {
    MapView mapView;
    GoogleMap map;

    @Override
    public void onSaveInstanceState(final Bundle state){
        super.onSaveInstanceState(state);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.093580, -88.217958))
                .title("Azumaya"))
                .setSnippet("Offerman and Lee design, Offerman Workshop in collaboration with Shozo Sato");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.096110, -88.216828))
                .title("Arboretum Presidential Memorial Garden and Grove"))
                .setSnippet("Garden under development");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.093627, -88.216117))
                .title("Dr. Frank W. Kari Ponds Walkway"))
                .setSnippet("Walkway around the larger of two ponds features bank plantings of native forbs and grasses.");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.093841, -88.218470))
                .title("Illinois Prairie Hosta Garden"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.092834, -88.217712))
                .title("Japan House Dry Garden (Karesansui)"))
                .setSnippet("A James and Lorene Bier Garden");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.092935, -88.218056))
                .title("Japan House Tea Garden (Cha Niwa)"))
                .setSnippet("A James and Lorene Bier Garden");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.096262, -88.218394))
                .title("Master Gardener Idea Garden"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.094427, -88.216829))
                .title("Meadow"))
                .setSnippet("Popular wedding venue");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.095367, -88.216817))
                .title("Miles C Hartley Selections Garden"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.095341, -88.218467))
                .title("Noel Welcome Garden"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.094950, -88.218732))
                .title("North Arboretum Parking Lot"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.091506, -88.215765))
                .title("Nut Grove East"))
                .setSnippet("Dr. J. C. McDaniels site for research into the improvement of nut trees.");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.091502, -88.217519))
                .title("Nut Grove West"))
                .setSnippet("Dr. J. C. McDaniels site for research into the improvement of nut trees.");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.095392, -88.215137))
                .title("Oak Savanna"))
                .setSnippet("Area under development");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.093118, -88.216759))
                .title("Ponds"))
                .setSnippet("Developed in 1997 as a gift-in-kind from the Illinois Land Improvement Contractors Association.");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.093620, -88.218096))
                .title("Pond event lawn E1"))
                .setSnippet("Popular wedding venue");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.093634, -88.217769))
                .title("Pond event lawn E2"))
                .setSnippet("Popular wedding venue");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.093902, -88.216808))
                .title("Pond event lawn E3"))
                .setSnippet("Popular wedding venue");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.094539, -88.217421))
                .title("Service building"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.093984, -88.218401))
                .title("Sen Cherry Tree Allée"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.092081, -88.217917))
                .title("South Arboretum Parking Lot"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.093022, -88.217167))
                .title("Island"))
                .setSnippet("Future site of the Stroll Garden (Kaiyushiki, dedicated to Shozo and Alice Sato");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.093524, -88.216600))
                .title("Sycamore Peninsula"))
                .setSnippet("Popular wedding venue");
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.095349, -88.218167))
                .title("Welcome Walk"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.084779, -88.216282))
                .title("Pollinatarium"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(40.095392, -88.215137))
                .title("South Arboretum Woods (SAW)"));

        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(40.093661, -88.216690), 16);
        map.animateCamera(cameraUpdate);

        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
