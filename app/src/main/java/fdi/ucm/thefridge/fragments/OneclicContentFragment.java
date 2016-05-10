package fdi.ucm.thefridge.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fdi.ucm.thefridge.R;

/**
 * Created by Carlos Casado González on 02/05/2016.
 */
public class OneclicContentFragment extends Fragment{
    private SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_oneclic, null);
    }

}