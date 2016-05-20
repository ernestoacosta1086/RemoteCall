package app.acosta.cf.com.example.ernesto.marcacionremota;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import app.acosta.cf.com.example.ernesto.marcacionremota.Services.LocationService;
import app.acosta.cf.com.example.ernesto.marcacionremota.Services.MarcacionAsynkTask;
import app.acosta.cf.com.example.ernesto.marcacionremota.Util.DateUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MarcacionActivity extends AppCompatActivity {
    @Bind(R.id.tipo_marcacion)
    Spinner spinner;
    @Bind(R.id.numero_usuario)
    EditText editText;
    @Bind(R.id.button_send)
    Button enviarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcacion);

        ButterKnife.bind(this);

//        image_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        editText.setText(prefs.getString("userNumber", ""));
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumeData();
            }
        });
    }

    @OnClick(R.id.image_back)
    public void goBack() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationService.get(getApplicationContext()).start(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationService.get(getApplicationContext()).pause();
    }

    private void consumeData() {
        if (LocationService.lastLocation == null) {
            Toast.makeText(this, getString(R.string.gps_disconnected), Toast.LENGTH_SHORT).show();
            return;
        }

        ButterKnife.bind(this);

        MarcacionAsynkTask marcacionTask = new MarcacionAsynkTask(getBaseContext());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String usuario = prefs.getString(getString(R.string.key_usuario), getString(R.string.text_correo_example));
        String clave = prefs.getString(getString(R.string.key_clave), "");

        String numeroUsuario = editText.getText().toString();

        String lat = Double.toString(LocationService.lastLocation.getLatitude());
        String lon = Double.toString(LocationService.lastLocation.getLongitude());

        String fecha = DateUtil.convertCurrentDateToString().toString();
        String hora = DateUtil.convertCurrentTimeToString().toString();

        String tipoMarcacion = String.valueOf(spinner.getSelectedItem());

        switch (tipoMarcacion) {
            case "E/S Empresa letra I":
                tipoMarcacion = "I";
                break;
            case "E/S Almuerzo letra O":
                tipoMarcacion = "O";
                break;
            case "E/S Permiso letra P":
                tipoMarcacion = "P";
                break;
            case "Seguimiento letra S":
                tipoMarcacion = "S";
                break;
        }
        marcacionTask.execute(usuario, clave, numeroUsuario, lat, lon, fecha, hora, tipoMarcacion);
    }
}


