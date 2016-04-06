package models;

/**
 * Created by Gabby on 12/8/2015.
 */
public class GridTile {

    private String content;
    private String imageResource;
    private String plantCode;

    public GridTile(String content, String imageResource, String plantCode) {
        this.content = content;
        this.imageResource = imageResource;
        this.plantCode = plantCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getPlantCode(){
        return this.plantCode;
    }

    public void setPlantCode(String plantCode){
        this.plantCode = plantCode;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

}
