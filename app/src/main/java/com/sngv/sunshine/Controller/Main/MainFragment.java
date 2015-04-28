package com.sngv.sunshine.Controller.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sngv.sunshine.Controller.Details.DetailActivity;
import com.sngv.sunshine.Controller.Setting.SettingsActivity;
import com.sngv.sunshine.DB.DBController;
import com.sngv.sunshine.DB.domain.LocationItem;
import com.sngv.sunshine.DB.domain.WeatherItem;
import com.sngv.sunshine.R;
import com.sngv.sunshine.Service.JsonParser;
import com.sngv.sunshine.Service.MultiPanelLisnter;
import com.sngv.sunshine.Service.Utility;

import org.json.JSONException;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private DBController dbController;
    private ListView listView;
    private LocationItem locationItem = new LocationItem();
    private SharedPreferences pref;
    private Utility utility;
    private MainCursorAdapter weatherAdapter;
    private WeatherItem today;
    private View view;
    private String POSITION_KEY = "position_key";
    private int position = -1;
    private boolean mUseTodayLayout = true;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        init();
        onClickListnerInit();
        updateWeather();
        if(savedInstanceState != null && savedInstanceState.containsKey(POSITION_KEY)){
            position = savedInstanceState.getInt(POSITION_KEY);
            listView.smoothScrollToPosition(position);
            listView.setSelection(position);
        }
        return view;
    }


    private void updateWeather() {
        updateSetting();
        insertIntoListFromDB();
    }

    public void updateSetting(){
        boolean check = false;
        if(locationItem == null)
            locationItem = new LocationItem(getLocation() , getCounter() , getUnitType());
        else{
            if(!locationItem.getCounter().equals(getCounter())) check = true;
            if(!locationItem.getUnitType().equals(getUnitType()))check = true;
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
        today = new WeatherItem();
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listView = (ListView) view.findViewById(R.id.list_item_forecast);
        dbController = new DBController(getActivity());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(position != -1)
            outState.putInt(POSITION_KEY , position);
        super.onSaveInstanceState(outState);
    }

    public void retrieveFromAPI(){
        JsonParser jsonParser = new JsonParser();
        String counter = locationItem.getCounter();
        String details = locationItem.getWeatherFromApi(locationItem, counter);
        try {
            ArrayList<WeatherItem> weathers = jsonParser.getWeatherDataFromJson(details, Integer.parseInt(counter));
            dbController.deleteAll();
            for(WeatherItem str : weathers){
                dbController.insertIntoWeather(str);
            }
            insertIntoListFromDB();
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "JSON EXCEPTION :: " + e.toString() , Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (Exception e){
            Toast.makeText(getActivity(), "EXCEPTION :: " + e.toString() , Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void insertIntoListFromDB(){
        ((TextView)view.findViewById(R.id.City)).setText(locationItem.getLocation());
        try {
            Cursor c = dbController.getAllWeatherCursor();
            if (!c.moveToFirst()){
                retrieveFromAPI();
                c = dbController.getAllWeatherCursor();
                if(!c.moveToFirst()){
//                    Toast.makeText(MainActivity.this, "Check your Connection" , Toast.LENGTH_LONG).show();
                    return ;
                }
            }
            today.setFromCursor(c);
            weatherAdapter = new MainCursorAdapter(getActivity() , c);
            weatherAdapter.setmUseTodayLayout(mUseTodayLayout);
            listView.setAdapter(weatherAdapter);
        } catch (Exception e){
            //Toast.makeText(MainActivity.this, "date base internal error" , Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    public void onClickListnerInit(){
        final Context con = this.getActivity();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int cPosition, long id) {
                Cursor cursor = weatherAdapter.getCursor();
                if(cursor != null && cursor.moveToPosition(cPosition)){
                    WeatherItem weatherItem = new WeatherItem();
                    weatherItem.setFromCursor(cursor);
                    ((MultiPanelLisnter)getActivity()).onItemSelect(weatherItem);
                }
                position = cPosition;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity() , SettingsActivity.class);
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
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = sharedPref.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default)
        );

        Uri uri = Uri.parse(getString(R.string.location_site)).buildUpon().
                appendQueryParameter("q", location).build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);

        if(intent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivity(intent);
        }
    }

    public void setmUseTodayLayout(boolean mUseTodayLayout) {
        this.mUseTodayLayout = mUseTodayLayout;
        if(weatherAdapter != null){
            weatherAdapter.setmUseTodayLayout(mUseTodayLayout);
        }
    }
}