package geo.rutas.madrid.com.madridencantada.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import application.MadridEncantadaApp;
import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;

public class PointMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private ImageView ivMiniMapPlace;
    private TextView tvMiniMapName;
    private LinearLayout llDetailContainter;
    private boolean locationPermissionGranted;
    private Integer currentPlaceIndex = null; //guarda la posición del array correspondiente al lugar que se está mostrando


    List<Marker> markers = new ArrayList<Marker>(); //Lista para los puntos del mapa


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_point_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // setContentView(R.layout.activity_point_map); //si pongo esta sentencia peta la app REVISARRRRR

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmap);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.map));*/

        ivMiniMapPlace = (ImageView) findViewById(R.id.iv_MiniMapPlace);
        tvMiniMapName = (TextView) findViewById(R.id.tv_MiniMapName);
        llDetailContainter = (LinearLayout) findViewById(R.id.linear_detail_container);

        llDetailContainter.setOnClickListener(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // comprobamos
        //permisos para acceder a la posición GPS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Acepta los permisos de geolocalización", Toast.LENGTH_LONG).show();
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        mMap.setOnMarkerClickListener(this);
        List<Lugar> lugaresList = ((MadridEncantadaApp) getApplication()).getLugaresList();
        for (int i = 0; i < lugaresList.size(); i++) {
            // Add a marker in -- and move the camera
            LatLng lugar = new LatLng(lugaresList.get(i).getLatitudLongitud().latitude, lugaresList.get(i).getLatitudLongitud().longitude);
            mMap.addMarker(new MarkerOptions().position(lugar).title(lugaresList.get(i).getNombre()));
            Marker marker = mMap.addMarker(new MarkerOptions().position(lugar)); //Llenamos el array con los datos
            markers.add(marker);
        }
        //para hacer zoom y que salgan todos los puntos del mapa
        animateGoogleMapCamera();
    }
    public void animateGoogleMapCamera (){
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


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PointMap Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        List<Lugar> lugaresList = ((MadridEncantadaApp) getApplication()).getLugaresList();
        tvMiniMapName.setText(marker.getTitle());
        for (int i = 0; i < lugaresList.size(); i++){   // Buscamos el lugar cuyo nombre coincida con el título del marcador
            if (lugaresList.get(i).getNombre().equals(marker.getTitle())){
                ivMiniMapPlace.setImageResource(lugaresList.get(i).getImagenGrande());
                currentPlaceIndex=i;
                break;
            }
        }
        return false;
    }



    @Override
    public void onClick(View v) {
        if (currentPlaceIndex!=null) {
            Intent intent = new Intent(this, LugarDetailActivity.class);
            intent.putExtra("LUGARINDEX", currentPlaceIndex);
            startActivity(intent);
            //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);  // TODO ANIMACIONES !!!
        } else{
            Toast.makeText(this,getString(R.string.surf),Toast.LENGTH_SHORT).show();
        }
    }
/**
 *  Intent intent = new Intent(this, LugarDetailActivity.class);
 intent.putExtra("LUGARINDEX", index);

 startActivity(intent);
 * Intent intent = getIntent();
 Integer placesListIndex = intent.getIntExtra("LUGARINDEX", 0);
 lugar = ((MadridEncantadaApp) getApplication()).getLugaresList().get(placesListIndex);
 */

}
