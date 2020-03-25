package com.example.weatherrepo;


public class VolleyRequests {
    private final static String token = "pk.eyJ1Ijoic2gzcmxvY2szZCIsImEiOiJjazg3cWt4b2wwMHBiM2VudW53dnh1ZnMyIn0.tS6efj1lfx_LHz-iMord4A";

    public static void mapBox(String cityName) {
        String query = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + cityName +
                ".json?access_token="+token;
        //RequestQueue requestQueue = Volley.newRequestQueue(c);


    }
}
