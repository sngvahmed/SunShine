package com.sngv.sunshine.weatherService;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchWeatherData extends AsyncTask<String , Void , String> {
    private HttpURLConnection urlConnection;
    private BufferedReader bufferedReader;
    private String forecastJsonStr;


    @Override
    protected String doInBackground(String... params) {
        String unit = params[0];
        String City = params[1];
        String counter = params[2];

        try {
            String api_url = WeatherUtility.API_WITH_CITY +City+"&mode=json&units="+unit+"&cnt="+counter;

            URL url = new URL(api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            if(stringBuffer == null){
                System.out.println("error");
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line + "\n");
            }
            forecastJsonStr = stringBuffer.toString();

            return forecastJsonStr;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}