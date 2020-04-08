package com.example.weatherrepo;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MyHandler extends Handler {
    public static final int SEARCH = 1;
    public static final int GET_WEATHER_REPORT = 2;
    public static MainActivity activity;
    public static double north, east;

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

//        String s = "";
//        for(DarkSky.Daily.Situation x:result.daily.data){
//            s += x.toString() + "\n";
//        }
//        Log.d("salam", "getWeatherReport: "+ s);
        // todo set second layout 
        //  go to second layout
        progressDialog.dismiss();
    }

    private void mapBoxHandling() {
//        EditText city = activity.findViewById(R.id.edit_query_city);
//        String cityName = city.getText().toString();
        /*AutoCompleteTextView city = activity.findViewById(R.id.edit_query_city);
        String[] cities_string = activity.getResources().getStringArray(R.array.citys);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1, cities_string);
        city.setAdapter(adapter);*/
        EditText city = activity.findViewById(R.id.edit_query_city);
        final String cityName = city.getText().toString();
        city.setText("");
        Boolean isConnected = activity.isConectedToInternet();
        if (!isConnected){
            makeAToast(activity.getString(R.string.no_internet_message));
            return;
        }
        /*
        Button btn = activity.findViewById(R.id.button_city);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = createProgressDialog(
                        activity.getString(R.string.map_boxing_title),
                        activity.getString(R.string.loading_message));
                progressDialog.show();
                Cities cities = VolleyRequests.mapBox(cityName);
                if(cities == null) {
                    progressDialog.dismiss();
                    return;
                }

                ListView listView = (ListView) activity.findViewById(R.id.listView);
                ArrayList<String> stringArrayList;
                stringArrayList = new ArrayList<>();
                String[] stringList = new String[cities.features.length];
                int listViewLen = 0;
                for (int i = 0; i < cities.features.length; i++) {
                    String s = cities.features[i].place_name + "  " + cities.features[i].center[0] + "  " + cities.features[i].center[1];
                    stringArrayList.add(s);
                    stringList[i] = s;
                }

                ArrayAdapter adapter1 = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1, stringList);
                listView.setAdapter(adapter1);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectItem = (String) parent.getItemAtPosition(position);
                        String[] params = selectItem.split(" ");
                        double east = 0, north = 0;// todo initialize east and north // east = Cities.city.center[0]
                        activity.goTo2LayoutThread(east, north);
                    }
                });
//        String s = "";
//        for (int i = 0; i < 5; i++) {
//            s += cities.features[i].place_name + "  " + cities.features[i].center[0] + "  " + cities.features[i].center[1]+"\n";
//
//        }
//        Log.d("salam", "mapBoxHandling: " + s);


                progressDialog.dismiss();

            }
        });
*/
        ProgressDialog progressDialog = createProgressDialog(
                activity.getString(R.string.map_boxing_title),
                activity.getString(R.string.loading_message));
        progressDialog.show();
        Cities cities = VolleyRequests.mapBox(cityName);
        if(cities == null) {
            progressDialog.dismiss();
            return;
        }
        /*
        ListView listView = (ListView) activity.findViewById(R.id.listView);
        ArrayList<String> stringArrayList;
        stringArrayList = new ArrayList<>();
        String[] stringList = new String[cities.features.length];
        int listViewLen = 0;
        for (int i = 0; i < cities.features.length; i++) {
            String s = cities.features[i].place_name + "  " + cities.features[i].center[0] + "  " + cities.features[i].center[1];
            stringArrayList.add(s);
            stringList[i] = s;
        }

        ArrayAdapter adapter1 = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_list_item_1, stringList);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectItem = (String) parent.getItemAtPosition(position);
                String[] params = selectItem.split(" ");
                double east = 0, north = 0;// todo initialize east and north // east = Cities.city.center[0]
                activity.goTo2LayoutThread(east, north);
            }
        });*/
        String s = "";
        for (int i = 0; i < cities.features.length; i++) {
            s += cities.features[i].place_name + "  " + cities.features[i].center[0] + "  " + cities.features[i].center[1]+"\n";

        }
        Log.d("salam", "mapBoxHandling: " + s);


        progressDialog.dismiss();
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
