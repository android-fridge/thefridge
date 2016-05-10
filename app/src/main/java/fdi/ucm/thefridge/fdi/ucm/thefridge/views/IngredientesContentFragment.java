package fdi.ucm.thefridge.fdi.ucm.thefridge.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;

import java.util.ArrayList;

import fdi.ucm.thefridge.fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.fdi.ucm.thefridge.model.ListViewIngredientesAdapter;
import fdi.ucm.thefridge.R;

/**
 * Created by Carlos Casado González on 02/05/2016.
 */
public class IngredientesContentFragment extends Fragment {

    ListView lv;
    ArrayList<Ingrediente> listIngredientes;
    ListViewIngredientesAdapter adapter;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_ingredientes, container, false);

        listIngredientes = GetlistIngredientes();
        lv = (ListView)rootView.findViewById(R.id.lista);
        adapter = new ListViewIngredientesAdapter(getActivity(), listIngredientes);
        lv.setAdapter(adapter);
        FloatingActionButton addButton = (FloatingActionButton) rootView.findViewById(R.id.botonAnadir);
        final WebView visorHTML = (WebView)rootView.findViewById(R.id.webview);

        //colocar aqui las reacciones de la lista

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                visorHTML.loadUrl("file:///android_asset/anadir.html");
                WebSettings webSettings = visorHTML.getSettings();
                webSettings.setJavaScriptEnabled(true);
                Log.v("tag", "Añadido");
            }
        });



        return rootView;
    }

    private ArrayList<Ingrediente> GetlistIngredientes(){
        ArrayList<Ingrediente> listaIngrediente = new ArrayList<Ingrediente>();

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadsa");
        listaIngrediente.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadsa");
        listaIngrediente.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadas1");
        listaIngrediente.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadas1");
        listaIngrediente.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadas1");
        listaIngrediente.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadas1");
        listaIngrediente.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadas1");
        listaIngrediente.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadas1");
        listaIngrediente.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadas1");
        listaIngrediente.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadas1");
        listaIngrediente.add(ingrediente);

        ingrediente = new Ingrediente();
        ingrediente.setNombre("dsadas1");
        listaIngrediente.add(ingrediente);

        return listaIngrediente;
    }


}