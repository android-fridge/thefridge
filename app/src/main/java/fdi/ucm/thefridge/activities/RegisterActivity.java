package fdi.ucm.thefridge.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fdi.ucm.thefridge.BD.DBHelper;
import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.model.Usuario;

/**
 * Created by Carlos Casado González on 11/05/2016.
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText userId, pass1, pass2;
    private Button button;
    private ImageView imageChecked1,imageChecked2, imageChecked0,imageNotChecked1,imageNotChecked2, imageNotChecked0;
    private boolean pass1Ok,pass2Ok,usernameOk;
    private Pattern pattern;
    private Matcher matcher;
    private ProgressDialog waitDialog;
    private AlertDialog userDialog;
    private String username, pass;
    private DBHelper dbHelper;

    /* */
    private static final String PASSWORD_PATTERN="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper=new DBHelper(this);
        setContentView(R.layout.activity_register);
        pattern=Pattern.compile(PASSWORD_PATTERN);
        pass1Ok=false;
        pass2Ok=false;
        button=(Button) findViewById(R.id.button_register);
        button.setEnabled(false);
        imageChecked0=(ImageView)findViewById(R.id.imageChecked0);
        imageNotChecked0=(ImageView)findViewById(R.id.imageNotChecked0);
        userId=(EditText)findViewById(R.id.editTextRegId);
        userId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (userId.getText().toString().isEmpty() || userId.getText().toString()==null){
                    imageChecked0.setVisibility(View.INVISIBLE);
                    imageNotChecked0.setVisibility(View.VISIBLE);
                    usernameOk=false;
                    button.setEnabled(false);

                }else{
                    imageChecked0.setVisibility(View.VISIBLE);
                    imageNotChecked0.setVisibility(View.INVISIBLE);
                    usernameOk=true;
                    if (pass1Ok && pass2Ok)
                        button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imageChecked1=(ImageView)findViewById(R.id.imageChecked);
        imageChecked2=(ImageView)findViewById(R.id.imageChecked2);
        imageNotChecked1=(ImageView)findViewById(R.id.imageNotChecked);
        imageNotChecked2=(ImageView)findViewById(R.id.imageNotChecked2);
        pass1=(EditText)findViewById(R.id.editTextRegPass1);
        pass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 if (isValidPassword(s)){
                     imageChecked1.setVisibility(View.VISIBLE);
                     imageNotChecked1.setVisibility(View.INVISIBLE);
                     pass1Ok=true;
                     if (!pass2.getText().toString().equals(pass1.getText().toString())){
                         imageChecked2.setVisibility(View.INVISIBLE);
                         imageNotChecked2.setVisibility(View.VISIBLE);
                         pass2Ok=false;
                         button.setEnabled(false);
                     }else{
                         imageChecked2.setVisibility(View.VISIBLE);
                         imageNotChecked2.setVisibility(View.INVISIBLE);
                         pass2Ok=true;
                         button.setEnabled(true);
                     }

                 }else{
                     imageChecked1.setVisibility(View.INVISIBLE);
                     imageNotChecked1.setVisibility(View.VISIBLE);
                     pass1Ok=false;
                     imageChecked2.setVisibility(View.INVISIBLE);
                     imageNotChecked2.setVisibility(View.VISIBLE);
                     pass2Ok=false;
                     button.setEnabled(false);
                 }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pass2=(EditText)findViewById(R.id.editTextRegPass2);
        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isValidPassword(s) && pass1Ok && pass2.getText().toString().equals(pass1.getText().toString()) && usernameOk){
                    imageChecked2.setVisibility(View.VISIBLE);
                    imageNotChecked2.setVisibility(View.INVISIBLE);
                    pass2Ok=true;
                    button.setEnabled(true);
                }else{
                    imageChecked2.setVisibility(View.INVISIBLE);
                    imageNotChecked2.setVisibility(View.VISIBLE);
                    pass2Ok=false;
                    button.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                username=userId.getText().toString();
                pass=pass1.getText().toString();


                showWaitDialog("Registrando...");

                try{
                    Usuario usuario = new Usuario(username, pass);
                    dbHelper.insert(usuario);
                    closeWaitDialog();
                    showDialogMessage("¡Registro completado!", "Bienvenido, "+username, true);
                }catch(SQLiteConstraintException e){
                    closeWaitDialog();
                    Log.d("Error DB", "Error de insercion insercion");
                    showDialogMessage("Fallo en el registro ", "El usuario ya existe", false);
                    e.printStackTrace();
                }
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
                        exit(username);
                    }
                } catch (Exception e) {
                    if(exit) {
                        exit(username);
                    }
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }
    private boolean isValidPassword(CharSequence s) {
        matcher=pattern.matcher(s);
        return matcher.matches();
    }
    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }
    private void showWaitDialog(String message) {
        closeWaitDialog();
        waitDialog = new ProgressDialog(this);
        waitDialog.setTitle(message);
        waitDialog.show();
    }
    private void exit(String uname) {
        Intent intent = new Intent();
        if(uname == null)
            uname = "";
        intent.putExtra("name",uname);
        setResult(RESULT_OK, intent);
        finish();
    }


}
