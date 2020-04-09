package com.example.weatherrepo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MyHandler extends Handler {
    public static final int SEARCH = 1;
    public static final int GET_WEATHER_REPORT = 2;
    public static MainActivity activity;
    public static double north, east;
    public static Cities.city[] globalCities = new Cities.city[5];

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case SEARCH:
                mapBoxHandling();
                break;
            case GET_WEATHER_REPORT:
                getWeatherReport();
                break;
        }
    }

    private void getWeatherReport() {
        Boolean isConnected = activity.isConectedToInternet();
        if (!isConnected){
            makeAToast(activity.getString(R.string.no_internet_message));
            return;
        }
        //
        ProgressDialog progressDialog = createProgressDialog(
                activity.getString(R.string.dark_sky_PD_title),
                activity.getString(R.string.loading_message));
        progressDialog.show();

        DarkSky result = VolleyRequests.darkSky(north, east);
        // todo set second layout
        //  go to second layout
        progressDialog.dismiss();
    }

    private void mapBoxHandling() {
        EditText city = activity.findViewById(R.id.edit_query_city);
        final String cityName = city.getText().toString();
        city.setText("");
        Boolean isConnected = activity.isConectedToInternet();
        if (!isConnected){
            makeAToast(activity.getString(R.string.no_internet_message));
            return;
        }
        ProgressDialog progressDialog = createProgressDialog(
                activity.getString(R.string.map_boxing_title),
                activity.getString(R.string.loading_message));
        progressDialog.show();
        Cities cities = VolleyRequests.mapBox(cityName);
        if(cities == null) {
            progressDialog.dismiss();
            return;
        }
        String s = "";
        for (int i = 0; i < cities.features.length; i++) {
            s += cities.features[i].place_name + "  " + cities.features[i].center[0] + "  " + cities.features[i].center[1]+"\n";

        }
        Log.d("salam", "mapBoxHandling: " + s);
        progressDialog.dismiss();
        ArrayList<String> list = new ArrayList<>();
        for(Cities.city i : cities.features) {
            list.add(i.place_name);
        }
        for(int i = 0; i < 5; i++){
            if (cities.features.length-1 < i){
                globalCities[i].place_name = "";
                globalCities[i].center[0] = 0;
                globalCities[i].center[1] = 0;
                activity.textViews[i].setText("");
            } else {
                globalCities[i].place_name = cities.features[i].place_name;
                globalCities[i].center[0] = cities.features[i].center[0];
                globalCities[i].center[1] = cities.features[i].center[1];
                activity.textViews[i].setText(cities.features[i].place_name);
            }
        }







//        ListView l = activity.findViewById(R.id.listView);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1, a);
//        l.setAdapter(adapter);
        //activity.a[0] = "aaaaaaaaaaaa";
        //activity.adapter.notifyDataSetChanged();





    }





    public static void makeAToast(String text) {
        Toast toast = Toast.makeText(activity.getApplicationContext(),text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static ProgressDialog createProgressDialog(String title, String message){
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        return progressDialog;
    }


}
