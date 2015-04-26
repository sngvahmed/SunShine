package com.sngv.sunshine.weatherService;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sngv.sunshine.R;
import com.sngv.sunshine.domain.WeatherItem;

import java.util.List;

/**
 * Created by sngv on 08/04/15.
 */
public class WeatherAdapter extends CursorAdapter {
    public Context activity;
    public WeatherAdapter(Context context, Cursor c) {
        super(context, c);
        this.activity = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = convertView = inflater.inflate(R.layout.clout_item, null);
        return convertView;
    }

    @Override
    public void bindView(View convertView, Context context, Cursor cursor) {
        TextView day = (TextView) convertView.findViewById(R.id.day);
        TextView minGrade = (TextView) convertView.findViewById(R.id.minGrade);
        TextView maxGrade = (TextView) convertView.findViewById(R.id.maxGrade);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        WeatherItem weatherItem = new WeatherItem();
        weatherItem.setFromCursor(cursor);
        day.setText(weatherItem.getDay());
        minGrade.setText("min : " + weatherItem.getMinGrade());
        maxGrade.setText("max : " + weatherItem.getMaxGrade());
        description.setText(weatherItem.getDescription());
    }
}
