package fdi.ucm.thefridge.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.data.DatabaseAccess;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.ListViewIngredientesAdapter;
import fdi.ucm.thefridge.model.SesionUsuario;

/**
 * Created by Michael Tome Rodriguez on 12/05/2016.
 */
public class FormAnadirIngredientes extends AppCompatActivity{


    // UI references.
    private DatabaseAccess dbAccess;
    private TextView mIngredienteView;
    private AutoCompleteTextView ingredienteBuscado;
    private ArrayList<Ingrediente> buscados;
    private ListView lv;
    private LinearLayout all;
    private ListViewIngredientesAdapter adapter;
    private ArrayList<Ingrediente> neveraInterna;
    private ArrayList<Ingrediente> ingredientesDB;
    private ArrayList<String> nombresDeIngredientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_anadir_ingredientes);

        dbAccess=DatabaseAccess.getInstance(this);
        neveraInterna = new ArrayList<>();
        ingredientesDB = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            neveraInterna =  extras.getParcelableArrayList("nevera");
            ingredientesDB =  extras.getParcelableArrayList("ingredientesDB");
            if(neveraInterna == null) {
                neveraInterna = new ArrayList<>();
                ingredientesDB = new ArrayList<>();
            }
        }

        //Ingredientes buscados
        buscados = new ArrayList<>();
        //Se crea el adaptador para la lista
        adapter = new ListViewIngredientesAdapter(this, buscados);
        //Se obtiene la lista
        lv = (ListView) findViewById(R.id.ingredientes_a_anadir);
        //Le damos el adapter a la lista
        lv.setAdapter(adapter);


        //Campo de texto
        mIngredienteView = (TextView) findViewById(R.id.texto_buscar_ingrediente);

        //Autocompletado
        ingredienteBuscado = (AutoCompleteTextView) findViewById(R.id.ingrediente_buscado);
        ///Obtener datos del archivo de ingredientes globales
        ArrayList<String> datosAuto = this.obtenerIngredientes();
        //Numero de objetos seleccionados para cerrar el desplegable
        ingredienteBuscado.setThreshold(1);
        //Preparar adaptador para el autocompletado
        ArrayAdapter<String> adapterAutoComp = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datosAuto);
        //Le damos el adapter al autocompletado
        ingredienteBuscado.setAdapter(adapterAutoComp);

        ingredienteBuscado.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (event != null ) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(ingredienteBuscado.getWindowToken(), 0);
                    //Obtiene el texto introducido
                    String ingrediente = ingredienteBuscado.getText().toString();
                    if(!ingrediente.isEmpty()){
                        ingredienteBuscado.setText("");
                        //busca el ingrediente con el texto
                        Ingrediente encontrado = busquedaBinaria(ingredientesDB, ingrediente);
                        if(encontrado != null) {
                            Ingrediente repetido = busquedaBinaria(neveraInterna, encontrado.getNombre());
                            if(repetido == null) {
                                Ingrediente repetidoTemp = busquedaBinaria(buscados, encontrado.getNombre());
                                if(repetidoTemp == null) {
                                    buscados.add(encontrado);
                                    lv.deferNotifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(v.getContext(), "¡Ya está en la lista!",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(v.getContext(), "¡Ya está en la nevera!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(v.getContext(), "Ingrediente no encontrado",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    
                    // Must return true here to consume event
                    return true;

                }
                return false;
            }
        });

        //Caja de contenido
        all = (LinearLayout) findViewById(R.id.form_ingediente_todo);
        //listener para poder cerrar el teclado virtual al presionar en otro lado distinto al editable
        all.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cierre del teclado
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(ingredienteBuscado.getWindowToken(), 0);
            }
        });

        //Boton aceptar de la interfaz
        Button aceptarCambios = (Button) findViewById(R.id.aceptar_nuevo_ingrediente);
        //Al aceptar guarda los nuevos datos en el archivo y cambia de actividad
        aceptarCambios.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reordenamos el array de la nevera con todos los datos agregados
                for(int i = 0; i < buscados.size(); i++){
                    neveraInterna.add(buscados.get(i));
                }
                Collections.sort(neveraInterna, new Ingrediente());
                OutputStreamWriter escritor=null;
                try
                {
                    escritor=new OutputStreamWriter(openFileOutput("intern_fridge.txt", Context.MODE_PRIVATE));
                    for(int i = 0; i < buscados.size(); i++) {
                        escritor.write(neveraInterna.get(i).getId() + "," + neveraInterna.get(i).getNombre() + "," + neveraInterna.get(i).getRareza() + "," + neveraInterna.get(i).getcategoria() + "\n");
                        dbAccess.insertNevera(SesionUsuario.getIdNum(), neveraInterna.get(i).getId());
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
                //Finaliza la actividad y se le pasa a la actividad padre el array de la nevera
                Context context = v.getContext();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("listaDeAñadidos", neveraInterna);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        //boton atras de la interfaz, simplemente vuelve atras
        Button retrocederCambios = (Button) findViewById(R.id.retroceder);

        retrocederCambios.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambio de actividad
                setResult(RESULT_CANCELED, null);
                finish();
            }
        });

        //boton flotante + en la interfaz
        FloatingActionButton anadir = (FloatingActionButton) findViewById(R.id.anadir);

        //Anade el ingrediente buscado a la lista temporal y permite anadir mas
        anadir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //cierra el teclado si estuviese abierto
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(ingredienteBuscado.getWindowToken(), 0);
                //Obtiene el texto introducido
                String ingrediente = ingredienteBuscado.getText().toString();
                if(!ingrediente.isEmpty()){
                    ingredienteBuscado.setText("");
                    //busca el ingrediente con el texto
                    Ingrediente encontrado = busquedaBinaria(ingredientesDB, ingrediente);
                    if(encontrado != null) {
                        Ingrediente repetido = busquedaBinaria(neveraInterna, encontrado.getNombre());
                        if(repetido == null) {
                            Ingrediente repetidoTemp = busquedaBinaria(buscados, encontrado.getNombre());
                            if(repetidoTemp == null) {
                                buscados.add(encontrado);
                                lv.deferNotifyDataSetChanged();
                            }
                            else{
                                Toast.makeText(v.getContext(), "¡Ya está en la lista!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(v.getContext(), "¡Ya está en la nevera!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(v.getContext(), "Ingrediente no encontrado",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(v.getContext(), "Ingrediente no encontrado",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Metodo para obtener los datos de la base de datos
     * @return Array con los nombres de los ingredientes, si no existen datos, es null
     */
    private ArrayList<String> obtenerIngredientes(){
        nombresDeIngredientes = new ArrayList<>();
        for(int i = 0; i < ingredientesDB.size(); i++){
            nombresDeIngredientes.add(ingredientesDB.get(i).getNombre());
        }
        return nombresDeIngredientes;
    }

    /**
     * Metodo necesario para el historial de autocompletado
     * @param item los items del historial
     * @return el item selecionado
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Busuqeda para encontrar el string y obtener el ingrediente, una busqueda binaria ordinaria
     * @param bd base de datos
     * @param ingrediente nombre del ingrediente
     * @return ingrediente encontrado
     */
    private Ingrediente busquedaBinaria(ArrayList<Ingrediente> bd, String ingrediente) {
        int izq, der, cen = 0;
        boolean band=false;
        izq=0; der=bd.size()-1;
        while((izq<=der) && (!band)) {
            cen=((izq+der)/2);
            if((ingrediente.compareTo(bd.get(cen).getNombre())) == 0){
                band=true;
            } else if((ingrediente.compareTo(bd.get(cen).getNombre())) < 0){
                    der=cen-1;
                } else
                    izq=cen+1;

        }
        if(band){
            return bd.get(cen);
        } else{
            return null;
        }
    }
}

