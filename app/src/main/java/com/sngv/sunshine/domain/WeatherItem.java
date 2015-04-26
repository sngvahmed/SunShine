package com.sngv.sunshine.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.ImageView;

import com.sngv.sunshine.DB.WeatherDBCommon;

/**
 * Created by sngv on 08/04/15.
 */
public class WeatherItem {
    private Long id;
    private String day = "";
    private String minGrade = "";
    private String maxGrade = "";
    private ImageView cloudImage = null;
    private String description = "";
    private String country = "";
    private String pressure = "";
    private String windSpeed = "";
    private String degree = "";
    private String humidity;

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
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
    public WeatherItem (){}

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

    public ContentValues getContentValue(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(WeatherDBCommon.COLUMN_DATETEXT, getDay());
        contentValues.put(WeatherDBCommon.COLUMN_MIN_TEMP, getMinGrade());
        contentValues.put(WeatherDBCommon.COLUMN_MAX_TEMP, getMaxGrade());
        contentValues.put(WeatherDBCommon.COLUMN_DESC, getDescription());
        contentValues.put(WeatherDBCommon.COLUMN_COUNTRY, getCountry());
        contentValues.put(WeatherDBCommon.COLUMN_HUMIDITY, getHumidity());
        contentValues.put(WeatherDBCommon.COLUMN_PRESSURE, getPressure());
        contentValues.put(WeatherDBCommon.COLUMN_WIND_SPEED, getWindSpeed());
        contentValues.put(WeatherDBCommon.COLUMN_DEGREES, getDegree());
        return contentValues;
    }

    public void setFromCursor(Cursor cursor) {
        setId(cursor.getLong(0));
        setDay(cursor.getString(1));
        setMinGrade(cursor.getString(2));
        setMaxGrade(cursor.getString(3));
        setDescription(cursor.getString(4));
        setCountry(cursor.getString(5));
        setHumidity(cursor.getString(6));
        setPressure(cursor.getString(7));
        setWindSpeed(cursor.getString(8));
        setDegree(cursor.getString(9));
    }

    public void printThem() {
        System.out.println(" *******************Item********************** ");
        System.out.println("Item :: id :: " + getId());
        System.out.println("Item :: Day ::" + getDay());
        System.out.println("Item :: MinGrade :: " + getMinGrade());
        System.out.println("Item :: MaxGrade :: " + getMaxGrade());
        System.out.println("Item :: Desc :: " + getDescription());
        System.out.println("Item :: Country :: " + getCountry());
        System.out.println("Item :: Humidinty :: " + getHumidity());
        System.out.println("Item :: Pressure :: " + getPressure());
        System.out.println("Item :: WindSpeed :: " + getWindSpeed());
        System.out.println("Item :: Degree :: " + getDegree());
        System.out.println(" ******************Item*********************** ");
    }
}
