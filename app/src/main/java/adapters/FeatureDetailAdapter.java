package adapters;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import models.Feature;
import models.GridTile;
import seniorproject.arboretumapp.R;

/**
 * Created by Gabby on 5/4/2016.
 */
public class FeatureDetailAdapter extends ArrayAdapter<Feature> {

    public FeatureDetailAdapter(Context context, ArrayList<Feature> features){
        super(context, R.layout.feature_details_item, features);
    }

    @Override
    public View getView(int pos, View customView, ViewGroup parent) {
        LayoutInflater listInflater = LayoutInflater.from(getContext());
        customView = listInflater.inflate(R.layout.feature_details_item, parent, false);

        Feature feature = getItem(pos);

        TextView ftTitle = (TextView) customView.findViewById(R.id.ft_title);
        TextView ftText = (TextView) customView.findViewById(R.id.ft_text);
        ImageView ftImage = (ImageView) customView.findViewById(R.id.ft_image);

        ftTitle.setText(feature.getTitle());

        if (!feature.getImage(0).equals("")) {
            Picasso.with(parent.getContext()).load(feature.getImage(0)).into(ftImage);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ftImage.setForegroundGravity(Gravity.CENTER);
            }

        }

        if (!(feature.getDescription().equals("null") || feature.getDescription().equals(""))){
            String displayString = feature.getDescription().replace("[TAB]", "");
            displayString = displayString.replace("[ITALIC]", "");
            displayString = displayString.replace("}", "").replace("{", "").replaceAll("  ", " ");
            ftText.setText(displayString);

        }
        else
            ftText.setText("Only an image is available as of now.");

        return customView;
    }

}
