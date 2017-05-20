package geo.rutas.madrid.com.madridencantada.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;

public class LugarDetailActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private static final float LOCATION_REFRESH_DISTANCE = 50;
    private Lugar lugar;
    //Declaramos las variables
    private Button btGoToAudio;
    private Button btGoToMap;
    private TextView tvHistory;
    private TextView tvLegend;
    private TextView tvInfo;
    private ImageView ivPlace;
    private LocationManager mLocationManager;
    private Location localizacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pinta la vista de la activity
        setContentView(R.layout.activity_lugar_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_lugar_detail);
        setSupportActionBar(toolbar);
        //asociamos nuestras variables a las de la vista XML
        //le damos forma de lo que queremos
        //enlazamos el "mundo real" con el "mundo de Tron"
        btGoToAudio = (Button) findViewById(R.id.button_go_to_audio);
        btGoToMap = (Button) findViewById(R.id.button_go_to_map);
        tvHistory = (TextView) findViewById(R.id.tv_history);
        tvLegend = (TextView) findViewById(R.id.tv_legend);
        tvInfo = (TextView) findViewById(R.id.tv_information);
        ivPlace = (ImageView) findViewById(R.id.iv_place);
        getData();
        fillView();
        setOnClickListenerForButtons();

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

    public void getData(){
        //lo que hemos enviado desde "AllPoint.." para que "pinte" los datos de un sitio concreto
        Intent intent = getIntent();

        String nombre = intent.getStringExtra("nombre");
        String historia = intent.getStringExtra("historia");
        String leyenda = intent.getStringExtra("leyenda");
        String informacion = intent.getStringExtra("informacion");
        Integer refImagen = intent.getIntExtra("imagen", R.drawable.cast_album_art_placeholder);
        Double latitud = intent.getDoubleExtra("latitud", 0.0);
        Double longitud = intent.getDoubleExtra("longitud", 0.0);
        lugar = new Lugar (nombre, historia, leyenda, informacion, refImagen, latitud, longitud);
    }

    public void fillView(){   // pintar los datos en la vista
        getSupportActionBar().setTitle(lugar.getNombre());
        tvHistory.setText(lugar.getHistoria());
        tvLegend.setText(lugar.getLeyenda());
        tvInfo.setText(lugar.getInformacion());
        ivPlace.setImageResource(lugar.getImagen());
    }

    public void setOnClickListenerForButtons (){
        btGoToMap.setOnClickListener(this);
        btGoToAudio.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v==btGoToAudio){
        } else {   //click en goToMap
            if (isGpsActive() && isUserLocated()) {
                goToMap();
            }
        }
    }

    private void goToMap() {
      //  Uri gmmIntentUri = Uri.parse("geo:40.337175,-3.875594"); //"geo:"+
        Uri gmmIntentUri = Uri.parse("geo:"+lugar.getLatitudLongitud().latitude+","+lugar.getLatitudLongitud().longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
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

    public boolean isGpsActive (){
        boolean gps_enabled = false;
        boolean network_enabled = false;
        gps_enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!gps_enabled && !network_enabled) {
            Toast.makeText(this,"Se necesitan permisos de Geolocalización",Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(myIntent);
            return false;
        } else
            return true;
    }


    public boolean isUserLocated (){
        //comprobamos si hemos geolocalizado al usuario
        if (localizacion == null){
            Toast.makeText(this, "Esperando geolocalización", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}
