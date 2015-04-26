package com.sngv.sunshine.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sngv on 14/04/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "weather.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + WeatherDBCommon.TABLE_NAME + " (" +
                WeatherDBCommon._id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WeatherDBCommon.COLUMN_DATETEXT + " TEXT NOT NULL, " +
                WeatherDBCommon.COLUMN_MIN_TEMP + " TEXT NOT NULL, " +
                WeatherDBCommon.COLUMN_MAX_TEMP + " TEXT NOT NULL, " +
                WeatherDBCommon.COLUMN_HUMIDITY + " TEXT NOT NULL, " +
                WeatherDBCommon.COLUMN_PRESSURE + " TEXT NOT NULL, " +
                WeatherDBCommon.COLUMN_WIND_SPEED + " TEXT NOT NULL, " +
                WeatherDBCommon.COLUMN_DESC + " TEXT NOT NULL, " +
                WeatherDBCommon.COLUMN_COUNTRY + " TEXT NOT NULL, " +
                WeatherDBCommon.COLUMN_DEGREES + " TEXT NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherDBCommon.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
