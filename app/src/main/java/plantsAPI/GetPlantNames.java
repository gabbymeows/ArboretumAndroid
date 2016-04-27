package plantsAPI;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import models.PlantMap;

/**
 * Created by dquea on 3/7/2016.
 */
public class GetPlantNames extends AsyncTask<String, Void, String> {

    private Exception exception;


    public String doInBackground(String... coords) {

        String latlon = coords[0];

        try {

            URL url = new URL("https://www.hort.net/uiplants-api/getNearObjects?key=7Vek7WIbv9FqPoKxjD7AriIj&mycoords="+ latlon +"&maxdistance=500&categoryregexp=plant");
            //URL url = new URL("https://www.hort.net/uiplants-api/getNearObjects?key=7Vek7WIbv9FqPoKxjD7AriIj&mycoords=40.096237,-88.217199&maxdistance=500&categoryregexp=plant");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();

                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String nearbyPlants) {
        try {
            JSONObject jsonObj = new JSONObject(nearbyPlants);

            ArrayList<String> names = new ArrayList<String>();
            Iterator<String> it = jsonObj.keys();
            for (int i = 0; i < jsonObj.length(); i++) {
                names.add(jsonObj.getJSONObject(it.next()).get("code").toString());

            }

            PlantMap.getInstance().setNearPlants(names);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        PlantMap.getInstance().redrawGridview();
    }



}
