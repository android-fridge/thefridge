package fdi.ucm.thefridge.fdi.ucm.thefridge.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.thefridge.R;

/**
 * Created by mizlap on 08/05/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RecetaViewHolder>{

    private List<Receta> recetas;

    public RVAdapter(List<Receta> recetas){
        this.recetas = recetas;
    }



    // This method creates an ArrayList that has three Receta objects
    private void initializeData(){
        recetas = new ArrayList<>();
        recetas.add(new Receta("Pollo Tikka Masala", "Plato especiado de la India", R.drawable.paris));
        recetas.add(new Receta("Pizza tropical", "Comida tradicional italiana", R.drawable.paris));
        recetas.add(new Receta("Lavash de ternera", "Exótico kebap de ternera", R.drawable.paris));
    }

    @Override
    public RecetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recetas, parent, false);
        RecetaViewHolder rvh = new RecetaViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecetaViewHolder holder, int i) {
        holder.recetaNombre.setText(recetas.get(i).nombre);
        holder.recetaTexto.setText(recetas.get(i).descripcion);
        holder.recetaImagen.setImageResource(recetas.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return recetas.size();
    }

    public static class RecetaViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView recetaNombre;
        TextView recetaTexto;
        ImageView recetaImagen;

        RecetaViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            recetaNombre = (TextView)itemView.findViewById(R.id.card_title);
            recetaTexto = (TextView)itemView.findViewById(R.id.card_text);
            recetaImagen = (ImageView)itemView.findViewById(R.id.card_image);
        }
    }
}
