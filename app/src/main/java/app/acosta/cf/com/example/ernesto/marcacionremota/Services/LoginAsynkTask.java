package app.acosta.cf.com.example.ernesto.marcacionremota.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import app.acosta.cf.com.example.ernesto.marcacionremota.R;

/**
 * Created by Ernesto on 8/2/2016.
 */
public class LoginAsynkTask extends AsyncTask<String, Void, Integer> {
    private Context context;
    private ProgressBar progressBar;
    private ImageView checkedImage;
    private String numero = "";
    private String error = "";

    public LoginAsynkTask(Context context, ProgressBar progressBar, ImageView checkedImage) {
        this.context = context;
        this.progressBar = progressBar;
        this.checkedImage = checkedImage;
    }

    @Override
    protected Integer doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        String BASE_URL = "http://asistencia.controlhidrocarburos.gob.ec/permisos/api/v1/login";

        try {
            Uri builtUri = Uri.parse(BASE_URL);
            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes("usuario=" + params[0] + "&clave=" + params[1]);
            wr.flush();
            wr.close();

            int code;
            try {
                code = urlConnection.getResponseCode();
            } catch (IOException e) {
                code = urlConnection.getResponseCode();
            }

            try {
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return code;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return code;
                }
                String forecastJsonStr = buffer.toString();
                numero = getUserNumber(forecastJsonStr);
            }catch (IOException e) {
                return code;
            }

            return code;

        } catch (IOException e) {
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private String getUserNumber(String result) {
        JSONObject resultJson = null;
        try {
            resultJson = new JSONObject(result);
            JSONObject userJson = resultJson.getJSONObject("usuario");
            return userJson.getString("numero");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    protected void onPostExecute(Integer result) {
        progressBar.setVisibility(View.GONE);
        Toast toast = null;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("userNumber", numero).apply();

        if (result != null) {
            if (result == 200) {
                checkedImage.setImageResource(R.drawable.ic_done_green_24dp);
                prefs.edit().putBoolean(context.getString(R.string.authenticated), true).apply();
                toast = Toast.makeText(context, context.getString(R.string.autentication_success), Toast.LENGTH_SHORT);
            }
            if (result == 400 || result == 401) {
                toast = Toast.makeText(context, context.getString(R.string.autentication_failed), Toast.LENGTH_SHORT);
                checkedImage.setImageResource(android.R.drawable.ic_delete);
            }
        } else {
            checkedImage.setImageResource(android.R.drawable.ic_delete);
            prefs.edit().putBoolean(context.getString(R.string.authenticated), false).apply();
            toast = Toast.makeText(context, context.getString(R.string.autentication_no_conexion), Toast.LENGTH_SHORT);
        }
        checkedImage.setVisibility(View.VISIBLE);
        toast.show();
    }
}
