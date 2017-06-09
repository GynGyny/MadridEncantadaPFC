package geo.rutas.madrid.com.madridencantada.activities;

import android.media.MediaPlayer;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import application.MadridEncantadaApp;
import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;

public class AudioMapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private ImageView ivRew;
    private ImageView ivPlay;
    private ImageView ivPause;
    List<Marker> markers = new ArrayList<Marker>(); //Lista para los puntos del mapa
    private MediaPlayer mediaPlayer;
    private  int backwardTime = 5000;
    private double startTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ivPause = (ImageView) findViewById(R.id.iv_media_pause);
        ivPlay = (ImageView) findViewById(R.id.iv_media_play);
        ivRew = (ImageView) findViewById(R.id.iv_media_rew);
        addOnclickListener();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // comprobamos
        List<Lugar> lugaresList = ((MadridEncantadaApp) getApplication()).getLugaresList();
        for (int i = 0; i < lugaresList.size(); i++) {
            // Add a marker in -- and move the camera
            LatLng lugar = new LatLng(lugaresList.get(i).getLatitudLongitud().latitude, lugaresList.get(i).getLatitudLongitud().longitude);
            mMap.addMarker(new MarkerOptions().position(lugar).title(lugaresList.get(i).getNombre()));
            Marker marker = mMap.addMarker(new MarkerOptions().position(lugar)); //Llenamos el array con los datos
            markers.add(marker);
        }
        //markers.size(); //tamaño del array
        //para hacer zoom y que salgan todos los puntos del mapa
        animateGoogleMapCamera();
    }

    private void animateGoogleMapCamera() {
        //Android map v2 zoom para mostrar los marcadores
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 100; // Pixels para el desplazamiento de los bordes del mapa (zoom)
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.moveCamera(cu);
    }

    private void addOnclickListener(){
        ivRew.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ivPlay)
            playAudio();
        else if (v==ivPause)
            pauseAudio();
        else if (v==ivRew)
            rewAudio();
    }

    public void playAudio (){
        if (mediaPlayer==null){
            mediaPlayer = MediaPlayer.create(this, R.raw.hooked);
            mediaPlayer.start();
        } else {
            mediaPlayer.start();
        }
        startTime = mediaPlayer.getCurrentPosition();
    }

    public void pauseAudio (){
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    public void rewAudio (){

        int temp = (int)startTime;

        if((temp-backwardTime)>0){
            startTime = startTime - backwardTime;
            mediaPlayer.seekTo((int) startTime);
            Toast.makeText(getApplicationContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
        }
    }
        /**
         * // When user click to "Rewind".
         public void doRewind(View view)  {
         int currentPosition = this.mediaPlayer.getCurrentPosition();
         int duration = this.mediaPlayer.getDuration();
         // 5 seconds.
         int SUBTRACT_TIME = 5000;

         if(currentPosition - SUBTRACT_TIME > 0 )  {
         this.mediaPlayer.seekTo(currentPosition - SUBTRACT_TIME);
         }
         }
         */
        /**if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            playAudio();
        }*/

}
