package com.sngv.sunshine;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sngv.sunshine.Common.WeatherCommon;
import com.sngv.sunshine.domain.CloudItem;
import com.sngv.sunshine.weatherService.WeatherService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ArrayAdapter<String> weathers_adapter ;
    private List<String> weathers_list;
    private WeatherAdapter weatherAdapter;
    private List<CloudItem> cloutItem;
    private WeatherService weatherService;
    private String details = null;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);
        initCustom();
    }

    public void initCustom(){

        weatherService = new WeatherService("Egypt");

        details = weatherService.retriveWeatherWithMetric();
        details = "Ahmed";

        listView = (ListView) findViewById(R.id.list_item_forecast);
        cloutItem = new ArrayList<CloudItem>();
        for(int i = 1 ; i < 10 ; i++){
            CloudItem cloud = new CloudItem("day" + Integer.toString(i) , i , i);
            cloutItem.add(cloud);
        }
        weatherAdapter = new WeatherAdapter(this , cloutItem);
        listView.setAdapter(weatherAdapter);
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {
        weathers_list = new ArrayList<String>();
        for(int i = 0 ; i < 10 ; i++){
//            TextView txt = new TextView(this);
//            txt.setText("today " + Integer.toString(i));
            weathers_list.add("today " + Integer.toString(i));
        }

        weathers_adapter= new ArrayAdapter<String>(this , R.layout.list_item_forecast , R.id.list_item_forecast_textView , weathers_list);
        ListView listView = (ListView) findViewById(R.id.list_item_forecast);
        listView.setAdapter(weathers_adapter);
    }

}
