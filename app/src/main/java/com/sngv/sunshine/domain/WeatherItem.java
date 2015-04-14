package com.sngv.sunshine.domain;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by sngv on 08/04/15.
 */
public class WeatherItem {
    private String day;
    private String minGrade;
    private String maxGrade;
    private ImageView cloudImage;
    private String description;
    private String country;
    private String columnPressure;
    private String windSpeed;
    private String degree;

    public String getColumnPressure() {
        return columnPressure;
    }

    public void setColumnPressure(String columnPressure) {
        this.columnPressure = columnPressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public WeatherItem(String Day, String description, Long MaxGrade, Long MinGrade){
        day = Day;
        minGrade = Long.toString(MinGrade);
        maxGrade = Long.toString(MaxGrade);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(String minGrade) {
        this.minGrade = minGrade;
    }

    public String getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(String maxGrade) {
        this.maxGrade = maxGrade;
    }

    public ImageView getCloudImage() {
        return cloudImage;
    }

    public void setCloudImage(ImageView cloudImage) {
        this.cloudImage = cloudImage;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
