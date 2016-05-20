package fdi.ucm.thefridge.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private ArrayList<Ingrediente> ingredientes;


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
        ImageView img = (ImageView) convertView.findViewById(R.id.imagen_ingrediente);
        ImageButton delButton = (ImageButton) convertView.findViewById(R.id.botonBorrar);

        //Aciones de los botones de borrado
        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                String nombre = ingredientes.get(position).getNombre();
                String rareza = ingredientes.get(position).getRareza();
                int img = ingredientes.get(position).getImg();
                ingredientes.remove(position); //or some other task
                notifyDataSetChanged();
            }

        });

        //dar texto e imagen
        nombre.setText(ingredientes.get(position).getNombre());
        rareza.setText(ingredientes.get(position).getRareza());
        img.setImageResource(ingredientes.get(position).getImg());
        return convertView;
    }
}