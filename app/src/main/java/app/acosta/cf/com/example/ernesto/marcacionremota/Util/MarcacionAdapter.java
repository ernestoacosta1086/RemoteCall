package app.acosta.cf.com.example.ernesto.marcacionremota.Util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import app.acosta.cf.com.example.ernesto.marcacionremota.Models.Daos;
import app.acosta.cf.com.example.ernesto.marcacionremota.Models.Marcacion;
import app.acosta.cf.com.example.ernesto.marcacionremota.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ernesto on 24/2/2016.
 */
public class MarcacionAdapter extends RecyclerView.Adapter<MarcacionAdapter.MarcacionHolder> {

    private List<Marcacion> marcaciones;

    public MarcacionAdapter(List<Marcacion> marcaciones) {
        this.marcaciones = marcaciones;
    }

    @Override
    public MarcacionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marcacion_cardview, parent, false);
        return new MarcacionHolder(v);
    }

    @Override
    public void onBindViewHolder(MarcacionHolder holder, final int position) {

        holder.numeroUsuario.setText(marcaciones.get(position).getNumero_usuario());
        holder.tipoMarcacion.setText(marcaciones.get(position).getTipo_marcacion());
        holder.hora.setText(marcaciones.get(position).getHora());
        holder.fecha.setText(marcaciones.get(position).getFecha());

    }

    @Override
    public int getItemCount() {
        return marcaciones.size();
    }

    public class MarcacionHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.column_numero_usuario)
        TextView numeroUsuario;
        @Bind(R.id.column_tipo_marcacion)
        TextView tipoMarcacion;
        @Bind(R.id.column_hora)
        TextView hora;
        @Bind(R.id.column_fecha)
        TextView fecha;
        @Bind(R.id.delete_button)
        Button deleteButton;

        public MarcacionHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.delete_button)
        public void deleteMarcation() {
            Daos.marcacionDao.deleteById(marcaciones.get(getAdapterPosition()).getId());
            marcaciones.remove(getAdapterPosition());
            notifyDataSetChanged();
        }
    }
}

