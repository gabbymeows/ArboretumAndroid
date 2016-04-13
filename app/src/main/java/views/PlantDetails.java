package views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.Image;
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

import java.util.concurrent.ExecutionException;

import models.Plant;
import models.PlantMap;
import plantsAPI.DownloadImageTask;
import seniorproject.arboretumapp.R;

/**
 * Created by Gabby on 4/8/2016.
 */
public class PlantDetails {

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

        ImageView plantImage = (ImageView) dialog.findViewById(R.id.largePlantImageView);

        Bitmap imageHabit = null;
        Bitmap imageFlower = null;
        Bitmap imageLeaves = null;
        Bitmap imageBuds = null;
        Bitmap imageFruit = null;
        Bitmap imageBark = null;
        Bitmap imageCulture = null;
        Bitmap imageFall = null;
        Bitmap imageStem = null;

        //try {
        if(!plant.getHabit().getImage(0).equals(""))
            Picasso.with(context).load(plant.getHabit().getImage(0)).into((ImageView)dialog.findViewById(R.id.habitimage));
            //imageHabit = new DownloadImageTask((ImageView)dialog.findViewById(R.id.habitimage)).execute(plant.getHabit().getImage(0)).get();
        if(!plant.getLeaves().getImage(0).equals(""))
            Picasso.with(context).load(plant.getLeaves().getImage(0)).into((ImageView)dialog.findViewById(R.id.leavesimage));
            //imageLeaves = new DownloadImageTask((ImageView)dialog.findViewById(R.id.leavesimage)).execute(plant.getLeaves().getImage(0)).get();
        if(!plant.getBuds().getImage(0).equals(""))
            Picasso.with(context).load(plant.getBuds().getImage(0)).into((ImageView)dialog.findViewById(R.id.budsimage));
            //imageBuds = new DownloadImageTask((ImageView)dialog.findViewById(R.id.budsimage)).execute(plant.getBuds().getImage(0)).get();
        if(!plant.getFlowers().getImage(0).equals(""))
            Picasso.with(context).load(plant.getFlowers().getImage(0)).into((ImageView)dialog.findViewById(R.id.flowerimage));
            //imageFlower = new DownloadImageTask((ImageView)dialog.findViewById(R.id.flowerimage)).execute(plant.getFlowers().getImage(0)).get();
        if(!plant.getFruits().getImage(0).equals(""))
            Picasso.with(context).load(plant.getFruits().getImage(0)).into((ImageView)dialog.findViewById(R.id.fruitimage));
           // imageFruit = new DownloadImageTask((ImageView)dialog.findViewById(R.id.fruitimage)).execute(plant.getFruits().getImage(0)).get();
        if(!plant.getBark().getImage(0).equals(""))
            Picasso.with(context).load(plant.getBark().getImage(0)).into((ImageView)dialog.findViewById(R.id.barkimage));
           // imageBark = new DownloadImageTask((ImageView)dialog.findViewById(R.id.barkimage)).execute(plant.getBark().getImage(0)).get();
        if(!plant.getCulture().getImage(0).equals(""))
            Picasso.with(context).load(plant.getCulture().getImage(0)).into((ImageView)dialog.findViewById(R.id.cultureimage));
            //imageCulture = new DownloadImageTask((ImageView)dialog.findViewById(R.id.cultureimage)).execute(plant.getCulture().getImage(0)).get();
        if(!plant.getFallcolor().getImage(0).equals(""))
            Picasso.with(context).load(plant.getFallcolor().getImage(0)).into((ImageView)dialog.findViewById(R.id.fallimage));
            //imageFall = new DownloadImageTask((ImageView)dialog.findViewById(R.id.fallimage)).execute(plant.getFallcolor().getImage(0)).get();
        if(!plant.getStems().getImage(0).equals(""))
            Picasso.with(context).load(plant.getStems().getImage(0)).into((ImageView)dialog.findViewById(R.id.stemimage));
            //imageStem = new DownloadImageTask((ImageView)dialog.findViewById(R.id.stemimage)).execute(plant.getStems().getImage(0)).get();
            //imageHabit = new DownloadImageTask(plantImage).execute(plant.getHabit().getImage(0)).get();
       // } catch (InterruptedException e) {
        //    e.printStackTrace();
       // } catch (ExecutionException e) {
       //     e.printStackTrace();
       // }
        //((ImageView)dialog.findViewById(R.id.habitimage)).setImageBitmap(imageHabit);
        //((ImageView)dialog.findViewById(R.id.leavesimage)).setImageBitmap(imageLeaves);
        //((ImageView)dialog.findViewById(R.id.budsimage)).setImageBitmap(imageBuds);
       // ((ImageView)dialog.findViewById(R.id.flowerimage)).setImageBitmap(imageFlower);
        //((ImageView)dialog.findViewById(R.id.fruitimage)).setImageBitmap(imageFruit);
        //((ImageView)dialog.findViewById(R.id.barkimage)).setImageBitmap(imageBark);
        //((ImageView)dialog.findViewById(R.id.cultureimage)).setImageBitmap(imageCulture);
        //((ImageView)dialog.findViewById(R.id.fallimage)).setImageBitmap(imageFall);
        //((ImageView)dialog.findViewById(R.id.stemimage)).setImageBitmap(imageStem);


        //plantImage.setImageBitmap(imageLeaves);


        final ImageButton like = (ImageButton) dialog.findViewById(R.id.likeButton);
        //like.setImageResource(R.drawable.like_unfilled_24);
        if(!PlantMap.getInstance().getFavoritePlantsList().contains(plantCode))
            like.setImageResource(R.drawable.like_unfilled_48);
        else
            like.setImageResource(R.drawable.like_filled_48);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(!PlantMap.getInstance().getFavoritePlantsList().contains(plantCode)) {
                    like.setImageResource(R.drawable.like_filled_48);
                    PlantMap.getInstance().getFavoritePlantsList().add(plantCode);
                    Toast.makeText(context, plantname +" added to favorites!", Toast.LENGTH_SHORT).show();
               }
                else {
                    like.setImageResource(R.drawable.like_unfilled_48);
                    PlantMap.getInstance().getFavoritePlantsList().remove(plantCode);
                    Toast.makeText(context, plantname + " removed from favorites.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //new DownloadImageTask(plantImage).execute(tileClicked.getImageResource());

        return dialog;

    }
}
