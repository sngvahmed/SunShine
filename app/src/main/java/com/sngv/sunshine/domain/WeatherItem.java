package com.sngv.sunshine.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.ImageView;

import com.sngv.sunshine.DB.DBCommon;

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
        contentValues.put(DBCommon.COLUMN_DATETEXT, getDay());
        contentValues.put(DBCommon.COLUMN_MIN_TEMP, getMinGrade());
        contentValues.put(DBCommon.COLUMN_MAX_TEMP, getMaxGrade());
        contentValues.put(DBCommon.COLUMN_DESC, getDescription());
        contentValues.put(DBCommon.COLUMN_COUNTRY, getCountry());
        contentValues.put(DBCommon.COLUMN_HUMIDITY, getHumidity());
        contentValues.put(DBCommon.COLUMN_PRESSURE, getPressure());
        contentValues.put(DBCommon.COLUMN_WIND_SPEED, getWindSpeed());
        contentValues.put(DBCommon.COLUMN_DEGREES, getDegree());
        return contentValues;
    }

    public void setFromCursor(Cursor cursor) {
        setId(cursor.getLong(cursor.getColumnIndex(DBCommon._id)));
        setDay(cursor.getString(cursor.getColumnIndex(DBCommon.COLUMN_DATETEXT)));
        setMinGrade(cursor.getString(cursor.getColumnIndex(DBCommon.COLUMN_MIN_TEMP)));
        setMaxGrade(cursor.getString(cursor.getColumnIndex(DBCommon.COLUMN_MAX_TEMP)));
        setHumidity(cursor.getString(cursor.getColumnIndex(DBCommon.COLUMN_HUMIDITY)));
        setPressure(cursor.getString(cursor.getColumnIndex(DBCommon.COLUMN_PRESSURE)));
        setWindSpeed(cursor.getString(cursor.getColumnIndex(DBCommon.COLUMN_WIND_SPEED)));;
        setDescription(cursor.getString(cursor.getColumnIndex(DBCommon.COLUMN_DESC)));
        setCountry(cursor.getString(cursor.getColumnIndex(DBCommon.COLUMN_COUNTRY)));
        setDegree(cursor.getString(cursor.getColumnIndex(DBCommon.COLUMN_DEGREES)));
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
