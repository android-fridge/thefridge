package fdi.ucm.thefridge.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.ListViewIngredientesAdapter;

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

        rootView = inflater.inflate(R.layout.list_ingrediente, container, false);

        listIngredientes = GetlistIngredientes();

        lv = (ListView)rootView.findViewById(R.id.list_ingredientes);
        adapter = new ListViewIngredientesAdapter(getActivity(), listIngredientes);
        lv.setAdapter(adapter);
        FloatingActionButton addButton = (FloatingActionButton) rootView.findViewById(R.id.boton_anadir);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }

    /**
     * Metodo para escribir en el archivo local
     */
    public void escribirArchivoEnLocal(Ingrediente ing){

    }

    /**
     * Metodo para añadir datos a la lista
     * @return arrayList con los datos del fichero
     */
    private ArrayList<Ingrediente> GetlistIngredientes(){
        ArrayList<Ingrediente> listaIngrediente = new ArrayList<>();


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