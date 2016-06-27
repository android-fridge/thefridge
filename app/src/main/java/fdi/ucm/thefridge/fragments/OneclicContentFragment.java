package fdi.ucm.thefridge.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fdi.ucm.thefridge.R;
import fdi.ucm.thefridge.activities.DetailActivity;
import fdi.ucm.thefridge.activities.PhotoActivity;
import fdi.ucm.thefridge.data.DatabaseAccess;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.ListViewIngredientesAdapter;
import fdi.ucm.thefridge.model.Receta;

/**
 * Created by Carlos Casado González on 02/05/2016.
 */
public class OneclicContentFragment extends Fragment{
    private SwipeRefreshLayout refreshLayout;
    private List<Receta> recetas;
    private View rootView;
    private TextView nomb;
    private Receta receta;
    private Button detail;
    private ImageButton share;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.item_oneclic2, container, false);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_oneclic);
        Context c = getContext();
        DatabaseAccess db = DatabaseAccess.getInstance(c);
        db.open();
        recetas = db.getRecetasNevera();
        receta=recetas.get(randomize(recetas.size()-1));
        nomb = (TextView) rootView.findViewById(R.id.card_text_oneclic);
        nomb.setText(receta.getNombre());
        detail=(Button)rootView.findViewById(R.id.action_button_oneclic);
        share=(ImageButton)rootView.findViewById(R.id.share_button_oneclic);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                receta=recetas.get(randomize(recetas.size()-1));
                nomb.setText(receta.getNombre());
                refreshLayout.setRefreshing(false);
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Receta", receta);
                context.startActivity(intent);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, PhotoActivity.class);
                context.startActivity(intent);
            }
        });


        return rootView;
    }

    public int randomize(int max){
        Random r=new Random();
        return r.nextInt(max+1);
    }
}