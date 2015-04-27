package com.sngv.sunshine.Controller.Details;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sngv.sunshine.DB.domain.WeatherItem;
import com.sngv.sunshine.R;
import com.sngv.sunshine.Service.Utility;

public class DetailFragment extends Fragment {

    public DetailFragment() {
    }

    static String status = "true";
    private WeatherItem weatherItem;
    private TextView country , day , min , max;
    private TextView desc , speed , pressure , humidity , degree;
    private ImageView imageView;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        status = "true";
        view = inflater.inflate(R.layout.details_fragment, container, false);
        getExtra();
        init();
        setItem();
        return view;
    }

    private void init() {
        country = (TextView) view.findViewById(R.id.country_details);
        day = (TextView) view.findViewById(R.id.day_details);
        min = (TextView) view.findViewById(R.id.minGrade_details);
        max = (TextView) view.findViewById(R.id.maxGrade_details);
        desc = (TextView) view.findViewById(R.id.description_details);
        speed = (TextView) view.findViewById(R.id.windSpeed_details);
        pressure = (TextView) view.findViewById(R.id.pressure_details);
        humidity = (TextView) view.findViewById(R.id.humidity_details);
        degree = (TextView) view.findViewById(R.id.degree_details);
        imageView = (ImageView) view.findViewById(R.id.CloudImage_details);
    }

    private void setItem() {
        double maxD = Double.parseDouble(weatherItem.getMaxGrade());
        double minD = Double.parseDouble(weatherItem.getMinGrade());
        country.setText(weatherItem.getCountry());
        day.setText(weatherItem.getDay());
        min.setText(Utility.formatTemp(minD , getActivity().getApplicationContext()));
        max.setText(Utility.formatTemp(maxD , getActivity().getApplicationContext()));
        desc.setText(weatherItem.getDescription());
        speed.setText(weatherItem.getWindSpeed());
        pressure.setText(weatherItem.getPressure());
        humidity.setText(weatherItem.getHumidity());
        degree.setText(weatherItem.getDegree());
        imageView.setImageResource(Utility.getArtResourceForWeatherCondition(Integer.parseInt(weatherItem.getTempId())));
    }

    private void getExtra() {
        Intent intent = getActivity().getIntent();
        if(intent == null || !intent.hasExtra(Intent.EXTRA_TEXT)){
            Toast.makeText(getActivity(), "no intent :D ", Toast.LENGTH_LONG).show();
            return ;
        }
        weatherItem = (WeatherItem) intent.getSerializableExtra(Intent.EXTRA_TEXT);
    }
}
