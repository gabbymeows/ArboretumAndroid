package plantsAPI;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by dquea on 3/7/2016.
 */
public class GetPlantNames extends AsyncTask<Void, Void, String> {

    private Exception exception;

    public String doInBackground(Void... urls) {


        try {
            URL url = new URL("https://www.hort.net/uiplants-api/getNearObjects?key=7Vek7WIbv9FqPoKxjD7AriIj&mycoords=40.096237,-88.217199&maxdistance=500&categoryregexp=plant");
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
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }



}
