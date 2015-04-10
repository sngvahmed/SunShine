package com.sngv.sunshine.domain;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by sngv on 09/04/15.
 */
public class WeatherItem {
    private String City;
    private String postalCode;

    public WeatherItem(){
        City = "Egypt";
    }

    public WeatherItem(String city) {
        City = city;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
