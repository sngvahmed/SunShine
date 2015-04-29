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
import com.sngv.sunshine.Utility.Utility;

public class DetailFragment extends Fragment {

    public DetailFragment() {
    }

    static String status = "true";
    private WeatherItem weatherItem = null;
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
        double degreed = Double.parseDouble(weatherItem.getDegree());
        double windSpeed = Double.parseDouble(weatherItem.getWindSpeed());
        double pressured = Double.parseDouble(weatherItem.getPressure());
        double humitiy = Double.parseDouble(weatherItem.getHumidity());

        country.setText(weatherItem.getCountry());
        day.setText(weatherItem.getDay());
        min.setText(Utility.formatTemp(minD , getActivity().getApplicationContext()));
        max.setText(Utility.formatTemp(maxD , getActivity().getApplicationContext()));
        desc.setText(weatherItem.getDescription());
        speed.setText(Utility.formatWindSpeed(windSpeed , getActivity().getApplicationContext()));
        pressure.setText(Utility.formatPressure(pressured, getActivity().getApplicationContext()));
        humidity.setText(Utility.formatHumidity(humitiy , getActivity().getApplicationContext()));
        degree.setText(Utility.formatDegree(degreed, getActivity().getApplicationContext()));
        imageView.setImageResource(Utility.getArtResourceForWeatherCondition(Integer.parseInt(weatherItem.getTempId())));
        imageView.setContentDescription(weatherItem.getDescription());
    }

    private void getExtra() {
        Intent intent = getActivity().getIntent();
        Bundle bundle = getArguments();
        if(bundle != null){
            weatherItem = (WeatherItem) bundle.getSerializable(DetailActivity.DATE_KEY);
        }

        if(weatherItem == null && intent != null){
            weatherItem = (WeatherItem) intent.getSerializableExtra(Intent.EXTRA_TEXT);
        }
        if(weatherItem == null){
            Toast.makeText(getActivity() , "error on data transfer" , Toast.LENGTH_LONG).show();
            return;
        }
        init();
        setItem();
    }
}
