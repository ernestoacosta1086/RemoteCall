package app.acosta.cf.com.example.ernesto.marcacionremota.Services;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import app.acosta.cf.com.example.ernesto.marcacionremota.Models.Daos;
import app.acosta.cf.com.example.ernesto.marcacionremota.Models.Marcacion;
import app.acosta.cf.com.example.ernesto.marcacionremota.R;

/**
 * Created by Ernesto on 9/2/2016.
 */
public class MarcacionAsynkTask extends AsyncTask<String, Void, Integer> {

    private Context context;
    private Marcacion marcacion;
    private MarcacionCallback callback;

    public MarcacionAsynkTask(Context context) {
        this.context = context;
    }

    public MarcacionAsynkTask(Context context, Marcacion marcacion, MarcacionCallback callback) {
        this.context = context;
        this.marcacion = marcacion;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Daos.init(context);
    }

    @Override
    protected Integer doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        String BASE_URL = "http://asistencia.controlhidrocarburos.gob.ec/permisos/api/v1/marcacion";

        try {
            if (marcacion == null) {
                marcacion = new Marcacion();
                marcacion.setUsuario(params[0]);
                marcacion.setClave(params[1]);
                marcacion.setNumero_usuario(params[2]);
                marcacion.setCoordenada_lon(params[3]);
                marcacion.setCoordenada_lat(params[4]);
                marcacion.setFecha(params[5]);
                marcacion.setHora(params[6]);
                marcacion.setTipo_marcacion(params[7]);
            }

            Uri builtUri = Uri.parse(BASE_URL);
            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            urlConnection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());

            wr.writeBytes("meta[usuario]=" + marcacion.getUsuario()
                    + "&meta[clave]=" + marcacion.getClave()
                    + "&data[numero_usuario]=" + marcacion.getNumero_usuario()
                    + "&data[coordenada_lon]=" + marcacion.getCoordenada_lon()
                    + "&data[coordenada_lat]=" + marcacion.getCoordenada_lat()
                    + "&data[fecha]=" + marcacion.getFecha()
                    + "&data[hora]=" + marcacion.getHora()
                    + "&data[tipo_marcacion]=" + marcacion.getTipo_marcacion());

            wr.flush();
            wr.close();

            int code;
            try {
                code = urlConnection.getResponseCode();
            } catch (IOException e) {
                code = urlConnection.getResponseCode();
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


    @Override
    protected void onPostExecute(Integer result) {
        Toast toast;
        if (result != null && result == 200) {
            toast = Toast.makeText(context, context.getString(R.string.marcacion_success), Toast.LENGTH_SHORT);
            Daos.marcacionDao.deleteById(marcacion.getId());
        } else if (result != null && result == 401) {
            toast = Toast.makeText(context, context.getString(R.string.autentication_error), Toast.LENGTH_SHORT);
        } else if (result != null && result == 403) {
            toast = Toast.makeText(context, context.getString(R.string.number_error), Toast.LENGTH_SHORT);
            //Hacer un update a la bd con un cuadro de texto
            //marcacion = Daos.marcacionDao.getById(marcacion.getId());
        } else if (result != null && result == 400) {
            toast = Toast.makeText(context, context.getString(R.string.number_error), Toast.LENGTH_SHORT);
            //Hacer un update a la bd con un cuadro de texto
            //marcacion = Daos.marcacionDao.getById(marcacion.getId());
        } else {
            toast = Toast.makeText(context, context.getString(R.string.marcacion_error), Toast.LENGTH_SHORT);
            Daos.marcacionDao.saveOrUpdate(marcacion);
        }
        toast.show();
        if (callback != null) {
            callback.onFinish();
        }
    }

    public interface MarcacionCallback {
        void onFinish();
    }
}
