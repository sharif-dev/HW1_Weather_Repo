package com.example.weatherrepo;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class VolleyRequests {
    private final static String token = "pk.eyJ1Ijoic2gzcmxvY2szZCIsImEiOiJjazg3cWt4b2wwMHBiM2VudW53dnh1ZnMyIn0.tS6efj1lfx_LHz-iMord4A";
    private static RequestQueue requestQueue = Volley.newRequestQueue(MyHandler.activity);
    private static Gson gson = new Gson();
    private static Cities cities = null;
    private static Boolean errorOnMap = false;

    public static Cities mapBox(String cityName) {
        String query = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + cityName +
                ".json?access_token=" + token;
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
                MyHandler.makeAToast("That didn't work!");
                errorOnMap = true;
            }
        });
        requestQueue.add(stringRequest);
        while (cities == null) {
            if (errorOnMap) return null;
        }
        return cities;
    }
}