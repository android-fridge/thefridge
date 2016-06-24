package fdi.ucm.thefridge.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.activities.DetailActivity;
import fdi.ucm.thefridge.data.DatabaseAccess;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.Receta;

/**
 * Created by Carlos Casado González on 02/05/2016.
 */
public class RecetasContentFragment extends Fragment {
    private int CODIGO_ACTIVIDAD = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        Context c = getContext();
        DatabaseAccess db = DatabaseAccess.getInstance(c);
        db.open();
        List<Receta> recetas = db.getRecetas();
        db.close();
        ContentAdapter adapter = new ContentAdapter(c,recetas);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    public static class RecetaViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreTextView;
        public Button details;
        public ImageButton share;

        public RecetaViewHolder(View itemView) {
            super(itemView);
            nombreTextView = (TextView) itemView.findViewById(R.id.card_text);
            details = (Button) itemView.findViewById(R.id.action_button);
            share = (ImageButton) itemView.findViewById(R.id.share_button);

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        /*public RecetaViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_recetas, parent, false));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }*/


    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<RecetaViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;
        private List<Receta> recetas;
        private Context context;

        public ContentAdapter(Context context,List<Receta> list) {
            this.recetas = list;
            this.context = context;
        }

        @Override
        public RecetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View recetaView = inflater.inflate(R.layout.item_recetas, parent, false);

            RecetaViewHolder viewHolder = new RecetaViewHolder(recetaView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecetaViewHolder holder, int position) {
            Receta receta = recetas.get(position);
            holder.nombreTextView.setText(receta.getNombre());
        }

        @Override
        public int getItemCount() {
            return recetas.size();
        }
    }
}