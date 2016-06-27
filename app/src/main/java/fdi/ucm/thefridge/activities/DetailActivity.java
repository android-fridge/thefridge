package fdi.ucm.thefridge.activities;

/**
 * Created by mizlap on 10/05/2016.
 */


import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.data.DatabaseAccess;

import fdi.ucm.thefridge.model.ExpandableHeightListView;
import fdi.ucm.thefridge.model.Receta;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    ExpandableHeightListView lving;
    ExpandableHeightListView lvrecetas;
    ArrayList<String> listIngredientes;
    String[] pasosReceta;
    ArrayAdapter adaptador_pasos;
    ArrayAdapter adaptador_ingredientes;
    TextView horas;
    TextView personas;
    TextView dificultadText;


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

        lving = (ExpandableHeightListView) findViewById(R.id.ingredientes_de_recetas);
        adaptador_ingredientes = new ArrayAdapter<String>(this,R.layout.item_listview, listIngredientes);
        lving.setAdapter(adaptador_ingredientes);
        lving.setExpanded(true);
        lvrecetas = (ExpandableHeightListView) findViewById(R.id.pasos_de_receta);
        adaptador_pasos = new ArrayAdapter<String>(this,R.layout.item_listview, pasosReceta);
        lvrecetas.setAdapter(adaptador_pasos);
        lvrecetas.setExpanded(true);

        dificultadText = (TextView) findViewById(R.id.dificultad_text);
        switch (receta.getDificultad().charAt(0)){
            case '1':
                dificultadText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.d1o5dots, 0);
                break;
            case '2':
                dificultadText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.d2o5dots, 0);
                break;
            case '3':
                dificultadText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.d3o5dots, 0);
                break;
            case '4':
                dificultadText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.d4o5dots, 0);
                break;
            case '5':
                dificultadText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.d5o5dots, 0);
                break;
        }

        horas = (TextView) findViewById(R.id.tiempo_receta);
        personas = (TextView) findViewById(R.id.personas_receta);
        horas.setText(" "+receta.getDuracion());
        personas.setText(" "+receta.getPersonas());
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(receta.getNombre());
    }

}