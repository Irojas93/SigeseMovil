package com.pacinetes.sigesemovil.Actividad;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.pacinetes.sigesemovil.Dominio.Logueo;
import com.pacinetes.sigesemovil.Dominio.LogueoAction;
import com.pacinetes.sigesemovil.R;
import com.pacinetes.sigesemovil.Servicio.Marcas;
import com.pacinetes.sigesemovil.Servicio.RestLink;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
public class MainActivity extends AppCompatActivity {

    String url;
    String user;
    String pass;
    Marcas marcas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText et_user = (EditText)findViewById(R.id.editText_user);
        final EditText et_pass = (EditText)findViewById(R.id.editText_pass);
        final Button button_connect = (Button)findViewById(R.id.button_connect);
        final CheckBox cb_save = (CheckBox)findViewById(R.id.checkBox_save);
        final Activity activity = this;
        final LogueoAction logAction = new LogueoAction();
        final Logueo config = logAction.recuperarConfiguracion(this);

        et_user.setText(config.getUser());
        et_pass.setText(config.getPass());
        cb_save.setChecked(config.getSave());

        button_connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Logueo config = logAction.recuperarConfiguracion(activity);

                if (et_pass.getText().toString().isEmpty()){

                    mostrarMensaje("El campo \"pass\" no puede quedar en blanco");
                    return;
                }

                if (config.getUrlRestful().isEmpty()){
                    mostrarMensaje("No ha configurado una URL. Hágalo desde el menu \"configuración\"");
                    return;
                }

                config.setUser(et_user.getText().toString());


                if (cb_save.isChecked()){
                    config.setPass(et_pass.getText().toString());
                    config.setSave(true);
                } else {
                    config.setPass("");
                    config.setSave(false);
                }
                Log.v("user",config.getUser());
                Log.v("pass", config.getPass());
                Log.v("save", config.getSave().toString());
                Log.v("url", config.getUrlRestful());
                logAction.guardarConfiguracion(activity, config);
                Intent intent = new Intent("android.intent.action.PRINCIPAL_LIST");

                intent.putExtra("url", config.getUrlRestful());
                intent.putExtra("user", et_user.getText().toString());
                intent.putExtra("pass", et_pass.getText().toString());

                url =  intent.getStringExtra("url")+"services/MarcaMenu/actions/listar/invoke";
                user =  intent.getStringExtra("user");
                pass =  intent.getStringExtra("pass");


                try {

                    marcas = new FillListOfMarcasThread().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                List<RestLink> LinksMarcasList = null;
                final List<String> listNombres = new ArrayList<String>();
                if ((marcas != null) && (marcas.getResult().getValue().size()>=0)){
                    //mostrarMensaje("a verGa briel");
                    startActivity(intent);
                }else
                    mostrarMensaje("El usario No tiene permiso de Acceso ");

            }
        });
    }

    private class FillListOfMarcasThread extends AsyncTask<Void, Void, Marcas> {
        @Override
        protected Marcas doInBackground(Void...  params) {
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
                ResponseEntity<Marcas> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Marcas.class);
                Marcas marcas = response.getBody();

                Log.v("listarTodos", marcas.getResult().getValue().size() +"");
                int arraySize = marcas.getResult().getValue().size();
                RestLink[] marcasArray = new RestLink[arraySize];
                for (int i=0; i< arraySize;i++){
                    marcasArray[i] = marcas.getResult().getValue().get(i);
                    Log.v("Equipo Encontrado", marcasArray[i].getTitle());
                }
                return marcas;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }

    private void mostrarMensaje(CharSequence text) {
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_settings:
                Intent intent=new Intent(this,SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
