package com.sngv.sunshine.Controller.Main;

import android.content.Context;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sngv.sunshine.R;
import com.sngv.sunshine.DB.domain.WeatherItem;
import com.sngv.sunshine.Service.Utility;

/**
 * Created by sngv on 08/04/15.
 */
public class MainCursorAdapter extends CursorAdapter {
    private Context context;
    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FORECAST = 1;
    private boolean mUseTodayLayout = true;

    public MainCursorAdapter(Context context, Cursor c) {
        super(context, c);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FORECAST;
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
            double max = Double.parseDouble(today.getMaxGrade());
            double min = Double.parseDouble(today.getMinGrade());
            ((TextView)convertView.findViewById(R.id.day_mainActivity)).setText(today.getDay());
            ((TextView)convertView.findViewById(R.id.maxGrade_mainActivity)).setText(Utility.formatTemp(max , context));
            ((TextView)convertView.findViewById(R.id.minGrade_mainActivity)).setText(Utility.formatTemp(min,context));
            ((TextView)convertView.findViewById(R.id.description_mainActivity)).setText(today.getDescription());
            ((ImageView)convertView.findViewById(R.id.CloudImage_mainActitivty)).
                    setImageResource(Utility.getArtResourceForWeatherCondition(Integer.parseInt(today.getTempId())));

        }else {
            TextView day = (TextView) convertView.findViewById(R.id.day_cloudItem);
            TextView minGrade = (TextView) convertView.findViewById(R.id.minGrade_cloudItem);
            TextView maxGrade = (TextView) convertView.findViewById(R.id.maxGrade_cloudItem);
            TextView description = (TextView) convertView.findViewById(R.id.description_cloudItem);
            WeatherItem weatherItem = new WeatherItem();
            weatherItem.setFromCursor(cursor);
            double max = Double.parseDouble(weatherItem.getMaxGrade());
            double min = Double.parseDouble(weatherItem.getMinGrade());
            day.setText(weatherItem.getDay());
            minGrade.setText(Utility.formatTemp(min,context));
            maxGrade.setText(Utility.formatTemp(max,context));
            description.setText(weatherItem.getDescription());
            ((ImageView)convertView.findViewById(R.id.CloudImage_cloudItem)).
                    setImageResource(Utility.getIconResourceForWeatherCondition(Integer.parseInt(weatherItem.getTempId())));
        }
    }

    public void setmUseTodayLayout(boolean mUseTodayLayout) {
        this.mUseTodayLayout = mUseTodayLayout;
    }
}
