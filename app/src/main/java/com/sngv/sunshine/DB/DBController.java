package com.sngv.sunshine.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sngv.sunshine.DB.domain.WeatherItem;

import java.util.ArrayList;

/**
 * Created by sngv on 26/04/15.
 */
public class DBController {
    DBHelper dbHelper;
    Context context;

    public DBController(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public void insertIntoWeather(WeatherItem weatherItem){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = weatherItem.getContentValue();
        Long id = sqLiteDatabase.insertOrThrow(DBCommon.TABLE_NAME , null , contentValues);
        weatherItem.setId(id);
        sqLiteDatabase.close();
    }

    public ArrayList<WeatherItem> getAllWeatherItem(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(DBCommon.TABLE_NAME , DBCommon.selection , null , null , null,null,null);
        ArrayList<WeatherItem> weathers = new ArrayList<WeatherItem>();
        if(cursor.moveToFirst()){
            do{
                WeatherItem weatherItem = new WeatherItem();
                weatherItem.setFromCursor(cursor);
                weathers.add(weatherItem);
            }while (cursor.moveToNext());
        }
        return weathers;
    }

    public Cursor getAllWeatherCursor(){
       SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
       Cursor cur =  sqLiteDatabase.rawQuery("select rowid _id,* from " + DBCommon.TABLE_NAME, null);
       return cur;
    }

    public void deleteAll() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ DBCommon.TABLE_NAME);
    }
}
