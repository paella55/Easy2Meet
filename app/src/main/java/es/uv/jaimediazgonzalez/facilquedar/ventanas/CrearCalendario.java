package es.uv.jaimediazgonzalez.facilquedar.ventanas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.uv.jaimediazgonzalez.facilquedar.DateDialog;
import es.uv.jaimediazgonzalez.facilquedar.R;

/**
 * Created by Familia Diaz on 27/06/2017.
 */

public class CrearCalendario  extends AppCompatActivity {
    private Button listo;
    private EditText nombreCalendario, fechaDesde, fechaHasta, apodoUsuario;
    private Spinner horaInicial, horaFinal;
    DateDialog dateDialogDesde, dateDialogHasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_calendario);

        listo = (Button) findViewById(R.id.listo);
        listo.setEnabled(false);
        listo.setBackgroundColor(Color.parseColor("#D3D3D3"));

        horaInicial = (Spinner) findViewById(R.id.spinnerHoraCalendarioInicial);
        horaFinal = (Spinner) findViewById(R.id.spinnerHoraCalendarioFinal);

        //Ponemos hora inicial y final por defecto
        horaInicial.setSelection(0);
        horaFinal.setSelection(23);

        //Version inicial solo con dias
        horaInicial.setEnabled(false);
        horaFinal.setEnabled(false);

        nombreCalendario = (EditText) findViewById(R.id.nombreCalendario);
        fechaDesde = (EditText) findViewById(R.id.desdeFechaPicker);
        fechaHasta = (EditText) findViewById(R.id.hastaFechaPicker);

        apodoUsuario = (EditText) findViewById(R.id.nombreAdmin);

        /* LISTENERS */
        listo.setOnClickListener(listoCalendarioListener);
        fechaDesde.addTextChangedListener(textWatcher);
        fechaHasta.addTextChangedListener(textWatcher);
    }

    public void onStart(){
        super.onStart();
        dateDialogDesde = new DateDialog(this, R.id.desdeFechaPicker);
        dateDialogHasta = new DateDialog(this, R.id.hastaFechaPicker);

    }

    /* LISTENERS */
    final View.OnClickListener listoCalendarioListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            //Declaro el Intent
            Intent explicit_intent;

            //Instanciamos el Intent dandole:
            explicit_intent = new Intent(CrearCalendario.this, Calendario.class);

            //Metemos las fechas en el intent
            if(nombreCalendario.getText().toString().isEmpty())
            {
                nombreCalendario.setText(getResources().getString(R.string.nombre_calendario));
            }
            explicit_intent.putExtra("nombreCalendario", nombreCalendario.getText().toString());
            explicit_intent.putExtra("fechaDesde", fechaDesde.getText().toString());
            explicit_intent.putExtra("fechaHasta", fechaHasta.getText().toString());
            explicit_intent.putExtra("apodoUsuario", apodoUsuario.getText().toString());
            explicit_intent.putExtra("horaInicial", horaInicial.getSelectedItem().toString());
            explicit_intent.putExtra("horaFinal", horaFinal.getSelectedItem().toString());

            startActivity(explicit_intent);
        }
    };

    //TextWatcher para los editText de fecha desde y hasta
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            comprobarFechaDesdeHasta();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    //Si tiene fecha válida, entonces podemos activar el botón.
    private void comprobarFechaDesdeHasta()  {
        String s1 = fechaDesde.getText().toString();
        String s2 = fechaHasta.getText().toString();

        if(!s1.equals("")){
            dateDialogHasta.updateDate(dateDialogDesde.get_birthYear(), dateDialogDesde.get_month(),
                    dateDialogDesde.get_day());
        }

        if(s1.equals("") || s2.equals(""))
        {
            listo.setEnabled(false);
            listo.setBackgroundColor(Color.parseColor("#D3D3D3"));
        } else
        {
            // Comprobamos que la fecha desde es anterior a hasta
            SimpleDateFormat formatDates = new SimpleDateFormat("dd/MM/yyyy");
            Date desdeDate = null;
            Date hastaDate = null;
            try {
                desdeDate = formatDates.parse(s1);
                hastaDate = formatDates.parse(s2);
            } catch (ParseException e) {
                String advertenciaFechasInvalidas = getResources().getString(R.string.advertencia_fecha_no_valida);
                Toast.makeText(CrearCalendario.this, advertenciaFechasInvalidas,
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return;
            }

            if(desdeDate.before(hastaDate)){
                listo.setEnabled(true);
                listo.setBackgroundColor(Color.parseColor("#0D98FF"));
            } else{
                String advertenciaFechaAnterior = getResources().getString(R.string.advertencia_fecha_anterior);
                Toast.makeText(CrearCalendario.this, advertenciaFechaAnterior,
                        Toast.LENGTH_LONG).show();
                listo.setEnabled(false);
                listo.setBackgroundColor(Color.parseColor("#D3D3D3"));
            }
        }
    }
}
