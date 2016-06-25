package fdi.ucm.thefridge.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.activities.DetailActivity;
import fdi.ucm.thefridge.activities.PhotoActivity;
import fdi.ucm.thefridge.data.DatabaseAccess;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.Receta;

/**
 * Created by Carlos Casado González on 02/05/2016.
 */
public class RecetasContentFragment extends Fragment implements AdapterView.OnItemSelectedListener,SearchView.OnQueryTextListener {
    private int CODIGO_ACTIVIDAD = 2;
    private List<Receta> mModels;
    ContentAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        Context c = getContext();
        DatabaseAccess db = DatabaseAccess.getInstance(c);
        db.open();
        mModels = db.getRecetas();
        db.close();

        //RecyclerView
        mAdapter = new ContentAdapter(c,mModels);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Spinner
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Todas las recetas");
        categories.add("Mi nevera");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        SearchView sView = (SearchView)rootView.findViewById(R.id.action_search);
        sView.setOnQueryTextListener(this);

        return rootView;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Receta> filteredModelList = filter(mModels, query);
        mAdapter.setModels(filteredModelList);
        //mAdapter.animateTo(filteredModelList);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Receta> filter(List<Receta> models, String query) {
        query = query.toLowerCase();

        final List<Receta> filteredModelList = new ArrayList<>();
        for (Receta model : models) {
            final String text = model.getNombre().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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

        }

    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<RecetaViewHolder>{
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
            final Receta receta = recetas.get(position);
            holder.nombreTextView.setText(receta.getNombre());
            holder.details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("Receta", receta);
                    context.startActivity(intent);
                }
            });

            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, PhotoActivity.class);
                    context.startActivity(intent);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("Receta", receta);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return recetas.size();
        }

        public void setModels(List<Receta> models) {
            recetas = new ArrayList<>(models);
        }

    }
}