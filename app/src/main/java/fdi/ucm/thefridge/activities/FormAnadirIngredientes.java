package fdi.ucm.thefridge.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.ListViewIngredientesAdapter;

/**
 * Created by Michael Tome Rodriguez on 12/05/2016.
 */
public class FormAnadirIngredientes extends AppCompatActivity{


    // UI references.
    private TextView mIngredienteView;
    private AutoCompleteTextView ingredienteBuscado;
    private ArrayList<Ingrediente> buscados;
    private ListView lv;
    private LinearLayout all;
    private ListViewIngredientesAdapter adapter;
    private AlertDialog alert;
    private ArrayList<Ingrediente> datos;
    private ArrayList<String> nombresDeIngredientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_anadir_ingredientes);
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
                OutputStreamWriter escritor=null;
                try
                {
                    escritor=new OutputStreamWriter(openFileOutput("intern_fridge.txt", Context.MODE_APPEND));
                    for(int i = 0; i < buscados.size(); i++) {
                        escritor.write(buscados.get(i).getNombre() + "," + buscados.get(i).getImg() + "\n");
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
                //Cambio de actividad, se le pasa a la otra actividad el fragment donde se encontraba
                Context context = v.getContext();
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("viewpager_position", 2);
                context.startActivity(i);
                finish();
            }
        });

        //boton atras de la interfaz, simplemente vuelve atras
        Button retrocederCambios = (Button) findViewById(R.id.retroceder);

        retrocederCambios.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambio de actividad, se le pasa a la otra actividad el fragment donde se encontraba
                Context context = v.getContext();
                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("viewpager_position", 2);
                context.startActivity(i);
                finish();
            }
        });

        //boton flotante + en la interfaz
        FloatingActionButton anadir = (FloatingActionButton) findViewById(R.id.anadir);

        //alerta creada para cuando se incluye un ingrediente no valido
        alert = new AlertDialog.Builder(this).create();
        alert.setMessage("Ingrediente no válido");

        //Anade el ingrediente buscado a la lista temporal y permite anadir mas
        anadir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //cierra el teclado si estuviese abierto
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(ingredienteBuscado.getWindowToken(), 0);
                //Obtiene el texto introducido
                String ingrediente = ingredienteBuscado.getText().toString();
                if((ingrediente != null) && !ingrediente.isEmpty()){
                    ingredienteBuscado.setText("");
                    //busca el ingrediente con el texto
                    Ingrediente encontrado = busquedaBinaria(datos, ingrediente);
                    if(encontrado != null) {
                        buscados.add(encontrado);
                        lv.deferNotifyDataSetChanged();
                    }
                    else alert.show();
                }
                else{
                    alert.show();
                }
            }
        });
    }

    /**
     * Metodo para obtener los datos de la base de datos
     * @return Array con los nombres de los ingredientes, si no existen datos, es null
     */
    private ArrayList<String> obtenerIngredientes(){

        //Aqui se crearia un arrayList con los datos de los ingredientes de la bbdd
        datos = new ArrayList<>();
        datos.add(new Ingrediente("aaaa", R.drawable.ic_favorite_black_24dp));
        datos.add(new Ingrediente("bbbb", R.drawable.ic_favorite_black_24dp));
        datos.add(new Ingrediente("cccc", R.drawable.ic_favorite_black_24dp));
        datos.add(new Ingrediente("acv", R.drawable.ic_favorite_black_24dp));
        datos.add(new Ingrediente("ewq", R.drawable.ic_favorite_black_24dp));
        datos.add(new Ingrediente("fffsa", R.drawable.ic_favorite_black_24dp));
        datos.add(new Ingrediente("esto es real", R.drawable.ic_favorite_black_24dp));
        //Lo transformamos a un array de string y lo ordenamos, en caso de no estar ordenado
        //Esta ordenacion es opcional
        Collections.sort(datos, new Ingrediente());
        nombresDeIngredientes = new ArrayList<>();
        for(int i = 0; i < datos.size(); i++){
            nombresDeIngredientes.add(datos.get(i).getNombre());
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

