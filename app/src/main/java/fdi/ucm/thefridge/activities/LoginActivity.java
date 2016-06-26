package fdi.ucm.thefridge.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.data.DatabaseAccess;
import fdi.ucm.thefridge.model.AeSimpleSHA1;
import fdi.ucm.thefridge.model.SesionUsuario;
import fdi.ucm.thefridge.model.Usuario;

/**
 * Created by Carlos Casado González on 17/05/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin, buttonRegister;
    private DatabaseAccess dbAccess;
    private EditText userId, password;
    private Usuario usuario;
    private AlertDialog userDialog;
    private ProgressDialog waitDialog;
    private AeSimpleSHA1 hashClass;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin=(Button) findViewById(R.id.button_login);
        buttonRegister=(Button) findViewById(R.id.button_acceso_registro);
        dbAccess=DatabaseAccess.getInstance(this);
        hashClass = new AeSimpleSHA1();
        userId=(EditText)findViewById(R.id.usuarioLogin);
        password=(EditText)findViewById(R.id.passwordLogin);

        String userReg;
        String passReg;
        if((userReg = getIntent().getStringExtra("name")) != null && (passReg= getIntent().getStringExtra("pass")) != null){
            userId.setText(userReg);
            password.setText(passReg);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAccess.open();
                usuario=dbAccess.get(userId.getText().toString());

                if (usuario==null){
                    closeWaitDialog();
                    showDialogMessage("¡Error!", "Usuario o contraseña incorrectos, ", false);
                }else {
                    String hash = hashClass.sha1Hash(password.getText().toString());
                    if (usuario.getId().equals(userId.getText().toString()) && usuario.getPassword().equals(hash)) {
                        closeWaitDialog();
                        SesionUsuario.setId(usuario.getId());
                        SesionUsuario.setIdNum(usuario.getIdNum());
                        SesionUsuario.setLogueado(true);
                        exit(usuario.getId());
                    } else {
                        closeWaitDialog();
                        showDialogMessage("¡Error!", "Usuario o contraseña incorrectos, ", false);
                    }
                }
                dbAccess.close();
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
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
