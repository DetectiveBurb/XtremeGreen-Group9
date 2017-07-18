package com.wolkabout.hexiwear.activity;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by micha on 2017-07-13.
 */

public class Entry implements Serializable{

    SimpleDateFormat sdf;
    private double temperature;
    private double light;
    private double humidity;

    public Entry(SimpleDateFormat sdf, double temperature, double light, double humidity)
    {
        sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.sdf = sdf;
        this.temperature = temperature;
        this.light = light;
        this.humidity = humidity;

    }

    @Exclude
    public Map<String, Object> MapValues(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("temperature", temperature);
        result.put("light", light);
        result.put("humidity", humidity);

        return result;
    }

}
