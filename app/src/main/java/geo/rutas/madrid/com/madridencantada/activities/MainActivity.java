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

import java.util.Locale;

import geo.rutas.madrid.com.madridencantada.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btMap;
    private Button btPointOfInterest;
    private Button btOptions;
    private Button btAudioGuide;




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
        finish();

    }

    private void goToPointOfInterestAcivity() {
        Intent intent = new Intent(this, AllPointsOfInterestActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);  // TODO ANIMACIONES !!!
        finish();
    }

    private void goToOptionsActivity() {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
        finish();

    }

    private void goToAudioGuideActivity() {
    }
}
