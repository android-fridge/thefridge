package fdi.ucm.thefridge.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.data.DatabaseAccess;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.ListViewIngredientesAdapter;
import fdi.ucm.thefridge.model.Receta;

/**
 * Created by Carlos Casado González on 02/05/2016.
 */
public class OneclicContentFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout refreshLayout;
    private List<Receta> recetas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_oneclic, container, false);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        refreshLayout.setOnRefreshListener(this);
        Context c = getContext();
        DatabaseAccess db = DatabaseAccess.getInstance(c);
        db.open();
        recetas = db.getRecetas();
        db.close();

        return rootView;
    }
    private List<Receta> filtraRecetas(List<Receta> recetas) {
        List<Receta> recetas2 = new ArrayList<>();
        List<Ingrediente> ingredientes = ListViewIngredientesAdapter.getIngredientes();
        /*...*/
        return recetas2;
    }

    @Override
    public void onRefresh() {
        filtraRecetas(recetas);
    }
}