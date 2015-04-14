package com.sngv.sunshine.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sngv.sunshine.Common.WeatherCommon;
import com.sngv.sunshine.Controller.Details.Details_Activity;
import com.sngv.sunshine.Controller.Setting.SettingsActivity;
import com.sngv.sunshine.R;
import com.sngv.sunshine.weatherService.WeatherAdapter;
import com.sngv.sunshine.domain.WeatherItem;
import com.sngv.sunshine.domain.LocationItem;
import com.sngv.sunshine.weatherService.JsonParserWeather;
import com.sngv.sunshine.weatherService.WeatherService;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ArrayAdapter<String> weathers_adapter ;
    private WeatherAdapter weatherAdapter;
    private List<WeatherItem> cloutItem;
    private WeatherService weatherService;
    private String details = null;
    private ListView listView;
    private LocationItem weatherItem = new LocationItem();
    private JsonParserWeather jsonParserWeather;
    private SharedPreferences pref;

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
        updateListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateWeather();
    }

    public void updateSetting(){
        String location = pref.getString(getString(R.string.pref_location_key) , getString(R.string.pref_location_default));
        if(weatherItem == null)
            weatherItem = new LocationItem(location);
        else
            weatherItem.setPATH_LOCATION(location);
        weatherService.setUnitType(getUnitType());
    }

    public String getUnitType(){
        return pref.getString( getString(R.string.pref_units_key),
                getString(R.string.pref_units_metric));
    }

    public void init(){
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        weatherService = new WeatherService(getUnitType());
        listView = (ListView) findViewById(R.id.list_item_forecast);
        cloutItem = new ArrayList<WeatherItem>();
        jsonParserWeather = new JsonParserWeather();

    }



    public void updateListView(){
        cloutItem.clear();
        details = "Waiting";
        details = weatherService.getWeatherFromApi(weatherItem);
        try {
            String city = "There was error";
            ArrayList<HashMap<String, String>> weatherJsonParse = jsonParserWeather.getWeatherDataFromJson(details, 7);
            for(HashMap<String,String> str : weatherJsonParse){
                city = str.get(WeatherCommon.PARSE_COUNTRY);
                Long low = Long.parseLong(str.get(WeatherCommon.PARSE_LOW));
                Long height = Long.parseLong(str.get(WeatherCommon.PARSE_HIGHT));
                String day = str.get(WeatherCommon.PARSE_DAY);
                String description = str.get(WeatherCommon.PARSE_DESCRIPTION);
                WeatherItem cloud = new WeatherItem(day , description , height ,low);
                cloutItem.add(cloud);
                weatherAdapter = new WeatherAdapter(this , cloutItem);
                listView.setAdapter(weatherAdapter);
            }
            ((TextView)findViewById(R.id.City)).setText(city);
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, e.toString() , Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(MainActivity.this, e.toString() , Toast.LENGTH_LONG).show();
        }
    }

    public void onClickListnerInit(){
        final Context con = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent detailsIntent = new Intent(con, Details_Activity.class).putExtra(Intent.EXTRA_TEXT , weatherService.getData());
                startActivity(detailsIntent);
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
