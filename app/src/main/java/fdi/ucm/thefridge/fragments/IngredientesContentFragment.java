package fdi.ucm.thefridge.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.activities.FormAnadirIngredientes;
import fdi.ucm.thefridge.data.DatabaseAccess;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.ListViewIngredientesAdapter;
import fdi.ucm.thefridge.model.SesionUsuario;

/**
 * Created by Carlos Casado González on 02/05/2016.
 */
public class IngredientesContentFragment extends Fragment{

    private DatabaseAccess dbAccess;
    private ListView lv;
    private ArrayList<Ingrediente> listIngredientes;
    private ListViewIngredientesAdapter adapter;
    private View rootView;
    private ArrayList<Ingrediente> listIngredientesDB;
    private static final int CODIGO_ACTIVIDAD = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbAccess=DatabaseAccess.getInstance(this.getContext());

        rootView = inflater.inflate(R.layout.list_ingrediente, container, false);
        listIngredientes = getIngredienetsNeveraDB();
        listIngredientesDB = GetlistIngredientesDB();

        lv = (ListView)rootView.findViewById(R.id.list_ingredientes);
        adapter = new ListViewIngredientesAdapter(getActivity(), listIngredientes);
        lv.setAdapter(adapter);
        FloatingActionButton addButton = (FloatingActionButton) rootView.findViewById(R.id.boton_anadir);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, FormAnadirIngredientes.class);
                //Pasar datos a la actividad
                intent.putExtra("nevera", listIngredientes);
                intent.putExtra("ingredientesDB", listIngredientesDB);
                //Necesario para activar la subactividad
                startActivityForResult(intent, CODIGO_ACTIVIDAD);

            }
        });

        return rootView;
    }

    /**
     * Llamado al momento de cerrar la aplicacion
     */
   public void onStop(){
       super.onStop();
            OutputStreamWriter escritor=null;
            try
            {
                escritor=new OutputStreamWriter(getActivity().openFileOutput("intern_fridge.txt", Context.MODE_PRIVATE));
                dbAccess.open();
                for(int i = 0; i < listIngredientes.size(); i++) {
                    escritor.write(listIngredientes.get(i).getId() + "," + listIngredientes.get(i).getNombre() + "," + listIngredientes.get(i).getRareza() + "," + listIngredientes.get(i).getcategoria() + "\n");
                    dbAccess.insertNevera(SesionUsuario.getIdNum(), listIngredientes.get(i).getId());
                }
                dbAccess.close();
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

            String line;
            while ((line = fin.readLine()) != null){

                String[] div = line.split(",");
                Ingrediente in = new Ingrediente(Integer.parseInt(div[0]), div[1], div[2], div[3].substring(0,1));
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

    private ArrayList<Ingrediente> getIngredienetsNeveraDB(){
        ArrayList<Ingrediente> listaIngredienteDB;
        dbAccess.open();
        listaIngredienteDB = dbAccess.getIngredientesNevera();
        dbAccess.close();
        return listaIngredienteDB;
    }
    /**
     * Metodo para añadir datos a la lista desde la BD
     * @return arrayList con los datos de la BD
     */
    private ArrayList<Ingrediente> GetlistIngredientesDB(){
        ArrayList<Ingrediente> listaIngredienteDB;
        dbAccess.open();
        listaIngredienteDB = dbAccess.getIngredientes();
        dbAccess.close();
        return listaIngredienteDB;
    }

    /**
     * Metodo que es llamado cuando una subactividad termina
     * @param requestCode codigo de la subactividad
     * @param resultCode codigo de resultado correcto o no
     * @param data intent de la subactividad
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        listIngredientes = new ArrayList<>();
                        listIngredientes = extras.getParcelableArrayList("listaDeAñadidos");
                        lv = (ListView) rootView.findViewById(R.id.list_ingredientes);
                        adapter = new ListViewIngredientesAdapter(getActivity(), listIngredientes);
                        lv.setAdapter(adapter);
                    }
                }
        }
    }
}