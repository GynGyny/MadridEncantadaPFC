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
        Lugar ayuntamiento = new Lugar(getString(R.string.ayuntamiento),"Palacio Comunicaciones", "Masacre", "Metro Banco España", R.drawable.madrid, 40.418633, -3.692476);
        Lugar linares = new Lugar(getString(R.string.linares), "Casa de América", "Psicofonías", "Recoletos", R.drawable.casa_de_america, 40.419947, -3.692248);
        lugaresList = new ArrayList<Lugar>();
        lugaresList.add(reinaSofia);
        lugaresList.add(bancoEspania);
        lugaresList.add(ayuntamiento);
        lugaresList.add(linares);
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
