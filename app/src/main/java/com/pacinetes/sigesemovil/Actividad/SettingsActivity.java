package com.pacinetes.sigesemovil.Actividad;



/**
 * Created by nacho on 14/11/2017.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pacinetes.sigesemovil.Dominio.Logueo;
import com.pacinetes.sigesemovil.Dominio.LogueoAction;
import com.pacinetes.sigesemovil.R;

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        final EditText editTextUrl = (EditText)findViewById(R.id.editText_settings_url);
        Button buttonSave = (Button)findViewById(R.id.button_guardar);
        final Activity activity = this;

        final LogueoAction logueAction = new LogueoAction();
        final Logueo config = logueAction.recuperarConfiguracion(activity);

        editTextUrl.setText(config.getUrlRestful());

        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final Logueo config = logueAction.recuperarConfiguracion(activity);
                config.setUrlRestful(editTextUrl.getText().toString());
                logueAction.guardarConfiguracion(activity, config);

                finish();
            }
        });

    }

}
