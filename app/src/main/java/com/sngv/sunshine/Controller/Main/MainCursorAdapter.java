package com.sngv.sunshine.Controller.Main;

import android.content.Context;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sngv.sunshine.R;
import com.sngv.sunshine.DB.domain.WeatherItem;

/**
 * Created by sngv on 08/04/15.
 */
public class MainCursorAdapter extends CursorAdapter {
    private Context activity;
    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FORECAST = 1;

    public MainCursorAdapter(Context context, Cursor c) {
        super(context, c);
        this.activity = context;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_TODAY : VIEW_TYPE_FORECAST;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        if(getItemViewType(cursor.getPosition()) == 0)
            return LayoutInflater.from(context).inflate(R.layout.today  , parent , false);
        return LayoutInflater.from(context).inflate(R.layout.clout_item , parent , false);

    }

    @Override
    public void bindView(View convertView, Context context, Cursor cursor) {
        if(getItemViewType(cursor.getPosition()) == 0){
            WeatherItem today = new WeatherItem();
            today.setFromCursor(cursor);
            ((TextView)convertView.findViewById(R.id.day_mainActivity)).setText(today.getDay());
            ((TextView)convertView.findViewById(R.id.maxGrade_mainActivity)).setText(today.getMaxGrade());
            ((TextView)convertView.findViewById(R.id.minGrade_mainActivity)).setText(today.getMinGrade());
            ((TextView)convertView.findViewById(R.id.description_mainActivity)).setText(today.getDescription());
        }else {
            TextView day = (TextView) convertView.findViewById(R.id.day_cloudItem);
            TextView minGrade = (TextView) convertView.findViewById(R.id.minGrade_cloudItem);
            TextView maxGrade = (TextView) convertView.findViewById(R.id.maxGrade_cloudItem);
            TextView description = (TextView) convertView.findViewById(R.id.description_cloudItem);

            WeatherItem weatherItem = new WeatherItem();
            weatherItem.setFromCursor(cursor);
            day.setText(weatherItem.getDay());
            minGrade.setText("min : " + weatherItem.getMinGrade() + "°");
            maxGrade.setText("max : " + weatherItem.getMaxGrade() + "°");
            description.setText(weatherItem.getDescription());
        }
    }
}
