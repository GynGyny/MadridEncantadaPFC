package geo.rutas.madrid.com.madridencantada.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

    private static String SPANISH = "es";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLanguage();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        btMap = (Button) findViewById(R.id.button_map);
        btPointOfInterest = (Button) findViewById(R.id.button_point_of_interest);
        btOptions = (Button)findViewById(R.id.button_options);
        btAudioGuide = (Button) findViewById(R.id.button_audio_guide);
        setOnClickListenersForButtons();
        ((MadridEncantadaApp) getApplication()).setLugaresList(getAllPlaces());
    }

    private void setAppLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String languageToLoad = sharedPreferences.getString(getString(R.string.language_key_preferencias), SPANISH) ;   // your language
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
            goToAudioActivity();
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
        showDialog();
    }

    private void goToAudioActivity() {
        Intent intent = new Intent(this, AudioMapsActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);  // TODO ANIMACIONES !!!
    }



    public List<Lugar> getAllPlaces() {
        List<Lugar> lugaresList = new ArrayList<Lugar>();

        //Las imagenes se cambian por las del sitio, Reina Sofía, Banco España, etc
        Lugar reinaSofia = new Lugar(getString(R.string.sofia),"Hospital", "No lo sé",R.drawable.museo_reina_sofia, R.drawable.pq_museo_reina_sofia,  40.407969, -3.694804,R.raw.bach,R.raw.hooked);
        Lugar bancoEspania = new Lugar(getString(R.string.banco),"Banco", "A veces", R.drawable.banco_de_espana, R.drawable.pq_banco_de_espana, 40.418740, -3.694601,R.raw.bach,R.raw.hooked);
        Lugar ayuntamiento = new Lugar(getString(R.string.ayuntamiento),"Palacio Comunicaciones", "Metro Banco España", R.drawable.ayuntamiento_de_madrid, R.drawable.pq_ayuntamiento_de_madrid, 40.418633, -3.692476,R.raw.bach,R.raw.hooked);
        Lugar linares = new Lugar(getString(R.string.linares), "Casa de América", "Recoletos", R.drawable.casa_de_america, R.drawable.pq_casa_de_america, 40.419947, -3.692248,R.raw.bach,R.raw.hooked);
        Lugar ejercito = new Lugar(getString(R.string.ejercito), getString(R.string.historia_ejercito), "Recoletos", R.drawable.cuartel_general_ejercito, R.drawable.pq_cuartel_general_ejercito, 40.421081, -3.694592,R.raw.bach,R.raw.es_ejercito);
        Lugar chimeneas = new Lugar(getString(R.string.chimenea), "7 chimeneas", "Recoletos", R.drawable.casa_de_las_7_chimeneas, R.drawable.pq_casa_de_las_7_chimeneas, 40.4202, -3.69666,R.raw.bach,R.raw.hooked);
        Lugar iglesia = new Lugar(getString(R.string.iglesia), "Iglesia de San José", "Gran vía", R.drawable.iglesia_de_san_jose, R.drawable.pq_iglesia_de_san_jose, 40.419068, -3.696654,R.raw.bach,R.raw.hooked);
        Lugar teatro = new Lugar(getString(R.string.teatro), "Teatro Español", "Plaza Santa Ana", R.drawable.teatro_espanol_madrid, R.drawable.pq_teatro_espanol_madrid, 40.414852, -3.699982,R.raw.bach,R.raw.hooked);
        Lugar ana = new Lugar(getString(R.string.ana), "Plaza de Santa Ana", "Plaza de Santa Ana", R.drawable.plaza_de_santa_ana, R.drawable.pq_plaza_de_santa_ana, 40.414714, -3.700897,R.raw.bach,R.raw.hooked);
        //Lugar hotel = new Lugar(getString(R.string.hotel), "Hotel del Gato", "calle del gato", R.drawable.casa_de_las_7_chimeneas, 40.4202, -3.69666);
        Lugar cabeza = new Lugar(getString(R.string.cabeza), "Calle de La Cabeza", "Calle de La Cabeza", R.drawable.cabeza1, R.drawable.pq_cabeza1, 40.411864, -3.703292,R.raw.bach,R.raw.hooked);
        Lugar palacio = new Lugar(getString(R.string.palacio), "Palacio de Santa Cruz", "palacio", R.drawable.palacio_de_santa_cruz, R.drawable.pq_palacio_de_santa_cruz, 40.4147, -3.706031,R.raw.bach,R.raw.hooked);
        Lugar mayor = new Lugar(getString(R.string.mayor), "Plaza Mayor", "Plaza Mayor", R.drawable.plaza_mayor, R.drawable.pq_plaza_mayor, 40.415556, -3.707222,R.raw.bach,R.raw.hooked);

       // Lugar universidad = new Lugar("UNIVERSIDAD","1","2", R.drawable.plaza_mayor, R.drawable.pq_plaza_mayor, 40.341328, -3.845749,R.raw.bach,R.raw.audios_cortos);
        //Lugar retamas = new Lugar("RETAMAS","1","2", R.drawable.plaza_mayor, R.drawable.pq_plaza_mayor, 40.3407826,-3.845783,R.raw.bach,R.raw.es_ejercito);

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
        //lugaresList.add(universidad);
        //lugaresList.add(retamas);
        return lugaresList;
    }


    private void showDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getResources().getString(R.string.str_button));
        //obtiene los idiomas del array de string.xml
        String[] types = getResources().getStringArray(R.array.languages);
        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                switch (which) {
                    case 0:
                        editor.putString(getString(R.string.language_key_preferencias), "es");
                        break;
                    case 1:
                        editor.putString(getString(R.string.language_key_preferencias), "en");
                        break;
                }
                editor.commit();
                dialog.dismiss();
                //Cambia el idioma de la aplicación
                Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                startActivity(refresh);
                finish();
            }

        });

        b.show();
    }

}
