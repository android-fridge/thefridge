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
            mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_ingredientes, null);
        }
        //inicializaciones
        //CardView card = (CardView)  convertView.findViewById(R.id.card_ingrediente);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre_ingrediente);
        ImageView img = (ImageView) convertView.findViewById(R.id.imagen_ingrediente);
        ImageButton delButton = (ImageButton) convertView.findViewById(R.id.botonBorrar);

        //Aciones de los botones de borrado
        delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                String nombre = ingredientes.get(position).getNombre();
                int img = ingredientes.get(position).getImg();
                ingredientes.remove(position); //or some other task
                /** try
                 {
                 BufferedReader fin =
                 new BufferedReader(
                 new InputStreamReader(
                 context.openFileInput("intern_fridge.txt")));

                 String line = null;
                 while ((line = fin.readLine()) != null){
                 if(!line.equals(linea)){
                 String[] div = line.split(",");
                 Ingrediente in = new Ingrediente(div[0], Integer.parseInt(div[1]));
                 ingredientes.add(in);
                 }
                 }
                 fin.close();
                 }
                 catch (Exception ex)
                 {
                 Log.e("Ficheros", "Error al leer fichero desde memoria interna");
                 }*/
                notifyDataSetChanged();
            }

        });

        //dar texto e imagen
        nombre.setText(ingredientes.get(position).getNombre());
        img.setImageResource(ingredientes.get(position).getImg());
        return convertView;
    }
}