package models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Lugar donde está la
 */

public class Lugar {

    private String nombre;
    private String historia;
    private String leyenda;
    private String informacion;
    private int imagen;
    private LatLng latitudLongitud; //puntos cardinales




    public Lugar(String nombre, String historia, String leyenda, String informacion, int imagen, double latitud, double longitud){
        this.nombre = nombre;
        this.historia = historia;
        this.leyenda = leyenda;
        this.informacion = informacion;
        this.imagen = imagen;
        latitudLongitud = new LatLng(latitud, longitud);
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public LatLng getLatitudLongitud() {
        return latitudLongitud;
    }

    public void setLatitudLongitud(LatLng latitudLongitud) {
        this.latitudLongitud = latitudLongitud;
    }
}
