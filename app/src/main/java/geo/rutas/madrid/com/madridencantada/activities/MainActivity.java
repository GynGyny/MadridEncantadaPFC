package geo.rutas.madrid.com.madridencantada.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import application.MadridEncantadaApp;
import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btMap;
    private Button btPointOfInterest;
    private Button btOptions;
    private Button btAudioGuide;
    private List<Lugar> lugaresList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setAppLanguage();


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        btMap = (Button) findViewById(R.id.button_map);
        btPointOfInterest = (Button) findViewById(R.id.button_point_of_interest);
        btOptions = (Button)findViewById(R.id.button_options);
        btAudioGuide = (Button) findViewById(R.id.button_audio_guide);
        setOnClickListenersForButtons();
        createData();
        ((MadridEncantadaApp) getApplication()).setLugaresList(lugaresList);
    }

    private void setAppLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String languageToLoad = sharedPreferences.getString(getString(R.string.language_key_preferencias), "es") ;   // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }


    public void setOnClickListenersForButtons(){
        btMap.setOnClickListener(this);
        btPointOfInterest.setOnClickListener(this);
        btOptions.setOnClickListener(this);
        btAudioGuide.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btMap) {
            goToMapActitivity();
        } else if (v==btPointOfInterest){
            goToPointOfInterestAcivity();
        } else if (v==btOptions){
            goToOptionsActivity();
        } else{
            goToAudioGuideActivity();
        }
    }

    private void goToMapActitivity() {
        Intent intent = new Intent(this, PointMapActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);  // TODO ANIMACIONES !!!
    }

    private void goToPointOfInterestAcivity() {
        Intent intent = new Intent(this, AllPointsOfInterestActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);  // TODO ANIMACIONES !!!
    }

    private void goToOptionsActivity() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);  // TODO ANIMACIONES !!!
    }

    private void goToAudioGuideActivity() {
    }



    public void createData() {
        //Las imagenes se cambian por las del sitio, Reina Sofía, Banco España, etc
        Lugar reinaSofia = new Lugar(getString(R.string.sofia),"Hospital", "Medium", "No lo sé",R.drawable.museo_reina_sofia, 40.407969, -3.694804);
        Lugar bancoEspania = new Lugar(getString(R.string.banco),"Banco", "Monja", "A veces", R.drawable.banco_de_espana, 40.418740, -3.694601);
        Lugar ayuntamiento = new Lugar(getString(R.string.ayuntamiento),"Palacio Comunicaciones", "Masacre", "Metro Banco España", R.drawable.ayuntamiento_de_madrid, 40.418633, -3.692476);
        Lugar linares = new Lugar(getString(R.string.linares), "Casa de América", "Psicofonías", "Recoletos", R.drawable.casa_de_america, 40.419947, -3.692248);
        Lugar ejercito = new Lugar(getString(R.string.ejercito), "Cuartel General del Ejército de Tierra", "Prim", "Recoletos", R.drawable.cuartel_general_ejercito, 40.421081, -3.694592);
        Lugar chimeneas = new Lugar(getString(R.string.chimenea), "7 chimeneas", "chimeneas", "Recoletos", R.drawable.casa_de_las_7_chimeneas, 40.4202, -3.69666);
        Lugar iglesia = new Lugar(getString(R.string.iglesia), "Iglesia de San José", "Baile", "Gran vía", R.drawable.iglesia_de_san_jose, 40.419068, -3.696654);
        Lugar teatro = new Lugar(getString(R.string.teatro), "Teatro Español", "Muchos", "Plaza Santa Ana", R.drawable.teatro_espanol_madrid, 40.414852, -3.699982);
        Lugar ana = new Lugar(getString(R.string.ana), "Plaza de Santa Ana", "No me acuerdo", "Plaza de Santa Ana", R.drawable.plaza_de_santa_ana, 40.414714, -3.700897);
        //Lugar hotel = new Lugar(getString(R.string.hotel), "Hotel del Gato", "Varios", "calle del gato", R.drawable.casa_de_las_7_chimeneas, 40.4202, -3.69666);
        Lugar cabeza = new Lugar(getString(R.string.cabeza), "Calle de La Cabeza", "Cabeza", "Calle de La Cabeza", R.drawable.cabeza1, 40.411864, -3.703292);
        Lugar palacio = new Lugar(getString(R.string.palacio), "Palacio de Santa Cruz", "palacios", "palacio", R.drawable.palacio_de_santa_cruz, 40.4147, -3.706031);
        Lugar mayor = new Lugar(getString(R.string.mayor), "Plaza Mayor", "Ajusticiados", "Plaza Mayor", R.drawable.plaza_mayor, 40.415556, -3.707222);

        lugaresList = new ArrayList<Lugar>();
        lugaresList.add(reinaSofia);
        lugaresList.add(bancoEspania);
        lugaresList.add(ayuntamiento);
        lugaresList.add(linares);
        lugaresList.add(ejercito);
        lugaresList.add(chimeneas);
        lugaresList.add(iglesia);
        lugaresList.add(teatro);
        lugaresList.add(ana);
        //<string name="hotel">Hotel del Gato</string>
        lugaresList.add(cabeza);
        lugaresList.add(palacio);
        lugaresList.add(mayor);
    }

}
