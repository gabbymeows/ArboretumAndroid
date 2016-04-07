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



/**
 * Created by Gabby on 2/3/2016.
 */
public class PlantGridFragment extends Fragment {

    final boolean UPDATE_DATABASE = false;

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
            String[] names = new String[jsonObj.length()];
            Iterator<String> it = jsonObj.keys();
            for(int i = 0; i < jsonObj.length(); i++){




                names[i] = jsonObj.getJSONObject(it.next()).get("code").toString();


            }



            //PlantMap.getInstance().populatePlantMap(getContext());

            System.out.println(names.length);
            if (!UPDATE_DATABASE)
            {
                for(int i = 0; i < names.length; i++) {

                    //Log.d("plant_tag", names.names().get(i).toString());
                    String plantCodeName = names[i];

                    tiles.add(new GridTile(PlantMap.getInstance().getSciName(plantCodeName).replace("<i>","").replace("</i> x", "").replace("</i>", ""), PlantMap.getInstance().getThumbnail(plantCodeName), plantCodeName) );

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


            GridViewAdapter workingAdapter = (GridViewAdapter) parent.getAdapter();
            GridTile tileClicked = ((GridTile) ((parent.getAdapter()).getItem(position)));
            String plantname = workingAdapter.getPlantNameFromPosition(position);
            String plantCode = tileClicked.getPlantCode();

            //todo figure out how to show the plant details fragment from here
            // THE FOLLOWING CODE IS BULLSHIT.... MIGHT BE USEFUL.... DUNNO....

            final Dialog dialog = new Dialog(parent.getContext());
            //dialog.setContentView(R.layout.plant_detail_view);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            dialog.getWindow().setLayout((int) .96 * width, (int) .4 * height);

            //dialog.setContentView(R.layout.detail_plant_view);
            dialog.setContentView(R.layout.test2_details);
            String fixedName = plantname.replace("<i>", "").replace("</i> x", "").replace("</i>", "");


            //dialog.setTitle(fixedName);

            Button closeButton = (Button) dialog.findViewById(R.id.closeButton);

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });


            ImageView plantImage = (ImageView) dialog.findViewById(R.id.largePlantImageView);
            plantImage.setImageDrawable(((ImageView) view.findViewById(R.id.imageView)).getDrawable());

            //int imageId = workingAdapter.getImageIdFromPosition(position);


            Plant plant = PlantMap.getInstance().getPlantMap().get(plantCode);

            ((TextView) dialog.findViewById(R.id.sciname)).setText(plantname);
            ((TextView) dialog.findViewById(R.id.comname)).setText(plant.getComName());

            ((TextView) dialog.findViewById(R.id.About)).setText("About");
            ((TextView) dialog.findViewById(R.id.DescriptionMainTitle)).setText("Description");


            ((TextView) dialog.findViewById(R.id.descriptionmain)).setText(plant.getHabit().getDescription());
            ((TextView) dialog.findViewById(R.id.color)).setText("Color: " + plant.getHabit().getColor());
            ((TextView) dialog.findViewById(R.id.size)).setText("Size: " + plant.getHabit().getSize());
            ((TextView) dialog.findViewById(R.id.texture)).setText("Texture: " + plant.getHabit().getTexture());
            ((TextView) dialog.findViewById(R.id.Habit)).setText("Habit");
            ((TextView) dialog.findViewById(R.id.habit)).setText(plant.getHabit().getHabit());
//
//            if (plant.getLeaves().getDescription() == null || plant.getLeaves().getDescription().equals("null")){
//                Log.v("yo", "its null "+plant.getLeaves().getDescription());
//            }

            if (!plant.getLeaves().getDescription().equals("null") && !plant.getLeaves().getDescription().equals("")){
                ((TextView) dialog.findViewById(R.id.Leaves)).setText("Leaves");
                ((TextView) dialog.findViewById(R.id.leaves)).setText(plant.getLeaves().getDescription());
                Log.v("yo", "not null... " + plant.getLeaves().getDescription());
         }

            ((TextView) dialog.findViewById(R.id.Fruits)).setText("Fruits");
            ((TextView) dialog.findViewById(R.id.fruits)).setText(plant.getFruits().getDescription());
            ((TextView) dialog.findViewById(R.id.Buds)).setText("Buds");
            ((TextView) dialog.findViewById(R.id.buds)).setText(plant.getBuds().getDescription());
            ((TextView) dialog.findViewById(R.id.Bark)).setText("Bark");
            ((TextView) dialog.findViewById(R.id.bark)).setText(plant.getBark().getDescription());
            ((TextView) dialog.findViewById(R.id.Culture)).setText("Culture");
            ((TextView) dialog.findViewById(R.id.culture)).setText(plant.getCulture().getDescription());
            ((TextView) dialog.findViewById(R.id.Flower)).setText("Flower");
            ((TextView) dialog.findViewById(R.id.flower)).setText(plant.getFlowers().getDescription());
            ((TextView) dialog.findViewById(R.id.Fall)).setText("Fall Color");
            ((TextView) dialog.findViewById(R.id.fall)).setText(plant.getFallcolor().getDescription());
            ((TextView) dialog.findViewById(R.id.Stem)).setText("Stems");
            ((TextView) dialog.findViewById(R.id.stem)).setText(plant.getStems().getDescription());

            Bitmap imageHabit = null;
            Bitmap imageFlower = null;
            Bitmap imageLeaves = null;
            Bitmap imageBuds = null;
            Bitmap imageFruit = null;
            Bitmap imageBark = null;
            Bitmap imageCulture = null;
            Bitmap imageFall = null;
            Bitmap imageStem = null;

            try {
                imageHabit = new DownloadImageTask(plantImage).execute(plant.getHabit().getImage(0)).get();
                imageLeaves = new DownloadImageTask(plantImage).execute(plant.getLeaves().getImage(0)).get();
                imageBuds = new DownloadImageTask(plantImage).execute(plant.getBuds().getImage(0)).get();
                imageFlower = new DownloadImageTask(plantImage).execute(plant.getFlowers().getImage(0)).get();
                imageFruit = new DownloadImageTask(plantImage).execute(plant.getFruits().getImage(0)).get();
                imageBark = new DownloadImageTask(plantImage).execute(plant.getBark().getImage(0)).get();
                imageCulture = new DownloadImageTask(plantImage).execute(plant.getCulture().getImage(0)).get();
                imageFall = new DownloadImageTask(plantImage).execute(plant.getFallcolor().getImage(0)).get();
                imageStem = new DownloadImageTask(plantImage).execute(plant.getStems().getImage(0)).get();
                //imageHabit = new DownloadImageTask(plantImage).execute(plant.getHabit().getImage(0)).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            ((ImageView)dialog.findViewById(R.id.habitimage)).setImageBitmap(imageHabit);
            ((ImageView)dialog.findViewById(R.id.leavesimage)).setImageBitmap(imageLeaves);
            ((ImageView)dialog.findViewById(R.id.budsimage)).setImageBitmap(imageBuds);
            ((ImageView)dialog.findViewById(R.id.flowerimage)).setImageBitmap(imageFlower);
            ((ImageView)dialog.findViewById(R.id.fruitimage)).setImageBitmap(imageFruit);
            ((ImageView)dialog.findViewById(R.id.barkimage)).setImageBitmap(imageBark);
            ((ImageView)dialog.findViewById(R.id.cultureimage)).setImageBitmap(imageCulture);
            ((ImageView)dialog.findViewById(R.id.fallimage)).setImageBitmap(imageFall);
            ((ImageView)dialog.findViewById(R.id.stemimage)).setImageBitmap(imageStem);


//            TextView title1 = (TextView) dialog.findViewById(R.id.title1);
//            title1.setText("Habit");
//
//            TextView text1 = (TextView) dialog.fidViewById(R.id.text1);
//            text1.setText(plant.getHabit().getHabit());
//
//            TextView title2 = (TextView) dialog.findViewById(R.id.title2);
//            title2.setText("Color");
//
//            TextView text2 = (TextView) dialog.findViewById(R.id.text2);
//            text2.setText(plant.getHabit().getColor());
//
//            TextView title3 = (TextView) dialog.findViewById(R.id.title3);
//            title3.setText("Description");
//
//            TextView text3 = (TextView) dialog.findViewById(R.id.text3);
//            text3.setText(plant.getHabit().getDescription());


            new DownloadImageTask(plantImage).execute(tileClicked.getImageResource());

            dialog.show();
            // END OF BULLSHIT CODEs

            //getFragmentManager().findFragmentById(R.id.fra)

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
