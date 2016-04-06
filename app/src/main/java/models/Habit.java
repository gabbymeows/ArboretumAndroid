package models;

import java.util.ArrayList;

/**
 * Created by dquea on 4/6/2016.
 */
public class Habit extends Feature {

    String texture;
    String color;
    String hardiness;
    String habitat;
    String habit;
    String size;

    public Habit(ArrayList<String> images, String description, String texture, String color, String hardiness, String habitat, String habit,String size){

        this.images = images;
        this.description = description;
        this.texture = texture;
        this.images = images;
        this.size = size;
        this.color = color;
        this.hardiness = hardiness;
        this.habitat = habitat;
        this.habit=habit;

    }

    public String getTexture() {
        return texture;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getHardiness() {
        return hardiness;
    }

    public String getHabitat() {
        return habitat;
    }

    public String getHabit() {
        return habit;
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
