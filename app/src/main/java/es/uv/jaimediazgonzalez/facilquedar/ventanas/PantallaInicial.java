package es.uv.jaimediazgonzalez.facilquedar.ventanas;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import es.uv.jaimediazgonzalez.facilquedar.R;
import es.uv.jaimediazgonzalez.facilquedar.basedatos.EventoDbHelper;

public class PantallaInicial extends AppCompatActivity {

    private Button crearCalendario, verCalendario, misCalendarios;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);

        crearCalendario = (Button) findViewById(R.id.crearCalendario);
        verCalendario = (Button) findViewById(R.id.verCalendario);
        misCalendarios = (Button) findViewById(R.id.misCalendarios);

        EventoDbHelper baseDatosEventos = new EventoDbHelper(getApplicationContext());
        Cursor eventos = baseDatosEventos.getTodosEventos();
        if(eventos.getCount() == 0) {
            misCalendarios.setEnabled(false);
            misCalendarios.setBackgroundColor(Color.parseColor("#D3D3D3"));
        }

        MobileAds.initialize(this, "ca-app-pub-4541521919567374~6802638036");

        mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("C42B273B43CE4B989BF1D9B059A047C3").build();
        mAdView.loadAd(adRequest);

        /* LISTENERS */
        crearCalendario.setOnClickListener(crearCalendarioListener);
        verCalendario.setOnClickListener(verCalendarioListener);
        misCalendarios.setOnClickListener(verMisCalendarios);
    }

    /* LISTENERS */
    final View.OnClickListener crearCalendarioListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //Declaro el Intent
            Intent explicit_intent;
            //Instanciamos el Intent dandole:
            explicit_intent = new Intent(PantallaInicial.this, CrearCalendario.class);
            startActivity(explicit_intent);
        }
    };

    /* LISTENERS */
    final View.OnClickListener verCalendarioListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //Declaro el Intent
            Intent explicit_intent;
            //Instanciamos el Intent dandole:
            explicit_intent = new Intent(PantallaInicial.this, VerCalendario.class);
            startActivity(explicit_intent);
        }
    };

    final View.OnClickListener verMisCalendarios = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //Declaro el Intent
            Intent explicit_intent;
            //Instanciamos el Intent dandole:
            explicit_intent = new Intent(PantallaInicial.this, MisEventos.class);
            startActivity(explicit_intent);
        }
    };

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
