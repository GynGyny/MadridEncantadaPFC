package geo.rutas.madrid.com.madridencantada.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

import geo.rutas.madrid.com.madridencantada.R;

public class OptionsActivity extends AppCompatActivity {
    private Button btLanguage;
    private Button btManual;
    //Para despleglable con los idiomas
    private Locale locale;
    private Configuration config = new Configuration();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        btLanguage = (Button) findViewById(R.id.button_languaje);

        btLanguage.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        showDialog();
                    }
                });
    }
        /**
         * Muestra una ventana de dialogo para elegir el nuevo idioma de la aplicacion
         * Cuando se hace clic en uno de los idiomas, se cambia el idioma de la aplicacion
         * y se recarga la actividad para ver los cambios
         * */

    private void showDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getResources().getString(R.string.str_button));
        //obtiene los idiomas del array de string.xml
        String[] types = getResources().getStringArray(R.array.languages);
        b.setItems(types, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                switch (which) {
                    case 0:
                        editor.putString(getString(R.string.language_key_preferencias), "es");
                        break;
                    case 1:
                        editor.putString(getString(R.string.language_key_preferencias), "en");
                        break;
                }
                editor.commit();
                dialog.dismiss();
                //Cambia el idioma de la aplicación
                Intent refresh = new Intent(OptionsActivity.this, MainActivity.class);
                startActivity(refresh);
                finish();
            }

        });

        b.show();
    }
}
