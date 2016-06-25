package fdi.ucm.thefridge.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.model.SesionUsuario;

/**
 * Created by Carlos Casado González on 25/06/2016.
 */
public class PhotoActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

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

    }

    @SuppressLint("SimpleDateFormat")
    private String getCode(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode = SesionUsuario.getId()+"_pic_" + date;
        return photoCode;
     }
}
