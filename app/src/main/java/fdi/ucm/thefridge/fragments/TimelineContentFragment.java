package fdi.ucm.thefridge.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.ArrayList;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.data.DatabaseAccess;
import fdi.ucm.thefridge.model.Publicacion;

/**
 * Created by Carlos Casado González on 22/05/2016.
 */
public class TimelineContentFragment extends Fragment{

    private View rootView;
    private WebView browser;
    private static final int numPublic=5;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_timeline, container, false);
        Context context=getContext();
        DatabaseAccess db = DatabaseAccess.getInstance(context);
        db.open();
        ArrayList<Publicacion> listaPublic=db.getPublicaciones(numPublic);
        db.close();
        browser=(WebView) rootView.findViewById(R.id.webViewTimeline);
        browser.loadUrl("file:///android_asset/www/timeline.html");

        StringBuilder htmlData=new StringBuilder();

        htmlData.append("<!DOCTYPE html>" +
                "<html>" +
                "    <head>" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "        <link rel=\"stylesheet\" type=\"text/css\" href=\"timeline.css\">" +
                "    </head>" +
                "    <body>");
        for (int i=0; i<listaPublic.size();i++) {
            htmlData.append(" <div class=\"contenedorCard\">" +
                    "            <div class=\"msg\">" +
                    "                <img class=\"img\" src=\""+listaPublic.get(i).getImg()+"\">" +
                    "                <div class=\"descripcion\">"+listaPublic.get(i).getMensaje()+"</div>" +
                    "            </div>" +
                    "            <div class=\"separator\"></div>" +
                    "            <div class=\"msgInfo\">" +
                    "                <div class=\"id_usr\">"+listaPublic.get(i).getUsuario()+"</div> " +
                    "                <div class=\"fecha\">"+listaPublic.get(i).getFecha().toString()+"</div>" +
                    "            </div>" +
                    "        </div>");
        }
        htmlData.append("   " +
                "    </body>" +
                "</html>");

        //browser.loadData("sadasdasdf","text/html",null);
        browser.loadDataWithBaseURL("file:///android_asset/www/", htmlData.toString(), "text/html", "UTF-8",null);
        //browser.setBackgroundColor(Color.TRANSPARENT);
        //browser.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        browser.setBackgroundColor(Color.TRANSPARENT);
        //browser.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));


        return rootView;
    }

}
