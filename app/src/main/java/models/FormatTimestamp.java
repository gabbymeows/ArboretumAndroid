package models;

import android.util.Log;

import java.sql.Timestamp;

/**
 * Created by Gabby on 4/11/2016.
 */
public class FormatTimestamp {

    public static String getDisplayDate(String t){
        String year = t.substring(0,4);
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String month = months[new Integer(t.substring(6,7))-1];
        String day = t.substring(8,10);
//        if (day.charAt(0) == '0')
//            day = day.substring(1,1);6

        String ret = month +" "+day+", "+ year+": ";
        Log.v("yoo2", ret);
        return ret;
    }
}
