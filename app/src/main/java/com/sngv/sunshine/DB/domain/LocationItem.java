package com.sngv.sunshine.DB.domain;

import com.sngv.sunshine.Service.FetchData;

import java.util.concurrent.ExecutionException;

/**
 * Created by sngv on 09/04/15.
 */
public class LocationItem {
    private String location;
    private String counter;
    private String unitType;
    private String data;

    public LocationItem(){
        location = "null";
        counter = "null";
        unitType = "null";
        data = "null";
    }

    public LocationItem(String location, String counter, String unitType) {
        this.location = location;
        this.counter = counter;
        this.unitType = unitType;
    }

    public String getLocation() {
        location = location.substring(0,1).toUpperCase() + location.substring(1,location.length());
        return location;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeatherFromApi(LocationItem weatherItem, String counter){
        FetchData fetchData = new FetchData();
        try {
            return data = fetchData.execute(getUnitType() , weatherItem.getLocation() ,counter).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "City unfound";
    }

}
