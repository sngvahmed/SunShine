package com.sngv.sunshine.domain;

/**
 * Created by sngv on 09/04/15.
 */
public class WeatherItem {
    private String City;
    private String postalCode;

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
