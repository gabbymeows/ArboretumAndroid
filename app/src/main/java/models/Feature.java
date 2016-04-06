package models;

import java.util.ArrayList;

/**
 * Created by dquea on 4/5/2016.
 */
public class Feature {
    protected ArrayList<String> images;
    protected String description;


    public Feature(){
        this.images= null;
        this.description = null;



    }
    public Feature(ArrayList<String>images , String description){
        this.images = images;
        this.description = description;
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

    public int numImages(){
        if (this.images == null){
            return 0;
        }
        return this.images.size();
    }


}
