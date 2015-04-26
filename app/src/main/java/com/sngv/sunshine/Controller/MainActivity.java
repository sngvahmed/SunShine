package com.sngv.sunshine.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sngv.sunshine.weatherService.WeatherUtility;
import com.sngv.sunshine.Controller.Details.Details_Activity;
import com.sngv.sunshine.Controller.Setting.SettingsActivity;
import com.sngv.sunshine.DB.DBController;
import com.sngv.sunshine.R;
import com.sngv.sunshine.weatherService.WeatherCursorAdapter;
import com.sngv.sunshine.domain.WeatherItem;
import com.sngv.sunshine.domain.LocationItem;
import com.sngv.sunshine.weatherService.JsonParserWeather;

import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private DBController dbController;
    private ListView listView;
    private LocationItem locationItem = new LocationItem();
    private SharedPreferences pref;
    private WeatherUtility weatherUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        init();
        onClickListnerInit();
        updateWeather();
    }

    private void updateWeather() {
        updateSetting();
        insertIntoListFromDB();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateWeather();
    }

    public void updateSetting(){
        boolean check = false;
        if(locationItem == null)
            locationItem = new LocationItem(getLocation() , getCounter() , getUnitType());
        else{
            if(locationItem.getCounter() != getCounter()) check = true;
            locationItem.setLocation(getLocation());
            locationItem.setCounter(getCounter());
            locationItem.setUnitType(getUnitType());
        }
        if(check)retrieveFromAPI();
    }

    public String getLocation(){
        return pref.getString(getString(R.string.pref_location_key) , getString(R.string.pref_location_default));
    }

    public String getUnitType(){
        return pref.getString( getString(R.string.pref_units_key),getString(R.string.pref_units_metric));
    }

    public String getCounter(){
        return pref.getString(getString(R.string.pref_counter_Key),getString(R.string.pref_counter_deafult));
    }

    public void init(){
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        listView = (ListView) findViewById(R.id.list_item_forecast);
        dbController = new DBController(this);
    }

    public void retrieveFromAPI(){
        JsonParserWeather jsonParserWeather = new JsonParserWeather();
        String counter = locationItem.getCounter();
        String details = locationItem.getWeatherFromApi(locationItem, counter);
        try {
            ArrayList<WeatherItem> weatherJsonParse = jsonParserWeather.getWeatherDataFromJson(details, Integer.parseInt(counter));
            dbController.deleteAll();
            for(WeatherItem str : weatherJsonParse){
                dbController.insertIntoWeather(str);
            }
            insertIntoListFromDB();
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, "JSON EXCEPTION :: " + e.toString() , Toast.LENGTH_LONG).show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertIntoListFromDB(){
        WeatherCursorAdapter weatherAdapter;
        ((TextView)findViewById(R.id.City)).setText(locationItem.getLocation());
        try {
            Cursor c = dbController.getAllWeatherCursor();
            if (!c.moveToFirst()){
                retrieveFromAPI();
                c = dbController.getAllWeatherCursor();
                if(!c.moveToFirst()){
                    Toast.makeText(MainActivity.this, "Check your Connection" , Toast.LENGTH_LONG).show();
                    return ;
                }
            }
            weatherAdapter = new WeatherCursorAdapter(this , c);
            listView.setAdapter(weatherAdapter);
        } catch (Exception e){
            Toast.makeText(MainActivity.this, "date base internal error" , Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    public void onClickListnerInit(){
        final Context con = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final Intent detailsIntent = new Intent(con, Details_Activity.class).putExtra(Intent.EXTRA_TEXT , locationItem.getData());
//                startActivity(detailsIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this , SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_map){
            openPreferredLocationInMap();
        }else if (id == R.id.action_refresh){
            retrieveFromAPI();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String location = sharedPref.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default)
                );

        Uri uri = Uri.parse(getString(R.string.location_site)).buildUpon().
                      appendQueryParameter("q", location).build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }


}
