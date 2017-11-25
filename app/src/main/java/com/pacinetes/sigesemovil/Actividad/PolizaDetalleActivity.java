package com.pacinetes.sigesemovil.Actividad;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.pacinetes.sigesemovil.Dominio.Poliza;
import com.pacinetes.sigesemovil.R;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by nacho on 24/11/2017.
 */

public class PolizaDetalleActivity extends Activity {

    String url;
    String user;
    String pass;
    Poliza poliza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poliza_detalle);

        TextView textViewNumeroPoliza = (TextView) findViewById(R.id.textView_poliza_numero);
        TextView textViewImporteTotalPoliza = (TextView) findViewById(R.id.textView_poliza_importe_total);
        TextView textViewCompaniaPoliza = (TextView) findViewById(R.id.textView_poliza_compania);
        TextView textViewClientePoliza = (TextView) findViewById(R.id.textView_poliza_cliente);
        TextView textViewEstadoPoliza = (TextView) findViewById(R.id.textView_poliza_estado);
        TextView textViewFechaEmision = (TextView) findViewById(R.id.textView_poliza_fecha_emision);
        TextView textViewFechaVigencia = (TextView) findViewById(R.id.textView_poliza_fecha_vigencia);
        TextView textViewFechaVencimiento = (TextView) findViewById(R.id.textView_poliza_fecha_vencimiento);
        TextView textViewDiasVencimiento = (TextView) findViewById(R.id.textView_poliza_dias_restantes);

        Intent intent = getIntent();
        url =  intent.getStringExtra("url");
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");

        try {
            poliza = new TraerPolizaThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        textViewNumeroPoliza.setText(poliza.getMembers().getPolizaNumero().getValue());
        textViewImporteTotalPoliza.setText(poliza.getMembers().getPolizaImporteTotal().getValue());
        textViewCompaniaPoliza.setText(poliza.getMembers().getPolizaCompania().getValue().getTitle());
        textViewClientePoliza.setText(poliza.getMembers().getPolizaCliente().getValue().getTitle());
        textViewEstadoPoliza.setText(poliza.getMembers().getPolizaEstado().getValue());
        textViewFechaEmision.setText(df.format(poliza.getMembers().getPolizaFechaEmision().getValue()));
        textViewFechaVigencia.setText(df.format(poliza.getMembers().getPolizaFechaVigencia().getValue()));
        textViewFechaVencimiento.setText(df.format(poliza.getMembers().getPolizaFechaVencimiento().getValue()));
        textViewDiasVencimiento.setText(String.valueOf(calcularCantidadDias(poliza.getMembers().getPolizaFechaVencimiento().getValue())));

    }
    private long calcularCantidadDias(Date input){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        df.format(date);
        long diferencia = input.getTime() - date.getTime() ;
        long dias = (diferencia/(1000*60*60*24))%365;
        return dias;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class TraerPolizaThread extends AsyncTask<Void, Void, Poliza> {
        @Override
        protected Poliza doInBackground(Void... params) {
            try {


                //Services services = null;
                Log.v("ingresando User y Pass", user + " : " + pass);
                // Set the username and password for creating a Basic Auth request
                HttpAuthentication authHeader = new HttpBasicAuthentication(user, pass);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                Log.v("ingresando URL",url);
                RestTemplate restTemplate = new RestTemplate();

                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                restTemplate.getMessageConverters().add(converter);

                // Make the HTTP GET request to the Basic Auth protected URL
                ResponseEntity<Poliza> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Poliza.class);

                //return response.getBody();

                Poliza oPoliza = response.getBody();

                //Log.v("leido", restLinks.getLinks().size()+"");
                return oPoliza;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
}
