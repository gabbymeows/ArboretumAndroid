package fragments;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.GridViewAdapter;
import models.GridTile;
import seniorproject.arboretumapp.R;
import plantsAPI.*;

/**
 * Created by Gabby on 2/3/2016.
 */
public class PlantGridFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "plant_grid_view";

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





            for(int i = 0; i < names.length(); i++){

                JSONObject plantInfo = new JSONObject(new GetPlantInfo().execute(names.names().get(i).toString()).get());
                StringBuffer imageURL = null;
                if(plantInfo.getJSONObject("habit").getJSONArray("images").length() > 0){
                    imageURL = new StringBuffer(plantInfo.getJSONObject("habit").getJSONArray("images").get(0).toString());
                }

                else if(plantInfo.getJSONObject("flowers").getJSONArray("images").length() > 0){
                    imageURL = new StringBuffer(plantInfo.getJSONObject("flowers").getJSONArray("images").get(0).toString());
                }

                else if(plantInfo.getJSONObject("leaves").getJSONArray("images").length() > 0){
                    imageURL = new StringBuffer(plantInfo.getJSONObject("leaves").getJSONArray("images").get(0).toString());
                }
                else if(plantInfo.getJSONObject("fruits").getJSONArray("images").length() > 0){
                    imageURL = new StringBuffer(plantInfo.getJSONObject("fruits").getJSONArray("images").get(0).toString());
                }
                else{
                    continue;
                }
                System.out.println();

                //TODO write a function to go from code to real common name
                tiles.add(new GridTile(names.getJSONObject(names.names().get(i).toString()).get("com_name").toString(), imageURL.toString()));

                //if(plantInfo.has("habit")){
                  //  System.out.println("has a habit");
                  //  if (plantInfo.getJSONObject("habit").has("images")){
                     //   System.out.print("Has images Too");
                       // if(plantInfo.getJSONArray("images").length() > 0){
                          //  System.out.println("its not empty!!");
                       // }
                   // }
              //  }

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


}
