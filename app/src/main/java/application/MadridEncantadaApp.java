package application;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;


public class MadridEncantadaApp extends Application{

    private List<Lugar> lugaresList;


    public MadridEncantadaApp() {
        super();
    }

    public List<Lugar> getLugaresList() {
        return lugaresList;
    }

    public void setLugaresList(List<Lugar> lugaresList) {
        this.lugaresList = lugaresList;
    }
}
