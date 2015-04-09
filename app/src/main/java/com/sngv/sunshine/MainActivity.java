package com.sngv.sunshine;

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
import com.sngv.sunshine.domain.CloudItem;
import com.sngv.sunshine.domain.WeatherItem;
import com.sngv.sunshine.weatherService.JsonParserWeather;
import com.sngv.sunshine.weatherService.WeatherService;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ArrayAdapter<String> weathers_adapter ;
    private List<String> weathers_list;
    private WeatherAdapter weatherAdapter;
    private List<CloudItem> cloutItem;
    private WeatherService weatherService;
    private String details = null;
    private ListView listView;
    private WeatherItem weatherItem = new WeatherItem("Egypt");
    private JsonParserWeather jsonParserWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        init();
        onClickListnerInit();
    }

    public void init(){

        weatherService = new WeatherService();
        listView = (ListView) findViewById(R.id.list_item_forecast);
        cloutItem = new ArrayList<CloudItem>();
        jsonParserWeather = new JsonParserWeather();

    }

    public void metricWeather(){
        cloutItem.clear();
        details = "Waiting";
        details = weatherService.retriveWeatherWithMetric(weatherItem);
        try {
            String city = "There was error";
            ArrayList<HashMap<String, String>> weatherJsonParse = jsonParserWeather.getWeatherDataFromJson(details, 7);
            for(HashMap<String,String> str : weatherJsonParse){
                city = str.get(WeatherCommon.PARSE_COUNTRY);
                double low = Double.parseDouble(str.get(WeatherCommon.PARSE_LOW));
                double height = Double.parseDouble(str.get(WeatherCommon.PARSE_HIGHT));
                String day = str.get(WeatherCommon.PARSE_DAY);
                String description = str.get(WeatherCommon.PARSE_DESCRIPTION);
                CloudItem cloud = new CloudItem(day , description , height ,low);
                cloutItem.add(cloud);
                weatherAdapter = new WeatherAdapter(this , cloutItem);
                listView.setAdapter(weatherAdapter);
            }
            ((TextView)findViewById(R.id.City)).setText(city);
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, e.toString() , Toast.LENGTH_LONG).show();
        }
    }

    public void imperialWeather(){
        cloutItem.clear();
        details = "Waiting";
        details = weatherService.retriveWeatherWithImperial(weatherItem);
        try {
            String city = "There was Error";
            ArrayList<HashMap<String, String>> weatherJsonParse = jsonParserWeather.getWeatherDataFromJson(details, 7);
            for(HashMap<String,String> str : weatherJsonParse){
                city = str.get(WeatherCommon.PARSE_COUNTRY);
                double low = Double.parseDouble(str.get(WeatherCommon.PARSE_LOW));
                double height = Double.parseDouble(str.get(WeatherCommon.PARSE_HIGHT));
                String day = str.get(WeatherCommon.PARSE_DAY);
                String description = str.get(WeatherCommon.PARSE_DESCRIPTION);
                CloudItem cloud = new CloudItem(day , description , height ,low);
                cloutItem.add(cloud);
                weatherAdapter = new WeatherAdapter(this , cloutItem);
                listView.setAdapter(weatherAdapter);
            }
            ((TextView)findViewById(R.id.City)).setText(city);
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, e.toString() , Toast.LENGTH_LONG).show();
        }

    }

    public void onClickListnerInit(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3){
                Toast.makeText(MainActivity.this,weatherService.getData(), Toast.LENGTH_LONG).show();
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
            return true;
        }else if (id == R.id.action_Imperial){
            imperialWeather();
        }else if (id == R.id.action_Metric){
            metricWeather();
        }
        return super.onOptionsItemSelected(item);
    }


}
