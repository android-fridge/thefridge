package fdi.ucm.thefridge.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.data.DatabaseAccess;
import fdi.ucm.thefridge.model.Publicacion;
import fdi.ucm.thefridge.model.SesionUsuario;

/**
 * Created by Carlos Casado González on 25/06/2016.
 */
public class PhotoActivity extends AppCompatActivity{
    private DatabaseAccess db;
    private EditText textoFoto;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        db = DatabaseAccess.getInstance(this);
        textoFoto=(EditText)findViewById(R.id.texto_foto);
        Button buttonFoto=(Button)findViewById(R.id.aceptar_foto);
        Button buttonAtras=(Button)findViewById(R.id.retroceder_foto);
        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/thefridge/"+getCode()+".jpg";
                File foto=new File(file);
                try {
                    foto.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri uri=Uri.fromFile(foto);
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //cameraIntent.setDataAndType(uri,"image/jpg");
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(cameraIntent,0);
                db.open();
                db.insertPublicacion(new Publicacion(0,SesionUsuario.getId(),textoFoto.getText().toString(),null,file));
                db.close();
            }
        });
        buttonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });



    }

    @SuppressLint("SimpleDateFormat")
    private String getCode(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode = SesionUsuario.getId()+"_pic_" + date;
        return photoCode;
     }
}
