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
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import application.MadridEncantadaApp;
import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout btMap;     // Son RelativeLayouts pero los usamos como botones
    private RelativeLayout btPointOfInterest;
    private RelativeLayout btOptions;
    private RelativeLayout btAudioGuide;

    private static String SPANISH = "es";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLanguage();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        btMap = (RelativeLayout) findViewById(R.id.button_map);
        btPointOfInterest = (RelativeLayout) findViewById(R.id.button_point_of_interest);
        btOptions = (RelativeLayout)findViewById(R.id.button_options);
        btAudioGuide = (RelativeLayout) findViewById(R.id.button_audio_guide);
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
        Lugar reinaSofia = new Lugar(getString(R.string.sofia),getString(R.string.historia_museo), getString(R.string.information_museo),R.drawable.museo_reina_sofia, R.drawable.pq_museo_reina_sofia,  40.407969, -3.694804,R.raw.bach,R.raw.es_museo);
        Lugar bancoEspania = new Lugar(getString(R.string.banco),getString(R.string.historia_banco), getString(R.string.information_banco), R.drawable.banco_de_espana, R.drawable.pq_banco_de_espana, 40.418974, -3.693961,R.raw.bach,R.raw.es_banco);
        Lugar ayuntamiento = new Lugar(getString(R.string.ayuntamiento),getString(R.string.historia_ayuntamiento), getString(R.string.information_ayuntamiento), R.drawable.ayuntamiento_de_madrid, R.drawable.pq_ayuntamiento_de_madrid, 40.419051, -3.692264,R.raw.bach,R.raw.es_ayuntamiento);
        Lugar linares = new Lugar(getString(R.string.linares), getString(R.string.historia_america),getString(R.string.information_america), R.drawable.casa_de_america, R.drawable.pq_casa_de_america, 40.419700, -3.692346,R.raw.bach,R.raw.es_america);
        Lugar ejercito = new Lugar(getString(R.string.ejercito), getString(R.string.historia_ejercito),getString(R.string.information_ejercito) , R.drawable.cuartel_general_ejercito, R.drawable.pq_cuartel_general_ejercito, 40.419278, -3.694298,R.raw.bach,R.raw.es_ejercito);
        Lugar chimeneas = new Lugar(getString(R.string.chimenea), getString(R.string.historia_chimeneas), getString(R.string.information_chimeneas), R.drawable.casa_de_las_7_chimeneas, R.drawable.pq_casa_de_las_7_chimeneas, 40.420131, -3.696642,R.raw.bach,R.raw.es_chimeneas);
        Lugar iglesia = new Lugar(getString(R.string.iglesia), getString(R.string.historia_iglesia), getString(R.string.information_iglesia), R.drawable.iglesia_de_san_jose, R.drawable.pq_iglesia_de_san_jose, 40.419056, -3.696643,R.raw.bach,R.raw.es_iglesia);
        Lugar teatro = new Lugar(getString(R.string.teatro), getString(R.string.historia_teatro), getString(R.string.information_teatro), R.drawable.teatro_espanol_madrid, R.drawable.pq_teatro_espanol_madrid, 40.414808, -3.700225,R.raw.bach,R.raw.es_teatro);
        Lugar ana = new Lugar(getString(R.string.ana), getString(R.string.historia_ana), getString(R.string.information_ana), R.drawable.plaza_de_santa_ana, R.drawable.pq_plaza_de_santa_ana, 40.414714, -3.700897,R.raw.bach,R.raw.es_ana);
        Lugar cabeza = new Lugar(getString(R.string.cabeza), getString(R.string.historia_cabeza), getString(R.string.information_cabeza), R.drawable.cabeza1, R.drawable.pq_cabeza1, 40.411864, -3.703292,R.raw.bach,R.raw.es_cabeza);
        Lugar palacio = new Lugar(getString(R.string.palacio), getString(R.string.historia_palacio), getString(R.string.information_palacio), R.drawable.palacio_de_santa_cruz, R.drawable.pq_palacio_de_santa_cruz, 40.414771, -3.705981,R.raw.bach,R.raw.hooked);
        Lugar mayor = new Lugar(getString(R.string.mayor), getString(R.string.historia_mayor), getString(R.string.information_mayor), R.drawable.plaza_mayor, R.drawable.pq_plaza_mayor, 40.415512, -3.707402,R.raw.bach,R.raw.hooked);

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
