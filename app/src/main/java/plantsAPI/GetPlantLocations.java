package plantsAPI;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by dquea on 4/6/2016.
 */
public class GetPlantLocations extends AsyncTask<String, Void, String> {
    private Exception exception;

    public String doInBackground(String... urls) {


        try {
            URL url = null;
            //if(urls.length <= 3)
                url = new URL("https://www.hort.net/uiplants-api/getObjectLocations?key=lDVPv70zfKus5BxzPT0T2Gw6&mycoords=40.096237,-88.217199&maxdistance=999999999&regexp=");

           // else
               // url = new URL("https://www.hort.net/uiplants-api/getObjectLocations?key=lDVPv70zfKus5BxzPT0T2Gw6&mycoords="+urls[0]+","+urls[1]+"&maxdistance="+urls[2]+"&regexp=");

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
            catch(Exception e) {
                e.printStackTrace();
                return null;
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
