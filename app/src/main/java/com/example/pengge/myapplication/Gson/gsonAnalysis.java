package com.example.pengge.myapplication.Gson;

import java.io.IOException;
import com.google.gson.Gson;

/**
 * Created by pengge on 16/7/31.
 */
public class gsonAnalysis {

    /**
     * lon : 116.40752
     * lat : 39.90403
     */

    private String lon;
    private String lat;

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
    public static void main(String[] args) throws IOException
    {
        Gson gson = new Gson();
        String data = "{\"lon\":\"116.40752\",\"lat\":\"39.90403\"}";
        //getGson.getClass(data);
        //getGson.fromJson(data.Person.class);
        gsonAnalysis address = gson.fromJson(data, gsonAnalysis.class);
        String lon = address.getLon();
        System.out.println(lon);
    }

}

