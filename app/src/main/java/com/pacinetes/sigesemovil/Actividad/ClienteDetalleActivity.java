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
import com.pacinetes.sigesemovil.Dominio.Cliente;
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
 * Created by nacho on 25/11/2017.
 */

public class ClienteDetalleActivity extends Activity {
    String url;
    String user;
    String pass;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalle);

        TextView textViewClienteApellido = (TextView) findViewById(R.id.textView_cliente_apellido);
        TextView textViewClienteNombre = (TextView) findViewById(R.id.textView_cliente_nombre);
        TextView textViewClienteTipoDni = (TextView) findViewById(R.id.textView_cliente_tipo_dni);
        TextView textViewClienteDni = (TextView) findViewById(R.id.textView_cliente_dni);
        TextView textViewClienteDireccion = (TextView) findViewById(R.id.textView_cliente_direccion);
        TextView textViewClienteFechaNacimiento = (TextView) findViewById(R.id.textView_cliente_fecha_nacimeiento);
        TextView textViewClienteTelefono= (TextView) findViewById(R.id.textView_cliente_telefono);
        TextView textViewClienteLocalidad= (TextView) findViewById(R.id.textView_cliente_localidad);

        Intent intent = getIntent();
        url =  intent.getStringExtra("url");
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");

        try {
            cliente = new ClienteDetalleActivity.TraerClienteThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        textViewClienteApellido.setText(cliente.getMembers().getClienteApellido().getValue());
        textViewClienteNombre.setText(cliente.getMembers().getClienteNombre().getValue());
        textViewClienteTipoDni.setText(cliente.getMembers().getclienteTipoDocumento().getValue().toUpperCase());
        textViewClienteDni.setText(cliente.getMembers().getClienteDni().getValue());
        textViewClienteDireccion.setText(cliente.getMembers().getPersonaDireccion().getValue());
        textViewClienteFechaNacimiento.setText(df.format(cliente.getMembers().getClienteFechaNacimiento().getValue()));
        textViewClienteTelefono.setText(cliente.getMembers().getPersonaTelefono().getValue());
        textViewClienteLocalidad.setText(cliente.getMembers().getPersonaLocalidad().getValue().getTitle());
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

    private class TraerClienteThread extends AsyncTask<Void, Void, Cliente> {
        @Override
        protected Cliente doInBackground(Void... params) {
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
                ResponseEntity<Cliente> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Cliente.class);

                //return response.getBody();

                Cliente oCliente = response.getBody();

                //Log.v("leido", restLinks.getLinks().size()+"");
                return oCliente;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
}
