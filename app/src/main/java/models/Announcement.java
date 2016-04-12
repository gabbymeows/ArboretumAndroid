package models;

import java.sql.Timestamp;

/**
 * Created by Gabby on 4/11/2016.
 */
public class Announcement {

    private String message;
    private Timestamp timestamp;

    public Announcement(){

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


}
