package fdi.ucm.thefridge.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.activities.formAnadirIngredientes;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.ListViewIngredientesAdapter;

/**
 * Created by Carlos Casado González on 02/05/2016.
 */
public class IngredientesContentFragment extends Fragment{

    ListView lv;
    ArrayList<Ingrediente> listIngredientes;
    ListViewIngredientesAdapter adapter;
    View rootView;
    WebView form;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.list_ingrediente, container, false);

        listIngredientes = GetlistIngredientes();

        lv = (ListView)rootView.findViewById(R.id.list_ingredientes);
        adapter = new ListViewIngredientesAdapter(getActivity(), listIngredientes);
        lv.setAdapter(adapter);
        FloatingActionButton addButton = (FloatingActionButton) rootView.findViewById(R.id.boton_anadir);

       /** form = (WebView) rootView.findViewById(R.id.webview);
        form.getSettings().setDomStorageEnabled(true);
        form.getSettings().setUseWideViewPort(true);
        form.getSettings().setJavaScriptEnabled(true);*/
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, formAnadirIngredientes.class);
                context.startActivity(intent);
                /** form.loadUrl("https://amatellanes.wordpress.com/");
                //Forzar a la aplicacion para que habra enlaces internos
                form.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        form.getSettings().setJavaScriptEnabled(true); // Permitimos que se ejecute JavaScript
                        form.getSettings().setLoadWithOverviewMode(true); // Ajustamos la vista para que no se vea demasiado grande
                        form.getSettings().setUseWideViewPort(true); // habilitamos el zoom
                        form.getSettings().setBuiltInZoomControls(true); // habilitamos el zoom
                        form.setInitialScale(100); // Escala inicial al 100%
                        view.loadUrl(url);
                        return true;
                    }
                });*/

            }
        });

        return rootView;
    }

    public void onPause(){
        OutputStreamWriter escritor=null;
        try
        {
            escritor=new OutputStreamWriter(getActivity().openFileOutput("intern_fridge.txt", Context.MODE_PRIVATE));
            for(int i = 0; i < listIngredientes.size(); i++) {
                escritor.write(listIngredientes.get(i).getNombre() + "," + listIngredientes.get(i).getImg() + "\n");
            }
            escritor.flush();
            escritor.close();
        }
        catch (Exception ex)
        {
            Log.e("error", "Error al escribir fichero a memoria interna");
        }
        finally
        {
            try {
                if(escritor!=null)
                    escritor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onDestroy(){

            OutputStreamWriter escritor=null;
            try
            {
                escritor=new OutputStreamWriter(getActivity().openFileOutput("intern_fridge.txt", Context.MODE_PRIVATE));
                for(int i = 0; i < listIngredientes.size(); i++) {
                    escritor.write(listIngredientes.get(i).getNombre() + "," + listIngredientes.get(i).getImg() + "\n");
                }
                escritor.flush();
                escritor.close();
            }
            catch (Exception ex)
            {
                Log.e("error", "Error al escribir fichero a memoria interna");
            }
            finally
            {
                try {
                    if(escritor!=null)
                        escritor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    /**
     * Metodo para añadir datos a la lista
     * @return arrayList con los datos del fichero
     */
    private ArrayList<Ingrediente> GetlistIngredientes(){
        ArrayList<Ingrediente> listaIngrediente = new ArrayList<>();
        try
        {
            InputStreamReader arch = new InputStreamReader(getActivity().openFileInput("intern_fridge.txt"));
            BufferedReader fin = new BufferedReader(arch);

            String line = null;
            while ((line = fin.readLine()) != null){

                String[] div = line.split(",");
                Ingrediente in = new Ingrediente(div[0], Integer.parseInt(div[1]));
                listaIngrediente.add(in);
            }
            fin.close();
            arch.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }

        return listaIngrediente;
    }
}