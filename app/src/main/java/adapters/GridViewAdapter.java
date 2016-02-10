package adapters;

/**
 * Created by Gabby on 12/8/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import models.GridTile;
import seniorproject.arboretumapp.R;

public class GridViewAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<GridTile> listStorage;
    private Context context;

    public GridViewAdapter(Context context, List<GridTile> customizedListView){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount(){
        return listStorage.size();
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long
    getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.listview_with_image_text, parent, false);
            listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.textView);
            listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }

        listViewHolder.textInListView.setText(listStorage.get(position).getContent());
        int imageResourceId = this.context.getResources().getIdentifier(listStorage.get(position).getImageResource(), "drawable", this.context.getPackageName());
        //int imageResourceId = R.drawable.pine_grid_tile_image

        listViewHolder.imageInListView.setImageResource(imageResourceId);

        return convertView;
    }

    static class ViewHolder{
        TextView textInListView;
        ImageView imageInListView;
    }

}
