package geo.rutas.madrid.com.madridencantada.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

public class AudioMapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private ImageView ivRew;
    private ImageView ivPlay;
    private ImageView ivPause;
    private TextView tvMiniMapName;
    List<Marker> markers = new ArrayList<Marker>(); //Lista para los puntos del mapa
    private MediaPlayer mediaPlayer;

    Location mLocation;
    GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 15000;  /* 15 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */

    private static double ACTION_RADIUS = 15.0;



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
        tvMiniMapName = (TextView) findViewById(R.id.tv_MiniMapName);
        addOnclickListeners();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Acepta los permisos de geolocalización", Toast.LENGTH_LONG).show();
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        mMap.setMyLocationEnabled(true);
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



    private void addOnclickListeners(){
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
            mediaPlayer = MediaPlayer.create(this, R.raw.bach);
            mediaPlayer.start();
        } else {
            mediaPlayer.start();
        }
    }



    public void pauseAudio (){
        if (mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }



    public void rewAudio (){
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }



    @Override
    public void onConnectionSuspended(int i) {
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation!=null)
        {
            tvMiniMapName.setText("Latitude : "+mLocation.getLatitude()+" , Longitude : "+mLocation.getLongitude());
        }
        startLocationUpdates();
    }



    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable Permissions", Toast.LENGTH_LONG).show();
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }



    public void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi
                    .removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onLocationChanged(Location location) {   // CALLBACK PARA LOS CAMBIOS DE UBICACIÓN
        if(location!=null)
            tvMiniMapName.setText("Latitude : "+location.getLatitude()+" , Longitude : "+location.getLongitude());
    }

}

