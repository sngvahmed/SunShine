package com.sngv.sunshine.Service;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.sngv.sunshine.Controller.Main.MainFragment;
import com.sngv.sunshine.DB.DBController;
import com.sngv.sunshine.DB.domain.WeatherItem;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sngv on 29/04/15.
 */
public class WeatherService extends IntentService {
    public static String UNIT_EXTRA = "unit_extra";
    public static String CITY_EXTRA = "city_extra";
    public static String COUNTER_EXTRA = "counter_extra";
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;
    private String forecastJsonStr;
    private String API_WITH_CITY = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private String API_KEY = "APPID=57b7e7166b2b88c67403396642c01917";

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String unit = intent.getStringExtra(UNIT_EXTRA);
        String City = intent.getStringExtra(CITY_EXTRA);
        String counter = intent.getStringExtra(COUNTER_EXTRA);
        try {
            String api_url = API_WITH_CITY + City + "&mode=json&units=" + unit + "&cnt=" + counter + "&" + API_KEY;

            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            forecastJsonStr = stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JsonParser jsonParser = new JsonParser();
            try {
                ArrayList<WeatherItem> weathers = jsonParser.getWeatherDataFromJson(forecastJsonStr, Integer.parseInt(counter));
                DBController dbController = new DBController(this);
                dbController.deleteAll();
                for (WeatherItem str : weathers) {
                    dbController.insertIntoWeather(str);
                }
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MainFragment.WeatherReciver.PROCESS_RESPONSE);
                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                sendBroadcast(broadcastIntent);
            } catch (JSONException e) {
                Toast.makeText(this, "JSON EXCEPTION :: " + e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (Exception e) {
                Toast.makeText(this, "EXCEPTION :: " + e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}
