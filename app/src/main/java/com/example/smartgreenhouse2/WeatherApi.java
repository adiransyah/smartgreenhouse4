package com.example.smartgreenhouse2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherApi {

    HttpURLConnection urlConnect = null;
    BufferedReader bufferedReader = null;
    private final String apiKey="BLRhKP7sm10AXrIdYHIvGSooyF4xjEeb";

    public String Url(String url) {
        // HttpURLConnection urlConnect;
        {
            try {
                URL connect = new URL(url);
                try {
                    urlConnect = (HttpURLConnection) connect.openConnection();
                    urlConnect.connect();

                    InputStream is = urlConnect.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(is));
                    String line = "";
                    StringBuffer buffer = new StringBuffer();

                    while ((line = bufferedReader.readLine()) != null) {
                        buffer.append(line);
                    }
                    return buffer.toString();
                    //System.out.println(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } finally {
                if (urlConnect != null) {
                    urlConnect.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return url;
    }

    public String CurrentConditions(String key) {
        String currentconditions = "http://dataservice.accuweather.com/currentconditions/v1/" + key + "?apikey="+apiKey+"&details=true";
        StringBuffer bufferForecast = null;
        try {
            URL connect = new URL(currentconditions);
            try {
                urlConnect = (HttpURLConnection) connect.openConnection();
                urlConnect.connect();

                InputStream is = urlConnect.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line = "";
                bufferForecast = new StringBuffer();

                while ((line = bufferedReader.readLine()) != null) {
                    bufferForecast.append(line);
                }

                //System.out.println(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            if (urlConnect != null) {
                urlConnect.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bufferForecast.toString();
    }
}
