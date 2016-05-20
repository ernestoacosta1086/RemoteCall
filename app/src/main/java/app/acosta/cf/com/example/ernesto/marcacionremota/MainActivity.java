package app.acosta.cf.com.example.ernesto.marcacionremota;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import app.acosta.cf.com.example.ernesto.marcacionremota.Models.Daos;
import app.acosta.cf.com.example.ernesto.marcacionremota.Services.LoginAsynkTask;
import butterknife.Bind;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity {
    @Bind(R.id.textview_user)
    EditText usuario;
    @Bind(R.id.textview_password)
    EditText clave;
    @Bind(R.id.checked_image)
    ImageView checkedImage;
    @Bind((R.id.button_aceppt))
    Button acceptButton;
    @Bind(R.id.button_marcacion)
    Button marcButton;
    @Bind(R.id.button_pending)
    Button pendingButton;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Daos.init(getApplicationContext());
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setUpButton();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String usuarioStored = prefs.getString(getString(R.string.key_usuario), getString(R.string.text_correo_example));
        String claveStored = prefs.getString(getString(R.string.key_clave), "");
        boolean authenticated = prefs.getBoolean(getString(R.string.authenticated), false);

        if (authenticated) {
            checkedImage.setImageResource(R.drawable.ic_done_green_24dp);
        } else {
            checkedImage.setImageResource(android.R.drawable.ic_delete);
        }

        usuario.setText(usuarioStored);
        clave.setText(claveStored);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consumeData();
            }
        });

        marcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean authenticated = prefs.getBoolean(getString(R.string.authenticated), false);
                if (authenticated) {
                    Intent intent = new Intent(getBaseContext(), MarcacionActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.no_authenticated), Toast.LENGTH_LONG).show();
                }
            }
        });


            pendingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    boolean authenticated = prefs.getBoolean(getString(R.string.authenticated), false);
                    if (authenticated) {
                        Intent intent = new Intent(getBaseContext(), PendingActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.no_authenticated), Toast.LENGTH_LONG).show();
                    }

                    //enviarMarcacioesPendientes();
                }
            });
        }

    private void consumeData() {
        progressBar.setVisibility(View.VISIBLE);
        checkedImage.setVisibility(View.GONE);

        LoginAsynkTask loginTask = new LoginAsynkTask(getBaseContext(), progressBar, checkedImage);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String usuario = String.valueOf(((EditText) findViewById(R.id.textview_user)).getText());
        String clave = String.valueOf(((EditText) findViewById(R.id.textview_password)).getText());

        prefs.edit().putString(getString(R.string.key_usuario), usuario).apply();
        prefs.edit().putString(getString(R.string.key_clave), clave).apply();

        loginTask.execute(usuario, clave);
    }

    @Override
    protected void onResume() {
        TextView textViewUser = (TextView) findViewById(R.id.textview_user);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        textViewUser.setText(sharedPrefs.getString(getString(R.string.key_usuario), getString(R.string.text_correo_example)));

        TextView textViewIdentification = (TextView) findViewById(R.id.textview_password);
        textViewIdentification.setText(sharedPrefs.getString(getString(R.string.key_clave), getString(R.string.text_clave)));
        setUpButton();
        super.onResume();
    }

    private void setUpButton() {
        Button confButton = (Button) findViewById(R.id.button_pending);
        confButton.setText(String.format(getString(R.string.text_pendientes_button), Daos.marcacionDao.count()));
    }


    //Verified internet connection
//    public boolean connectInternet(){
//        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnectedOrConnecting()){
//            return true;
//        }else {
//            return false;
//        }
//    }
}
