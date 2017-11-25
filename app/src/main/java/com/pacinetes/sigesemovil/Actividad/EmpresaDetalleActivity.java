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
import com.pacinetes.sigesemovil.Dominio.Empresa;
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
import java.util.concurrent.ExecutionException;

/**
 * Created by nacho on 25/11/2017.
 */

public class EmpresaDetalleActivity extends Activity {
    String url;
    String user;
    String pass;
    Empresa empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_detalle);

        TextView textViewEmpresaRazonSocial = (TextView) findViewById(R.id.textView_empresa_razon_social);
        TextView textViewEmpresaCuit = (TextView) findViewById(R.id.textView_empresa_cuit_cuil);
        TextView textViewEmpresaDireccion = (TextView) findViewById(R.id.textView_empresa_direccion);
        TextView textViewEmpresaLocalidad = (TextView) findViewById(R.id.textView_empresa_localidad);
        TextView textViewEmpresaMail = (TextView) findViewById(R.id.textView_empresa_mail);
        TextView textViewEmpresaTelefono = (TextView) findViewById(R.id.textView_empresa_telefono);

        Intent intent = getIntent();
        url =  intent.getStringExtra("url");
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");

        try {
            empresa = new EmpresaDetalleActivity.TraerEmpresaThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        textViewEmpresaRazonSocial.setText(empresa.getMembers().getEmpresaRazonSocial().getValue());
        textViewEmpresaCuit.setText(empresa.getMembers().getPersonaCuitCuil().getValue());
        textViewEmpresaDireccion.setText(empresa.getMembers().getPersonaDireccion().getValue());
        textViewEmpresaLocalidad.setText(empresa.getMembers().getPersonaLocalidad().getValue().getTitle());
        textViewEmpresaMail.setText(empresa.getMembers().getPersonaMail().getValue());
        textViewEmpresaTelefono.setText(empresa.getMembers().getPersonaTelefono().getValue());

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

    private class TraerEmpresaThread extends AsyncTask<Void, Void, Empresa> {
        @Override
        protected Empresa doInBackground(Void... params) {
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
                ResponseEntity<Empresa> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Empresa.class);

                //return response.getBody();

                Empresa oEmpresa = response.getBody();

                //Log.v("leido", restLinks.getLinks().size()+"");
                return oEmpresa;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
}
