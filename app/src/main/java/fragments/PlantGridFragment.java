package fragments;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import adapters.GridViewAdapter;
import models.GridTile;
import seniorproject.arboretumapp.MainActivity;
import seniorproject.arboretumapp.R;

/**
 * Created by Gabby on 2/3/2016.
 */
public class PlantGridFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "plant_grid_view";

    public PlantGridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_plant_grid, container, false);

        List<GridTile> tiles = new ArrayList<>();
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
        tiles.add(new GridTile("Dragon Eye Pine", "pine_grid_tile_image"));
        tiles.add(new GridTile("Blackhaw Plant", "blackhaw_viburnum_tile"));
        tiles.add(new GridTile("Black Locust", "black_locust_tile"));
        tiles.add(new GridTile("Gingko", "gingko_tile"));
        tiles.add(new GridTile("Black Locust", "black_locust_tile"));
        tiles.add(new GridTile("Gingko", "gingko_tile"));


        // Here we inflate the layout we created above
        GridView gridView = (GridView) rootView.findViewById(R.id.plantgridview);
        gridView.setAdapter(new GridViewAdapter(this.getActivity().getApplicationContext(), tiles));
        gridView.setOnItemClickListener(onListClick);

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


            int imageId = workingAdapter.getImageIdFromPosition(position);
            plantImage.setImageResource(imageId);

            dialog.show();
            // END OF BULLSHIT CODEs

            //getFragmentManager().findFragmentById(R.id.fra)

        }
    };


}
