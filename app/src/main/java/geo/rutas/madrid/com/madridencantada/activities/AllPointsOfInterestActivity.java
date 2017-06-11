package geo.rutas.madrid.com.madridencantada.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapters.LugarAdapter;
import application.MadridEncantadaApp;
import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;

public class AllPointsOfInterestActivity extends AppCompatActivity {

    private ListView lvLugaresLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_points_of_interest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvLugaresLista = (ListView) findViewById(R.id.lv_lista_lugares);
        List <Lugar> lugaresList = ((MadridEncantadaApp) getApplication()).getLugaresList();
        LugarAdapter adapter = new LugarAdapter(this, lugaresList);
        lvLugaresLista.setAdapter(adapter);
        addItemClickListener();
    }

    private void addItemClickListener() {
        lvLugaresLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lugar lugarItem = (Lugar) lvLugaresLista.getAdapter().getItem(position);
                goToLugarDetailAcivity(position);
            }
        });
    }

    private void goToLugarDetailAcivity(int index) {
        //lo que le pasamos del listado de los puntos de interés al lugar para que lo "pinte"
        Intent intent = new Intent(this, LugarDetailActivity.class);
        intent.putExtra("LUGARINDEX", index);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
