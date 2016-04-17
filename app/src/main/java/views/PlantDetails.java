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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
    public static Dialog getDialog(final String plantCode, final Context context, View view){

        final Dialog dialog = new Dialog(context);
        //dialog.setContentView(R.layout.plant_detail_view);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final String plantname = PlantMap.getInstance().getPlantMap().get(plantCode).getComName();

//        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//
//        dialog.getWindow().setLayout((int) .96 * width, (int) .4 * height);

        //dialog.setContentView(R.layout.detail_plant_view);
        dialog.setContentView(R.layout.test2_details);
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

        ((TextView) dialog.findViewById(R.id.About)).setText("About");
        ((TextView) dialog.findViewById(R.id.DescriptionMainTitle)).setText("Description");


        ((TextView) dialog.findViewById(R.id.descriptionmain)).setText(plant.getHabit().getDescription());
        ((TextView) dialog.findViewById(R.id.color)).setText("Color: " + plant.getHabit().getColor());
        ((TextView) dialog.findViewById(R.id.size)).setText("Size: " + plant.getHabit().getSize());
        ((TextView) dialog.findViewById(R.id.texture)).setText("Texture: " + plant.getHabit().getTexture());

        ((TextView) dialog.findViewById(R.id.Habit)).setText("Habit");
        if (!plant.getHabit().getHabit().equals("null")) {
            ((TextView) dialog.findViewById(R.id.habit)).setText(plant.getHabit().getHabit());
        }

        if (!plant.getLeaves().getDescription().equals("null") && !plant.getLeaves().getDescription().equals("")){
            ((TextView) dialog.findViewById(R.id.Leaves)).setText("Leaves");
            ((TextView) dialog.findViewById(R.id.leaves)).setText(plant.getLeaves().getDescription());
            Log.v("yo", "not null... " + plant.getLeaves().getDescription());
        }

        ((TextView) dialog.findViewById(R.id.Fruits)).setText("Fruits");
        if (!plant.getFruits().getDescription().equals("null"))
            ((TextView) dialog.findViewById(R.id.fruits)).setText(plant.getFruits().getDescription());
        ((TextView) dialog.findViewById(R.id.Buds)).setText("Buds");
        if (!plant.getBuds().getDescription().equals("null"))
            ((TextView) dialog.findViewById(R.id.buds)).setText(plant.getBuds().getDescription());
        ((TextView) dialog.findViewById(R.id.Bark)).setText("Bark");
        if (!plant.getBark().getDescription().equals("null"))
            ((TextView) dialog.findViewById(R.id.bark)).setText(plant.getBark().getDescription());
        ((TextView) dialog.findViewById(R.id.Culture)).setText("Culture");
        if (!plant.getCulture().getDescription().equals("null"))
            ((TextView) dialog.findViewById(R.id.culture)).setText(plant.getCulture().getDescription());
        ((TextView) dialog.findViewById(R.id.Flower)).setText("Flower");
        if (!plant.getFlowers().getDescription().equals("null"))
            ((TextView) dialog.findViewById(R.id.flower)).setText(plant.getFlowers().getDescription());
        ((TextView) dialog.findViewById(R.id.Fall)).setText("Fall Color");
        if (!plant.getFallcolor().getDescription().equals("null"))
            ((TextView) dialog.findViewById(R.id.fall)).setText(plant.getFallcolor().getDescription());
        ((TextView) dialog.findViewById(R.id.Stem)).setText("Stems");
        if (!plant.getStems().getDescription().equals("null"))
            ((TextView) dialog.findViewById(R.id.stem)).setText(plant.getStems().getDescription());


        if(!plant.getHabit().getImage(0).equals("")) {
            Picasso.with(context).load(plant.getHabit().getImage(0)).into((ImageView) dialog.findViewById(R.id.habitimage));
            Picasso.with(context).load(plant.getHabit().getImage(0)).into((ImageView) dialog.findViewById(R.id.largePlantImageView));
        }
        if(!plant.getStems().getImage(0).equals("")){
            Picasso.with(context).load(plant.getStems().getImage(0)).into((ImageView)dialog.findViewById(R.id.stemimage));
            Picasso.with(context).load(plant.getStems().getImage(0)).into((ImageView) dialog.findViewById(R.id.largePlantImageView));
        }
        if(!plant.getFruits().getImage(0).equals("")) {
            Picasso.with(context).load(plant.getFruits().getImage(0)).into((ImageView) dialog.findViewById(R.id.fruitimage));
            Picasso.with(context).load(plant.getFruits().getImage(0)).into((ImageView) dialog.findViewById(R.id.largePlantImageView));
        }
        if(!plant.getFlowers().getImage(0).equals("")) {
            Picasso.with(context).load(plant.getFlowers().getImage(0)).into((ImageView) dialog.findViewById(R.id.flowerimage));
            Picasso.with(context).load(plant.getFlowers().getImage(0)).into((ImageView) dialog.findViewById(R.id.largePlantImageView));
        }
        if(!plant.getLeaves().getImage(0).equals("")) {
            Picasso.with(context).load(plant.getLeaves().getImage(0)).into((ImageView) dialog.findViewById(R.id.leavesimage));
            Picasso.with(context).load(plant.getLeaves().getImage(0)).into((ImageView)dialog.findViewById(R.id.largePlantImageView));
        }
        if(!plant.getBuds().getImage(0).equals(""))
            Picasso.with(context).load(plant.getBuds().getImage(0)).into((ImageView)dialog.findViewById(R.id.budsimage));
        if(!plant.getBark().getImage(0).equals(""))
            Picasso.with(context).load(plant.getBark().getImage(0)).into((ImageView)dialog.findViewById(R.id.barkimage));
        if(!plant.getCulture().getImage(0).equals(""))
            Picasso.with(context).load(plant.getCulture().getImage(0)).into((ImageView)dialog.findViewById(R.id.cultureimage));
        if(!plant.getFallcolor().getImage(0).equals(""))
            Picasso.with(context).load(plant.getFallcolor().getImage(0)).into((ImageView)dialog.findViewById(R.id.fallimage));

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
