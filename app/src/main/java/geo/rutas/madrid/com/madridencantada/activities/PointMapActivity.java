package geo.rutas.madrid.com.madridencantada.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;

public class PointMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private ImageView ivMiniMapPlace;
    private TextView tvMiniMapName;
    private List<Lugar> lugaresList;

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

        createData();


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for (int i = 0; i < lugaresList.size(); i++) {
            // Add a marker in -- and move the camera
            LatLng lugar = new LatLng(lugaresList.get(i).getLatitudLongitud().latitude, lugaresList.get(i).getLatitudLongitud().longitude);
            mMap.addMarker(new MarkerOptions().position(lugar).title(lugaresList.get(i).getNombre()));

            Marker marker = mMap.addMarker(new MarkerOptions().position(lugar)); //Llenamos el array con los datos
            markers.add(marker);

        }
       //markers.size(); //tamaño del array
        //para hacer zoom y que salgan todos los puntos del mapa
        animeteGoogleMapCamera();
    }
    public void animeteGoogleMapCamera (){
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
        Lugar cabeza = new Lugar(getString(R.string.cabeza), "Calle de La Cabeza", "Cabeza", "Calle de La Cabeza", R.drawable.calle_de_la_cabeza, 40.411864, -3.703292);
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

    public void fillView() {   // pintar los datos en la vista

    }
}
