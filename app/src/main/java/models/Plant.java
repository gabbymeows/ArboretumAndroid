package models;

import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dquea on 4/5/2016.
 */
public class Plant {

    protected String code;
    protected ArrayList<Pair< Float, Float>> coords;
    private String sci_name;
    private String com_name;
    private String fam_name;
    private Feature fruits;
    private Feature leaves;
    private Feature buds;
    private Feature fallcolor;
    private Feature bark;
    private Feature flowers;
    private Feature stems;
    private Feature culture;
    private Habit habit;
    private String thumbnail;


    public Plant(String code, String coords){
        this.code = code;
        this.coords = new ArrayList<Pair<Float,Float>>();
        this.addNewLocation(coords);
        this.thumbnail = "";
    }

    public Habit getHabit(){
        return this.habit;
    }

    public Plant(String code, JSONArray coords){
        try {
            this.code = code;
            this.thumbnail = "";
            this.coords = new ArrayList<Pair<Float,Float>>();
            for (int i = 0; i < coords.length(); i++) {
                this.addNewLocation(coords.get(i).toString());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addNewLocation(String coords){
        this.coords.add(stringToCoords(coords));
    }

    public void addNewLocation(JSONArray coords){
        try {
            for (int i = 0; i < coords.length(); i++) {
                this.addNewLocation(coords.get(i).toString());
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public ArrayList<Pair<Float,Float>> getCoords(){
        return this.coords;
    }

    public JSONObject getJSON(){
        try {
            JSONObject json = new JSONObject();
            json.put("code", this.code);
            json.put("com_name", this.com_name);
            json.put("fam_name", this.fam_name);
            json.put("sci_name", this.sci_name);
            json.put("thumbnail", this.thumbnail);

            JSONObject fruits = new JSONObject();
            JSONObject leaves = new JSONObject();
            JSONObject bark = new JSONObject();
            JSONObject buds = new JSONObject();
            JSONObject fallcolor = new JSONObject();
            JSONObject flowers = new JSONObject();
            JSONObject stems = new JSONObject();
            JSONObject culture = new JSONObject();
            JSONObject habit = new JSONObject();

            JSONArray coordsJSON = new JSONArray();
            JSONArray fruitsJSON = new JSONArray();
            JSONArray leavesJSON = new JSONArray();
            JSONArray barkJSON = new JSONArray();
            JSONArray budsJSON = new JSONArray();
            JSONArray fallcolorJSON = new JSONArray();
            JSONArray flowersJSON = new JSONArray();
            JSONArray stemsJSON = new JSONArray();
            JSONArray cultureJSON = new JSONArray();
            JSONArray habitJSON = new JSONArray();

            for (int i = 0; i < this.coords.size(); i++) {
                coordsJSON.put(this.coords.get(i).first + "," + this.coords.get(i).second);
            }
            for (int i = 0; i < this.fruits.numImages(); i++) {
                fruitsJSON.put(this.fruits.getImage(i));
            }
            for (int i = 0; i < this.leaves.numImages(); i++) {
                leavesJSON.put(this.leaves.getImage(i));
            }
            for (int i = 0; i < this.bark.numImages(); i++) {
                barkJSON.put(this.bark.getImage(i));
            }
            for (int i = 0; i < this.buds.numImages(); i++) {
                budsJSON.put(this.buds.getImage(i));
            }
            for (int i = 0; i < this.fallcolor.numImages(); i++) {
                fallcolorJSON.put(this.fallcolor.getImage(i));
            }
            for (int i = 0; i < this.flowers.numImages(); i++) {
                flowersJSON.put(this.flowers.getImage(i));
            }
            for (int i = 0; i < this.stems.numImages(); i++) {
                stemsJSON.put(this.stems.getImage(i));
            }
            for (int i = 0; i < this.culture.numImages(); i++) {
                cultureJSON.put(this.culture.getImage(i));

            }
            for (int i = 0; i < this.habit.numImages(); i++){
                habitJSON.put(this.habit.getImage(i));
            }


            json.put("coords", coordsJSON);

            fruits.put("images", fruitsJSON);
            fruits.put("description", this.fruits.getDescription());

            leaves.put("images", leavesJSON);
            leaves.put("description", this.leaves.getDescription());

            bark.put("images", barkJSON);
            bark.put("description", this.bark.getDescription());

            buds.put("images", budsJSON);
            buds.put("description", this.buds.getDescription());

            fallcolor.put("images", fallcolorJSON);
            fallcolor.put("description", this.fallcolor.getDescription());

            flowers.put("images", flowersJSON);
            flowers.put("description", this.flowers.getDescription());

            stems.put("images", stemsJSON);
            stems.put("description", this.stems.getDescription());

            culture.put("images", cultureJSON);
            culture.put("description", this.culture.getDescription());

            habit.put("images", habitJSON);
            habit.put("texture", this.habit.getTexture());
            habit.put("size", this.habit.getSize());
            habit.put("color", this.habit.getColor());
            habit.put("hardiness", this.habit.getHardiness());
            habit.put("habitat", this.habit.getHabitat());
            habit.put("habit", this.habit.getHabit());
            habit.put("description", this.habit.getDescription());

            json.put("fruits", fruits);
            json.put("leaves", leaves);
            json.put("bark", bark);
            json.put("buds", buds);
            json.put("fallcolor", fallcolor);
            json.put("stems", stems);
            json.put("flowers", flowers);
            json.put("culture", culture);
            json.put("habit", habit);









            return json;
        }
        catch(Exception e){
            System.out.println("GETTING JSON FAILED....");
            e.printStackTrace();
            return null;
        }
    }

    public void getFeaturesFromJson(JSONObject info){
        try {
            this.setLeaves(info.getJSONObject("leaves"));

            this.setHabit(info.getJSONObject("habit"));
            this.setFlowers(info.getJSONObject("flowers"));
            this.setBark(info.getJSONObject("bark"));
            this.setFruits(info.getJSONObject("fruits"));
            this.setBuds(info.getJSONObject("buds"));
            this.setStems(info.getJSONObject("stems"));
            this.setFallcolor(info.getJSONObject("fallcolor"));
            this.com_name = info.get("com_name").toString();
            this.fam_name = info.get("fam_name").toString();
            this.sci_name = info.get("sci_name").toString();
            this.setCulture(info.getJSONObject("culture"));


        }
        catch(Exception e){
            System.out.println("Failure to extract features from JSON");
        }



    }

    private Pair<Float, Float> stringToCoords(String str){
        String[] xAndY = str.split(",");
        return new Pair<Float, Float>(Float.parseFloat(xAndY[0]), Float.parseFloat(xAndY[1]));

    }

    public void setLeaves(JSONObject json) {
        try {
            ArrayList<String> images = new ArrayList<String>();
            for (int i = 0; i < json.getJSONArray("images").length(); i++) {
                String image = json.getJSONArray("images").get(i).toString();
                if(this.thumbnail == null|| this.thumbnail.equals("")){
                    this.thumbnail = image;
                }
                images.add(image);
            }
            this.leaves = new Feature(images, json.get("description").toString());
        }
        catch (Exception e){}
    }

    public void setBark(JSONObject json) {
        try {
            ArrayList<String> images = new ArrayList<String>();
            for (int i = 0; i < json.getJSONArray("images").length(); i++) {
                String image = json.getJSONArray("images").get(i).toString();
                if(this.thumbnail == null|| this.thumbnail.equals("")){
                    this.thumbnail = image;
                }
                images.add(image);
            }
            this.bark = new Feature(images, json.get("description").toString());
        }
        catch (Exception e){}
    }

    public void setCulture(JSONObject json) {
        try {
            ArrayList<String> images = new ArrayList<String>();
            for (int i = 0; i < json.getJSONArray("images").length(); i++) {
                String image = json.getJSONArray("images").get(i).toString();
                if(this.thumbnail == null|| this.thumbnail.equals("")){
                    this.thumbnail = image;
                }
                images.add(image);
            }
            this.culture = new Feature(images, json.get("description").toString());
        }
        catch (Exception e){}
    }

    public void setStems(JSONObject json) {
        try {
            ArrayList<String> images = new ArrayList<String>();
            for (int i = 0; i < json.getJSONArray("images").length(); i++) {
                String image = json.getJSONArray("images").get(i).toString();
                if(this.thumbnail == null|| this.thumbnail.equals("")){
                    this.thumbnail = image;
                }
                images.add(image);
            }
            this.stems = new Feature(images, json.get("description").toString());
        }
        catch (Exception e){}
    }

    public void setFruits(JSONObject json) {
        try {
            ArrayList<String> images = new ArrayList<String>();
            for (int i = 0; i < json.getJSONArray("images").length(); i++) {
                String image = json.getJSONArray("images").get(i).toString();
                if(this.thumbnail == null|| this.thumbnail.equals("")){
                    this.thumbnail = image;
                }
                images.add(image);
            }
            this.fruits = new Feature(images, json.get("description").toString());
        }
        catch (Exception e){}
    }

    public void setBuds(JSONObject json) {
        try {
            ArrayList<String> images = new ArrayList<String>();
            for (int i = 0; i < json.getJSONArray("images").length(); i++) {
                String image = json.getJSONArray("images").get(i).toString();
                if(this.thumbnail == null|| this.thumbnail.equals("")){
                    this.thumbnail = image;
                }
                images.add(image);
            }
            this.buds = new Feature(images, json.get("description").toString());
        }
        catch (Exception e){}
    }

    public void setFallcolor(JSONObject json) {
        try {
            ArrayList<String> images = new ArrayList<String>();
            for (int i = 0; i < json.getJSONArray("images").length(); i++) {
                String image = json.getJSONArray("images").get(i).toString();
                if(this.thumbnail == null|| this.thumbnail.equals("")){
                    this.thumbnail = image;
                }
                images.add(image);
            }
            this.fallcolor = new Feature(images, json.get("description").toString());
        }
        catch (Exception e){}
    }

    public void setFlowers(JSONObject json) {
        try {
            ArrayList<String> images = new ArrayList<String>();
            for (int i = 0; i < json.getJSONArray("images").length(); i++) {
                String image = json.getJSONArray("images").get(i).toString();
                if(this.thumbnail == null || this.thumbnail.equals("")){
                    this.thumbnail = image;
                }
                images.add(image);
            }
            this.flowers = new Feature(images, json.get("description").toString());
        }
        catch (Exception e){}
    }

    public void setHabit(JSONObject json) {
        try {


            ArrayList<String> images = new ArrayList<String>();
            for (int i = 0; i < json.getJSONArray("images").length(); i++) {
                String image = json.getJSONArray("images").get(i).toString();
                if(this.thumbnail == null || this.thumbnail.equals("")){
                    this.thumbnail = image;
                }
                images.add(image);
            }
            if(json.has("texture")) {
                String texture = json.get("texture").toString();
                String color = json.get("color").toString();
                String hardiness = json.get("hardiness").toString();
                String habitat = json.get("habitat").toString();
                String habit = json.get("habit").toString();
                String size = json.get("size").toString();
                this.habit = new Habit(images, json.get("description").toString(),texture,color,hardiness,habitat,habit, size);
            }

            else
            {
                this.habit = new Habit(images, "","","","","","", "");
            }


        }
        catch (Exception e){

            e.printStackTrace();

        }
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public String getSciName() {
        return this.sci_name;
    }

    public String getComName(){
        return this.com_name;
    }

    public Feature getLeaves(){
        return this.leaves;
    }

    public Feature getFruits(){
        return this.fruits;
    }

    public Feature getBuds(){
        return this.buds;
    }

    public Feature getBark(){
        return this.bark;
    }

    public Feature getFlowers(){
        return this.flowers;
    }

    public Feature getFallcolor(){
        return this.fallcolor;
    }

    public Feature getStems(){
        return this.stems;
    }

    public Feature getCulture(){
        return this.culture;
    }
}
