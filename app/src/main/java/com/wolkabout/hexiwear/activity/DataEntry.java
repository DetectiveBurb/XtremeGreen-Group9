package com.wolkabout.hexiwear.activity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mcjes on 2017-07-18.
 */

public class DataEntry {
    public String timeStamp;
    public String temperature;
    public String humidity;
    public String light;

    public DataEntry(){

    }
    public DataEntry(String date, String temperature, String humidity, String light){
        this.timeStamp =date;
        this.temperature=temperature;
        this.humidity=humidity;
        this.light=light;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("timeStamp",timeStamp);
        result.put("Temperature",temperature);
        result.put("Humidity",humidity);
        result.put("Light",light);

        return result;
    }
}
