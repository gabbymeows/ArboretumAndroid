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
public class GetPlantInfo extends AsyncTask<String, Void, String> {


    @Override
    public String doInBackground(String... code) {

        try {
            URL url = new URL("https://www.hort.net/uiplants-api/getPlant?key=7Vek7WIbv9FqPoKxjD7AriIj&code=" + code[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    line.replace("[TAB]", "").replace("[ITALIC] {", "").replace("}","").replace("  ", " ").replace("null","");
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();

                return stringBuilder.toString();
            }
            catch(Exception e){
                Log.e("that was dumb...",e.getMessage(),e);
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
