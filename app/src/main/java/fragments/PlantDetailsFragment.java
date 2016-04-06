package fragments;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
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
 * Created by Gabby on 2/3/2016.
 */
public class PlantDetailsFragment extends PlantGridFragment{

    public PlantDetailsFragment() {
    }

    @Override
    public void onSaveInstanceState(final Bundle state){
        super.onSaveInstanceState(state);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.detail_plant_view, container, false);
        //rootView.setVerticalScrollBarEnabled(true);
        rootView.setScrollContainer(true);

        ImageView image = (ImageView) rootView.findViewById(R.id.largePlantImageView);
        image.setImageResource(R.drawable.blackhaw_viburnum_tile);

        TextView name = (TextView) rootView.findViewById(R.id.plantName);
        name.setText("Blackhaw Viburnum Tree");
        TextView desc1 = (TextView) rootView.findViewById(R.id.plantDescription1);
        desc1.setText("This is a description and it will be fixed later. This is a description and it will be fixed later. This is a description and it will be fixed later. This is a description and it will be fixed later. ");

        TextView header2 = (TextView) rootView.findViewById(R.id.plantHeader2);
        header2.setText("Plant History");
        TextView desc2 = (TextView) rootView.findViewById(R.id.plantDescription2);
        desc2.setText("This is a description and it will be fixed later. This is a description and it will be fixed later. This is a description and it will be fixed later. This is a description and it will be fixed later. ");

        TextView header3 = (TextView) rootView.findViewById(R.id.plantHeader3);
        header3.setText("Fun Facts");
        TextView desc3 = (TextView) rootView.findViewById(R.id.plantDescription3);
        desc3.setText("This is a description and it will be fixed later. This is a description and it will be fixed later. This is a description and it will be fixed later. This is a description and it will be fixed later. ");



        return rootView;
    }

}
