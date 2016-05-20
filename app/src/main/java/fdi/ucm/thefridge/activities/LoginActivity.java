package fdi.ucm.thefridge.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fdi.ucm.thefridge.BD.DBHelper;
import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.model.SesionUsuario;
import fdi.ucm.thefridge.model.Usuario;

/**
 * Created by Carlos Casado González on 17/05/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin, buttonRegister;
    private DBHelper dbHelper;
    private EditText userId, password;
    private Usuario usuario;
    private AlertDialog userDialog;
    private ProgressDialog waitDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin=(Button) findViewById(R.id.button_login);
        buttonRegister=(Button) findViewById(R.id.button_acceso_registro);
        dbHelper=new DBHelper(this);
        userId=(EditText)findViewById(R.id.usuarioLogin);
        password=(EditText)findViewById(R.id.passwordLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario=dbHelper.get(userId.getText().toString());

                if (usuario==null){
                    closeWaitDialog();
                    showDialogMessage("¡Error!", "Usuario o contraseña incorrectos, ", false);
                }else
                    if (usuario.getId().equals(userId.getText().toString()) && usuario.getPassword().equals(password.getText().toString())){
                        closeWaitDialog();
                        SesionUsuario.setId(usuario.getId());
                        SesionUsuario.setLogueado(true);
                        showDialogMessage("¡Login ok!", "Bienvenido, "+usuario.getId(), true);
                    }else{
                        closeWaitDialog();
                        showDialogMessage("¡Error!", "Usuario o contraseña incorrectos, ", false);
                    }

            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void showDialogMessage(String title, String body, final boolean exit) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(body).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                    if(exit) {
                        exit(usuario.getId());
                    }
                } catch (Exception e) {
                    if(exit) {
                        exit(usuario.getId());
                    }
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }
    private void exit(String uname) {

        Context context = this.getApplicationContext();
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
    private void showWaitDialog(String message) {
        closeWaitDialog();
        waitDialog = new ProgressDialog(this);
        waitDialog.setTitle(message);
        waitDialog.show();
    }
    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }
}
