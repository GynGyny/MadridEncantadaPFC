package geo.rutas.madrid.com.madridencantada.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapters.LugarAdapter;
import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;

public class AllPointsOfInterestActivity extends AppCompatActivity {


    private ListView lvLugaresLista;
    private List <Lugar> lugaresList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_points_of_interest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvLugaresLista = (ListView) findViewById(R.id.lv_lista_lugares);
        crearLugares();
        LugarAdapter adapter = new LugarAdapter(this, lugaresList);
        lvLugaresLista.setAdapter(adapter);
        addItemClickListener();
    }

    private void addItemClickListener() {
        lvLugaresLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lugar lugarItem = (Lugar) lvLugaresLista.getAdapter().getItem(position);
                goToLugarDetailAcivity(lugarItem);
            }
        });
    }


    public void crearLugares(){
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

    private void goToLugarDetailAcivity(Lugar lugarItem) {
        //lo que le pasamos del listado de los puntos de interés al lugar para que lo "pinte"
        Intent intent = new Intent(this, LugarDetailActivity.class);
        intent.putExtra("nombre", lugarItem.getNombre());
        intent.putExtra("historia", lugarItem.getHistoria());
        intent.putExtra("leyenda", lugarItem.getLeyenda());
        intent.putExtra("informacion", lugarItem.getInformacion());
        intent.putExtra("imagen",lugarItem.getImagen());
        intent.putExtra("latitud",lugarItem.getLatitudLongitud().latitude);
        intent.putExtra("longitud",lugarItem.getLatitudLongitud().longitude);
        startActivity(intent);
    }
}
