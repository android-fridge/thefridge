package fdi.ucm.thefridge.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import fdi.ucm.thefridge.R;

/**
 * Created by Michael Tome Rodriguez on 03/05/2016.
 * Clase necesaria para adaptar la vista de la lista de ingredientes
 */
public class ListViewIngredientesAdapter extends ArrayAdapter<Ingrediente> {

    private LayoutInflater mInflater;
    private Context context;
    private static ArrayList<Ingrediente> ingredientes;


    public ListViewIngredientesAdapter(Context context, ArrayList<Ingrediente> ingredientes){
        super(context, R.layout.list_ingrediente, ingredientes);
        this.context = context;
        this.ingredientes = ingredientes;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stu
        if (convertView == null) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_ingredientes, null);
        }
        //inicializaciones
        //CardView card = (CardView)  convertView.findViewById(R.id.card_ingrediente);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre_ingrediente);
        TextView rareza = (TextView) convertView.findViewById(R.id.rareza_ingrediente);
        TextView categoria = (TextView) convertView.findViewById(R.id.categoria_ingrediente);
        ImageButton delButton = (ImageButton) convertView.findViewById(R.id.botonBorrar);

        //Aciones de los botones de borrado
        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                String nombre = ingredientes.get(position).getNombre();
                String rareza = ingredientes.get(position).getRareza();
                String categoria = ingredientes.get(position).getcategoria();
                ingredientes.remove(position); //or some other task
                notifyDataSetChanged();
            }

        });

        //dar texto e imagen
        nombre.setText(ingredientes.get(position).getNombre());
        rareza.setText("Rareza: " + ingredientes.get(position).getRareza());
        categoria.setText(ingredientes.get(position).getcategoria());
        return convertView;
    }
    public static ArrayList<Ingrediente> getIngredientes(){
        return ingredientes;
    }

}