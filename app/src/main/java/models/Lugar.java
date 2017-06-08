package models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Lugar donde está el array con lo que va a ver en la pantalla propia del sitio
 */

public class Lugar {

    private String nombre;
    private String historia;
    private String informacion;
    private int imagenGrande;
    private int imagenPequena;
    private LatLng latitudLongitud; //puntos cardinales




    public Lugar(String nombre, String historia, String informacion, int imagenGrande, int imagenPequena, double latitud, double longitud){
        this.nombre = nombre;
        this.historia = historia;
        this.informacion = informacion;
        this.imagenGrande = imagenGrande;
        this.imagenPequena = imagenPequena;
        latitudLongitud = new LatLng(latitud, longitud);
    }

    public int getImagenGrande() {
        return imagenGrande;
    }

    public void setImagenGrande(int imagenGrande) {
        this.imagenGrande = imagenGrande;
    }
    public int getImagenPequena() {
        return imagenPequena;
    }

    public void setImagenPequena(int imagenPequena) {
        this.imagenPequena = imagenPequena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
