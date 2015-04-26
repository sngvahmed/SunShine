package com.sngv.sunshine.weatherService;

import com.sngv.sunshine.domain.LocationItem;

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

    public String getWeatherFromApi(LocationItem weatherItem, int counter){
        FetchWeatherData fetchWeatherData = new FetchWeatherData();
        try {
            return data = fetchWeatherData.execute(getUnitType() , weatherItem.getPATH_LOCATION() , Integer.toString(counter)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "City unfound";
    }

    public String getData(){return data;}

}
