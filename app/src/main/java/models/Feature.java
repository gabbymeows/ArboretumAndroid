package models;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dquea on 4/5/2016.
 */
public class Feature {
    protected ArrayList<String> images;
    protected String description;
    protected Bitmap image;
    protected String title;


    public Feature(){
        this.images= null;
        this.description = null;
        this.image = null;


    }

    public void setImage(Bitmap image){
        this.image = image;
    }

    public ArrayList<String> getImages(){
        return this.images;
    }

    public Bitmap getImage(){
        return this.image;
    }

    public Feature(ArrayList<String>images , String description, String title){
        this.images = images;
        this.description = description;
        this.title = title;
    }

    public String getImage(int i){
        if (this.images == null){
            return "";
        }
        if (this.images.size() > i)
        return images.get(i);
        else return "";
    }

    public String getDescription(){
        return this.description;
    }

    public String getTitle(){
        return this.title;
    }

    public int numImages(){
        if (this.images == null){
            return 0;
        }
        return this.images.size();
    }

    public void setTitle(String title){
        this.title = title;
    }

}
