package fdi.ucm.thefridge.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fdi.ucm.thefridge.R;

/**
 * Created by Carlos Casado González on 17/05/2016.
 */
public class LoginActivity extends AppCompatActivity {
    Button buttonLogin, buttonRegister;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin=(Button) findViewById(R.id.button_login);
        buttonRegister=(Button) findViewById(R.id.button_acceso_registro);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

    }

}
