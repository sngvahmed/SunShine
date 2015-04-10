package com.sngv.sunshine.weatherService;

import android.text.format.Time;
import android.util.Log;

import com.sngv.sunshine.Common.WeatherCommon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sngv on 09/04/15.
 */
public class JsonParserWeather {

    final String OWM_LIST = "list";
    final String OWM_WEATHER = "weather";
    final String OWM_TEMPERATURE = "temp";
    final String OWM_MAX = "max";
    final String OWM_MIN = "min";
    final String OWM_DESCRIPTION = "main";
    final String OWN_CITY = "city";

    private String getReadableDateString(long time){
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
        return shortenedDateFormat.format(time);
    }

    private String formatHighLows(double high, double low) {
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    public ArrayList<HashMap<String,String> >  getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);
        JSONObject city = forecastJson.getJSONObject(OWN_CITY);
        Time dayTime = new Time();
        dayTime.setToNow();

        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

        dayTime = new Time();
        ArrayList<HashMap<String,String> > res = new ArrayList<HashMap<String,String> >();
        for(int i = 0; i < weatherArray.length(); i++) {
            HashMap<String,String> tmp = new HashMap<String,String>();
            JSONObject dayForecast = weatherArray.getJSONObject(i);
            long dateTime;


            dateTime = dayTime.setJulianDay(julianStartDay+i);
            tmp.put(WeatherCommon.PARSE_DAY , getReadableDateString(dateTime));
            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            tmp.put(WeatherCommon.PARSE_DESCRIPTION,weatherObject.getString(OWM_DESCRIPTION));
            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);

            long roundedHigh = Math.round(temperatureObject.getDouble(OWM_MAX));
            long roundedLow = Math.round(temperatureObject.getDouble(OWM_MIN));
            tmp.put(WeatherCommon.PARSE_HIGHT,Double.toString(roundedHigh));
            tmp.put(WeatherCommon.PARSE_LOW,Double.toString(roundedLow));

            tmp.put(WeatherCommon.PARSE_COUNTRY , city.getString("country"));
            res.add(tmp);
        }

        return res;

    }
}
