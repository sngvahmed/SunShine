package com.sngv.sunshine.weatherService;

import android.text.format.Time;

import com.sngv.sunshine.Common.WeatherUtility;
import com.sngv.sunshine.DB.WeatherDBCommon;
import com.sngv.sunshine.domain.WeatherItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by sngv on 09/04/15.
 */
public class JsonParserWeather {

    private final String OWM_PRESSURE = "pressure";
    private final String OWM_HUMIDITY = "humidity";
    private final String OWM_WIND_SPEED = "speed";
    private final String OWM_DEGREE = "deg";
    private final String OWM_COUNTRY = "country";
    private final String OWM_LIST = "list";
    private final String OWM_WEATHER = "weather";
    private final String OWM_TEMPERATURE = "temp";
    private final String OWM_MAX = "max";
    private final String OWM_MIN = "min";
    private final String OWM_DESCRIPTION = "description";
    private final String OWN_CITY = "city";



    public ArrayList<WeatherItem>  getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {
        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);
        JSONObject city = forecastJson.getJSONObject(OWN_CITY);
        Time dayTime = new Time();
        dayTime.setToNow();

        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

        dayTime = new Time();
        ArrayList<WeatherItem > res = new ArrayList<WeatherItem>();
        for(int i = 0; i < weatherArray.length(); i++) {
            WeatherItem weatherItem = new WeatherItem();
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            long dateTime;
            dateTime = dayTime.setJulianDay(julianStartDay+i);
            weatherItem.setDay(WeatherUtility.getReadableDateString(dateTime));

            weatherItem.setPressure(dayForecast.getString(OWM_PRESSURE));
            weatherItem.setHumidity(dayForecast.getString(OWM_HUMIDITY));
            weatherItem.setWindSpeed(dayForecast.getString(OWM_WIND_SPEED));
            weatherItem.setDegree(dayForecast.getString(OWM_DEGREE));

            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            weatherItem.setDescription(weatherObject.getString(OWM_DESCRIPTION));

            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            weatherItem.setMinGrade(temperatureObject.getString(OWM_MIN));
            weatherItem.setMaxGrade(temperatureObject.getString(OWM_MAX));

            weatherItem.setCountry(city.getString(OWM_COUNTRY));

            res.add(weatherItem);
        }

        return res;

    }
}
