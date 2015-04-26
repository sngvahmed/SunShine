package com.sngv.sunshine.DB;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Android @Udacity_Android
 */
public class DBCommon {
    public static final String PATH_WEATHER = "weather";
    public static final String PATH_LOCATION = "location";
    public static final String TABLE_NAME = "weather";
    public static final String COLUMN_DATETEXT = "date";
    public static final String COLUMN_MIN_TEMP = "min";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_MAX_TEMP = "max";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_PRESSURE = "pressure";
    public static final String COLUMN_WIND_SPEED = "wind";
    public static final String COLUMN_DEGREES = "degrees";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_TEMP_ID = "tempdegree";
    public static final String _id= "id";

    public static final String[] selection = {
            _id,
            COLUMN_DATETEXT,
            COLUMN_MIN_TEMP ,
            COLUMN_MAX_TEMP,
            COLUMN_HUMIDITY ,
            COLUMN_PRESSURE ,
            COLUMN_WIND_SPEED ,
            COLUMN_DESC ,
            COLUMN_COUNTRY ,
            COLUMN_DEGREES ,
            COLUMN_TEMP_ID
    };
}

