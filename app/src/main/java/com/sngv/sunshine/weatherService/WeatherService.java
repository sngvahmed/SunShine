package com.sngv.sunshine.weatherService;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.sngv.sunshine.Common.WeatherCommon;
import com.sngv.sunshine.R;
import com.sngv.sunshine.domain.WeatherItem;

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
    private String data = "null";
    private String unitType;

    public String getUnitType(){
        return unitType;
    }

    public WeatherService(String unitType){
        this.unitType = unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getWeatherFromApi(WeatherItem weatherItem){
        FetchWeatherData fetchWeatherData = new FetchWeatherData();
        try {
            return data = fetchWeatherData.execute(getUnitType() , weatherItem.getCity()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "City unfound";
    }

    public String getData(){return data;}

}
