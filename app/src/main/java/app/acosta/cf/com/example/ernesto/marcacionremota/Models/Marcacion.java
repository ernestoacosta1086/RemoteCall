package app.acosta.cf.com.example.ernesto.marcacionremota.Models;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Ernesto on 2/2/2016.
 */
public class Marcacion {

    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String usuario;
    @DatabaseField
    private String clave;
    @DatabaseField
    private String coordenada_lon;
    @DatabaseField
    private String coordenada_lat;
    @DatabaseField
    private String numero_usuario;
    @DatabaseField
    private String fecha;
    @DatabaseField
    private String hora;
    @DatabaseField
    private String tipo_marcacion;

    public Marcacion() {
    }

    public Marcacion(String usuario, String clave, String coordenada_lon, String coordenada_lat, String numero_usuario, String fecha, String hora, String tipo_marcacion) {

        this.usuario = usuario;
        this.clave = clave;
        this.coordenada_lon = coordenada_lon;
        this.coordenada_lat = coordenada_lat;
        this.numero_usuario = numero_usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.tipo_marcacion = tipo_marcacion;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public String getCoordenada_lon() {
        return coordenada_lon;
    }

    public void setCoordenada_lon(String coordenada_lon) {
        this.coordenada_lon = coordenada_lon;
    }

    public String getCoordenada_lat() {
        return coordenada_lat;
    }

    public void setCoordenada_lat(String coordenada_lat) {
        this.coordenada_lat = coordenada_lat;
    }

    public String getNumero_usuario() {
        return numero_usuario;
    }

    public void setNumero_usuario(String numero_usuario) {
        this.numero_usuario = numero_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipo_marcacion() {
        return tipo_marcacion;
    }

    public void setTipo_marcacion(String tipo_marcacion) {

        this.tipo_marcacion = tipo_marcacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
