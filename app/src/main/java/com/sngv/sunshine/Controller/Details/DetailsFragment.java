package com.sngv.sunshine.Controller.Details;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sngv.sunshine.R;

public class DetailsFragment extends Fragment {
    static String status = "true";

    TextView reciveFromIntent;
    View view;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        status = "true";
        view = inflater.inflate(R.layout.fragment_details_, container, false);
        init();
        loadOnStratUp();
        return view;
    }

    private void init() {
        reciveFromIntent = (TextView) view.findViewById(R.id.detalisFragment);
    }

    private void loadOnStratUp() {
        Intent intent = getActivity().getIntent();
        if(intent == null || !intent.hasExtra(Intent.EXTRA_TEXT)){
            status = "false";
            return ;
        }
        String data = intent.getExtras().get(Intent.EXTRA_TEXT).toString();
        reciveFromIntent.setText(data);
    }
}