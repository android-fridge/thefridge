package fdi.ucm.thefridge.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import fdi.ucm.thefridge.R;

/**
 * Created by Carlos Casado González on 22/05/2016.
 */
public class TimelineContentFragment extends Fragment{

    View rootView;
    WebView browser;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_timeline, container, false);
        browser=(WebView) rootView.findViewById(R.id.webViewTimeline);


        browser.loadUrl("file:///android_asset/www/timeline.html");
        //browser.setBackgroundColor(Color.TRANSPARENT);
        //browser.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        browser.setBackgroundColor(Color.TRANSPARENT);
        //browser.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));


        return rootView;
    }

}
