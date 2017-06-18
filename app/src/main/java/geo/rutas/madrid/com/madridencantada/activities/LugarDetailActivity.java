package geo.rutas.madrid.com.madridencantada.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Locale;

import application.MadridEncantadaApp;
import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;

public class LugarDetailActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private static final float LOCATION_REFRESH_DISTANCE = 50;
    private Lugar lugar;
    //Declaramos las variables
    private RelativeLayout btGoToMap;
    private TextView tvHistory;
    private TextView tvInfo;
    private ImageView ivPlaceBig;
    private ImageView ivPause;
    private ImageView ivPlay;
    private ImageView ivRew;
    private LocationManager mLocationManager;
    private Location localizacion;
    private MediaPlayer mediaPlayer;

    private static String SPANISH = "español";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pinta la vista de la activity
        setContentView(R.layout.activity_lugar_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_lugar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**1)asociamos nuestras variables a las de la vista XML
         2)le damos forma de lo que queremos
         3)enlazamos el "mundo real" con el "mundo de Tron"*/
        btGoToMap = (RelativeLayout) findViewById(R.id.button_go_to_map);
        tvHistory = (TextView) findViewById(R.id.tv_history);
        tvInfo = (TextView) findViewById(R.id.tv_information);
        ivPlaceBig = (ImageView) findViewById(R.id.iv_place);
        ivPause = (ImageView) findViewById(R.id.iv_media_pause);
        ivPlay = (ImageView) findViewById(R.id.iv_media_play);
        ivRew = (ImageView) findViewById(R.id.iv_media_rew);

        getData();
        fillView();
        setOnClickListenerForButtons();

        configureMediaPlayerByLanguage();

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


    }

    public void getData() {
        //lo que hemos enviado desde "AllPoint.." para que "pinte" los datos de un sitio concreto
        Intent intent = getIntent();
        Integer placesListIndex = intent.getIntExtra("LUGARINDEX", 0);
        lugar = ((MadridEncantadaApp) getApplication()).getLugaresList().get(placesListIndex);
    }

    public void fillView() {   // pintar los datos en la vista
        getSupportActionBar().setTitle(lugar.getNombre());
        tvHistory.setText(lugar.getHistoria());
        tvInfo.setText(lugar.getInformacion());
        ivPlaceBig.setImageResource(lugar.getImagenGrande());
    }

    public void setOnClickListenerForButtons() {
        btGoToMap.setOnClickListener(this); //va al mapa de la geolocaliación
        ivPause.setOnClickListener(this); //pausa el audio
        ivPlay.setOnClickListener(this); //reproduce el audio
        ivRew.setOnClickListener(this); //para el audio
    }

    @Override
    public void onClick(View v) {
        if (v==ivPause)
            pauseAudio();
        else if (v==ivPlay)
            playMp3(lugar.getMp3IdSpa());
        else if (v == ivRew)
            rewAudio();
        else if (v == btGoToMap) {
            if (isGpsActive() && isUserLocated()) {
                goToMap();
            }
        }
    }

    private void goToMap() {
        //Puntos de geolocaciolazación, 1º dónde estoy, 2º dónde voy
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=" + localizacion.getLatitude() + "," +
                        localizacion.getLongitude() + "&daddr=" + lugar.getLatitudLongitud().latitude +
                        "," + lugar.getLatitudLongitud().longitude));
        startActivity(intent);
    }


    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(this,"LOCALIZACIÓN --> " +location.getLatitude()+"  "+location.getLongitude(), Toast.LENGTH_SHORT).show();
        Log.i("GEOLOCALIZACIÓN -->", location.toString());

        localizacion = location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean isGpsActive() {
        boolean gps_enabled = false;
        boolean network_enabled = false;
        gps_enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gps_enabled && !network_enabled) {
            Toast.makeText(this, R.string.toast_gps, Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(myIntent);
            return false;
        } else
            return true;
    }


    public boolean isUserLocated() {
        //comprobamos si hemos geolocalizado al usuario
        if (localizacion == null) {
            Toast.makeText(this, "Esperando geolocalización", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    public void  playMp3 (Integer mp3Id){
            if (!mediaPlayer.isPlaying())
                mediaPlayer.start();
    }


    public void pauseAudio (){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }


    public void rewAudio (){
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void configureMediaPlayerByLanguage(){
        if (Locale.getDefault().getDisplayLanguage().equals(SPANISH)){
            mediaPlayer = MediaPlayer.create(this, lugar.getMp3IdSpa());
        } else {
            mediaPlayer = MediaPlayer.create(this, lugar.getMp3IdEng());
        }
    }


}
