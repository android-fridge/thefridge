package fdi.ucm.thefridge.activities;

/**
 * Created by mizlap on 10/05/2016.
 */

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.data.DatabaseAccess;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.ListViewIngredientesAdapter;
import fdi.ucm.thefridge.model.Receta;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    ListView lving;
    ListView lvrecetas;
    ArrayList<String> listIngredientes;
    ArrayList<String> pasosReceta;
    ListViewIngredientesAdapter adapter;
    ArrayAdapter<String> adaptador_pasos;
    ArrayAdapter<String> adaptador_ingredientes;
    ImageButton dificultad;
    TextView descripcion;
    TextView horas;
    TextView personas;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Receta receta = (Receta)getIntent().getSerializableExtra("Receta");

        DatabaseAccess db = DatabaseAccess.getInstance(getBaseContext());
        db.open();
        listIngredientes = db.GetlistIngredientesReceta(receta.get_id());
        pasosReceta = db.getPasosReceta(receta.get_id());
        db.close();



        lving = (ListView) findViewById(R.id.ingredientes_de_recetas);
        adaptador_ingredientes = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, listIngredientes);
        lving.setAdapter(adaptador_ingredientes);

        lvrecetas = (ListView) findViewById(R.id.pasos_de_receta);
        adaptador_pasos = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, pasosReceta);
        lvrecetas.setAdapter(adaptador_pasos);

        dificultad = (ImageButton) findViewById(R.id.imagen_dificultad);
        switch (receta.getDificultad().charAt(0)){
            case '1':
                dificultad.setImageResource(R.drawable.d1o5dots);
                break;
            case '2':
                dificultad.setImageResource(R.drawable.d2o5dots);
                break;
            case '3':
                dificultad.setImageResource(R.drawable.d3o5dots);
                break;
            case '4':
                dificultad.setImageResource(R.drawable.d4o5dots);
                break;
            case '5':
                dificultad.setImageResource(R.drawable.d5o5dots);
                break;
        }

        horas = (TextView) findViewById(R.id.tiempo_receta);

        personas = (TextView) findViewById(R.id.personas_receta);
        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(receta.getNombre());
        horas.setText(receta.getDuracion());
        personas.setText(receta.getPersonas());
    }

}