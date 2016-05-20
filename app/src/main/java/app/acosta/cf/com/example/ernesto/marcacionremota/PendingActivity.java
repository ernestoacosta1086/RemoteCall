package app.acosta.cf.com.example.ernesto.marcacionremota;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import app.acosta.cf.com.example.ernesto.marcacionremota.Models.Daos;
import app.acosta.cf.com.example.ernesto.marcacionremota.Models.Marcacion;
import app.acosta.cf.com.example.ernesto.marcacionremota.Services.MarcacionAsynkTask;
import app.acosta.cf.com.example.ernesto.marcacionremota.Util.MarcacionAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PendingActivity extends AppCompatActivity {

    @Bind(R.id.marcaciones_recycler_view)
    RecyclerView recycler;

    private RecyclerView.Adapter adapter;
    private List<Marcacion> marcaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        ButterKnife.bind(this);

        setUpList();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @OnClick(R.id.image_back)
    public void goBack() {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpList();
    }

    public void setUpList() {
        marcaciones = Daos.marcacionDao.getAll();

        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new MarcacionAdapter(marcaciones);
        recycler.setAdapter(adapter);


        // Crear un nuevo adaptador
        adapter = new MarcacionAdapter(marcaciones);
        recycler.setAdapter(adapter);
    }

    @OnClick(R.id.button_send)
    public void enviarMarcacioesPendientes() {
        List<Marcacion> marcacions = Daos.marcacionDao.getAll();
        for (Marcacion marcacion : marcacions) {
            new MarcacionAsynkTask(getBaseContext(), marcacion, new MarcacionAsynkTask.MarcacionCallback() {
                @Override
                public void onFinish() {
                    setUpList();
                }
            }).execute();
        }
    }
}
