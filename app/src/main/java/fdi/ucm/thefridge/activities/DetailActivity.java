package fdi.ucm.thefridge.activities;

/**
 * Created by mizlap on 10/05/2016.
 */

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.ListViewIngredientesAdapter;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    ListView lving;
    ListView lvrecetas;
    ArrayList<Ingrediente> listIngredientes;
    ArrayList<String> pasosReceta;
    ListViewIngredientesAdapter adapter;
    ArrayAdapter<String> adaptador;
    ImageButton dificultad;
    TextView descripcion;
    TextView horas;
    TextView personas;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        listIngredientes = GetlistIngredientesReceta();
        pasosReceta = getPasosReceta();
        lving = (ListView) findViewById(R.id.ingredientes_de_recetas);
        adapter = new ListViewIngredientesAdapter(this, listIngredientes);
        lving.setAdapter(adapter);

        lvrecetas = (ListView) findViewById(R.id.pasos_de_receta);
        adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, pasosReceta);
        lvrecetas.setAdapter(adaptador);

        dificultad = (ImageButton) findViewById(R.id.imagen_dificultad);

        descripcion = (TextView) findViewById(R.id.descripcion_receta);

        horas = (TextView) findViewById(R.id.tiempo_receta);

        personas = (TextView) findViewById(R.id.personas_receta);
        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(getString(R.string.item_title));
    }

    private ArrayList<String> getPasosReceta() {
        ArrayList<String> r = new ArrayList<>();

        return r;
    }

    private ArrayList<Ingrediente> GetlistIngredientesReceta() {
        ArrayList<Ingrediente> ing = new ArrayList<>();
        return ing;
    }
}