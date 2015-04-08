package com.sngv.sunshine.weatherService;

import android.os.AsyncTask;
import android.util.Log;

import com.sngv.sunshine.Common.WeatherCommon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by sngv on 08/04/15.
 */
public class WeatherService {
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;
    private String forecastJsonStr;
    private String City = "null";
    private String data = "null";

    public String retriveWeatherWithMetric(){
        FetchWeatherData fetchWeatherData = new FetchWeatherData();
        String res = null;
        try {
            return data = fetchWeatherData.execute(WeatherCommon.UnitMetric , City).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "City unfound";
    }

    public String retriveWeatherWithImperial(){
        FetchWeatherData fetchWeatherData = new FetchWeatherData();
        try {
            return data = fetchWeatherData.execute(WeatherCommon.UnitImperial , City).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "City unfound";
    }

    public WeatherService(String city){
        City = city;
    }

    public String getData(){return data;}

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

}
