package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapters.GridViewAdapter;
import models.GridTile;
import seniorproject.arboretumapp.R;

/**
 * Created by Gabby on 2/9/2016.
 */
public class FavoritesFragment extends Fragment {

    public FavoritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.favorites_view, container, false);
        //rootView.setVerticalScrollBarEnabled(true);

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
        GridView gridView = (GridView) rootView.findViewById(R.id.favplantgridview);
        gridView.setAdapter(new GridViewAdapter(this.getActivity().getApplicationContext(), tiles));


        return rootView;
    }

}
