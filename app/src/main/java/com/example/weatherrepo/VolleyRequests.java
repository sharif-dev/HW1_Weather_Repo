package com.example.weatherrepo;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class VolleyRequests {
    private final static String token = MyHandler.activity.getString(R.string.mapBox_token);
    private static final String mapBoxUrl = MyHandler.activity.getString(R.string.mapBox_url);
    public static final String mapBoxJson = MyHandler.activity.getString(R.string.mapBox_json);
    public static final String darkSkyUrl = MyHandler.activity.getString(R.string.darkSky_url);
    private static RequestQueue requestQueue = Volley.newRequestQueue(MyHandler.activity);
    private static Gson gson = new Gson();
    private static Cities cities = null;
    private static Boolean errorOnMap = false;
    private static DarkSky weather = null;
    private static Boolean errorOnSkyMap = false;


    public static Cities mapBox(String cityName) {
        String query = mapBoxUrl + cityName + mapBoxJson + token;
        cities = null;
        errorOnMap = false;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, query,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cities = gson.fromJson(response, Cities.class);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyHandler.makeAToast(MyHandler.activity.getString(R.string.skyMap_error));
                errorOnMap = true;
            }
        });
        requestQueue.add(stringRequest);
        while (cities == null) {
            if (errorOnMap) return null;
        }
        return cities;
    }

    public static DarkSky darkSky(double north, double east){
        String query = darkSkyUrl +north+","+east;
        weather = null;
        errorOnSkyMap = false;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                weather = gson.fromJson(response, DarkSky.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyHandler.makeAToast(MyHandler.activity.getString(R.string.darkSky_error));
                errorOnSkyMap = true;
            }
        });
        requestQueue.add(stringRequest);
        while (weather == null){
            if(errorOnSkyMap){
                return null;
            }
        }
        return weather;
    }
}