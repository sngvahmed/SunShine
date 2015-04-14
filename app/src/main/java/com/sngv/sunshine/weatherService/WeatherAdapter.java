package com.sngv.sunshine.weatherService;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sngv.sunshine.R;
import com.sngv.sunshine.domain.WeatherItem;

import java.util.List;

/**
 * Created by sngv on 08/04/15.
 */
public class WeatherAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<WeatherItem> weatherItemList;

    public WeatherAdapter(Activity activity , List<WeatherItem> weatherItem){
        weatherItemList = weatherItem;
        this.activity = activity;
    }

    public WeatherAdapter(){}

    @Override
    public int getCount() {
        return weatherItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.clout_item, null);

        TextView day = (TextView) convertView.findViewById(R.id.day);
        TextView minGrade = (TextView) convertView.findViewById(R.id.minGrade);
        TextView maxGrade = (TextView) convertView.findViewById(R.id.maxGrade);
        TextView description = (TextView) convertView.findViewById(R.id.description);

        WeatherItem weatherItem = weatherItemList.get(position);
        day.setText(weatherItem.getDay());
        minGrade.setText("min : " + weatherItem.getMinGrade());
        maxGrade.setText("max : " + weatherItem.getMaxGrade());
        description.setText(weatherItem.getDescription());
        return convertView;
    }
}
