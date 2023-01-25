package com.example.smartgreenhouse2;

import android.widget.TextView;

import java.text.ParseException;

public class WeatherData {
    TextView currentLocation;
    TextView currentPhrase;

    WeatherData(MainActivity activity) throws NullPointerException, ParseException{
        if (activity.Current_TemperatureValue!=null){
            TextView currentTemp = (TextView) activity.findViewById(R.id.current_temp);
            currentTemp.setText(activity.Current_TemperatureValue + "\u00b0");
        }
    }
}
