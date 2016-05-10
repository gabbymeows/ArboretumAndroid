package views;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import seniorproject.arboretumapp.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import adapters.FeatureDetailAdapter;
import models.Feature;
import models.GridTile;
import models.Plant;
import models.PlantMap;
import plantsAPI.DownloadImageTask;
import seniorproject.arboretumapp.R;

/**
 * Created by Gabby on 4/8/2016.
 */
public class PlantDetails {

    @SuppressWarnings("ResourceType")
    public static Dialog getDialog(GridView gridView, final String plantCode, final Context context, View view){

        final Dialog dialog = new Dialog(context);
        //dialog.setContentView(R.layout.plant_detail_view);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.v("gab", "the plant code is "+plantCode);
        final String plantname = PlantMap.getInstance().getPlantMap().get(plantCode).getComName();

//        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//
//        dialog.getWindow().setLayout((int) .96 * width, (int) .4 * height);

        //dialog.setContentView(R.layout.detail_plant_view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.test2_details);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        //String fixedName = plantname.replace("<i>", "").replace("</i> x", "").replace("</i>", "");


        //dialog.setTitle(fixedName);

        Button closeButton = (Button) dialog.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });




        //int imageId = workingAdapter.getImageIdFromPosition(position);


        final Plant plant = PlantMap.getInstance().getPlantMap().get(plantCode);

        TextView bigName = (TextView) dialog.findViewById(R.id.sciname);
        TextView smallName = (TextView) dialog.findViewById(R.id.comname);
        if(plant.getSciName().length() > 20) {
            bigName.setTextSize(18);
            smallName.setTextSize(16);
        }
        if(plant.getSciName().length() > 20) {
            bigName.setTextSize(14);
            smallName.setTextSize(12);
        }
        bigName.setText(plant.getSciName());
        smallName.setText(plantname);


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout headerItem = (LinearLayout) layoutInflater.inflate(R.layout.listview_header_item, null, false );
        ((ListView) dialog.findViewById(R.id.ftListView)).addHeaderView(headerItem);

        ((TextView) headerItem.findViewById(R.id.About)).setText("About");
        ((TextView) headerItem.findViewById(R.id.DescriptionMainTitle)).setText("Description");


        String plantCount = "";
        int count = PlantMap.getInstance().getPlantCount(plantCode);
        if (count == 0)
            plantCount = "There are none of these plants near you currently.";
        else if (count == 1)
            plantCount = "There is one of these plants near you currently.";
        else
            plantCount = "There are "+count+" of these plants near you currently.";

        ((TextView) headerItem.findViewById(R.id.descriptionmain)).setText(plantCount + "\n" + plant.getHabit().getDescription());
        if(!(plant.getHabit().getColor().equals("null") || plant.getHabit().getColor().equals("")))
            ((TextView) headerItem.findViewById(R.id.color)).setText("Color: " + plant.getHabit().getColor());
        if(!(plant.getHabit().getSize().equals("null") || plant.getHabit().getSize().equals("")))
            ((TextView) headerItem.findViewById(R.id.size)).setText("Size: " + plant.getHabit().getSize());
        if(!(plant.getHabit().getTexture().equals("null") || plant.getHabit().getTexture().equals("")))
            ((TextView) headerItem.findViewById(R.id.texture)).setText("Texture: " + plant.getHabit().getTexture());



        if(!plant.getHabit().getImage(0).equals("")) {
            //Picasso.with(context).load(plant.getHabit().getImage(0)).into((ImageView) dialog.findViewById(R.id.habitimage));
            Picasso.with(context).load(plant.getHabit().getImage(0)).into((ImageView) headerItem.findViewById(R.id.largePlantImageView));
        }
        if(!plant.getStems().getImage(0).equals("")){
            //Picasso.with(context).load(plant.getStems().getImage(0)).into((ImageView)dialog.findViewById(R.id.stemimage));
            Picasso.with(context).load(plant.getStems().getImage(0)).into((ImageView) headerItem.findViewById(R.id.largePlantImageView));
        }
        if(!plant.getFruits().getImage(0).equals("")) {
            //Picasso.with(context).load(plant.getFruits().getImage(0)).into((ImageView) dialog.findViewById(R.id.fruitimage));
            Picasso.with(context).load(plant.getFruits().getImage(0)).into((ImageView) headerItem.findViewById(R.id.largePlantImageView));
        }
        if(!plant.getFlowers().getImage(0).equals("")) {
            //Picasso.with(context).load(plant.getFlowers().getImage(0)).into((ImageView) dialog.findViewById(R.id.flowerimage));
            Picasso.with(context).load(plant.getFlowers().getImage(0)).into((ImageView) headerItem.findViewById(R.id.largePlantImageView));
        }
        if(!plant.getLeaves().getImage(0).equals("")) {
            //Picasso.with(context).load(plant.getLeaves().getImage(0)).into((ImageView) dialog.findViewById(R.id.leavesimage));
            Picasso.with(context).load(plant.getLeaves().getImage(0)).into((ImageView)headerItem.findViewById(R.id.largePlantImageView));
        }
//        if(!plant.getBuds().getImage(0).equals(""))
//            Picasso.with(context).load(plant.getBuds().getImage(0)).into((ImageView)dialog.findViewById(R.id.budsimage));
//        if(!plant.getBark().getImage(0).equals(""))
//            Picasso.with(context).load(plant.getBark().getImage(0)).into((ImageView)dialog.findViewById(R.id.barkimage));
//        if(!plant.getCulture().getImage(0).equals(""))
//            Picasso.with(context).load(plant.getCulture().getImage(0)).into((ImageView)dialog.findViewById(R.id.cultureimage));
//        if(!plant.getFallcolor().getImage(0).equals(""))
//            Picasso.with(context).load(plant.getFallcolor().getImage(0)).into((ImageView)dialog.findViewById(R.id.fallimage));

        ArrayList<Feature> features = new ArrayList<Feature>();
        Map<String, Feature> fts = plant.getFeatures();
        String[] ftKeys = {"habit", "leaves", "buds", "stems", "flowers", "fruits", "fallColor", "bark", "culture"};
        for( String key : ftKeys){
            Log.v("gab", "key is "+key);
            Feature ft = fts.get(key);
            if (!((ft.getDescription().equals("null") || ft.getDescription().equals("")) && ft.getImage(0).equals(""))){
                features.add(ft);
            }
        }

        FeatureDetailAdapter featureDetailAdapter = new FeatureDetailAdapter(context, features);
        ((ListView) dialog.findViewById(R.id.ftListView)).setAdapter(featureDetailAdapter);

        final ImageButton like = (ImageButton) dialog.findViewById(R.id.likeButton);
        if(!PlantMap.getInstance().getFavoritePlantsList().contains(plantCode))
            like.setImageResource(R.drawable.like_unfilled_48);
        else
            like.setImageResource(R.drawable.like_filled_48);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PlantMap.getInstance().getFavoritePlantsList().contains(plantCode)) {
                    like.setImageResource(R.drawable.like_filled_48);
                    PlantMap.getInstance().getFavoritePlantsList().add(plantCode);
                    Toast.makeText(context, plantname + " added to favorites!", Toast.LENGTH_SHORT).show();
                    PlantMap.getInstance().getFavTiles().add(new GridTile(PlantMap.getInstance().getSciName(plantCode), PlantMap.getInstance().getThumbnail(plantCode), plantCode));
                } else {
                    like.setImageResource(R.drawable.like_unfilled_48);
                    PlantMap.getInstance().getFavoritePlantsList().remove(plantCode);
                    Toast.makeText(context, plantname + " removed from favorites.", Toast.LENGTH_SHORT).show();
                    PlantMap.getInstance().updateFavGridTiles();
                }


            }
        });

        return dialog;

    }
}
