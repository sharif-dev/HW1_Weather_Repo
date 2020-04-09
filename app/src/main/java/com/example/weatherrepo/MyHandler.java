package com.example.weatherrepo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.example.weatherrepo.DailyListActivity;

import static androidx.core.content.ContextCompat.startActivity;

public class MyHandler extends Handler {
    public static final int SEARCH = 1;
    public static final int GET_WEATHER_REPORT = 2;
    public static final int GO_TO_SECOND_LAYOUT = 3;
    public static MainActivity activity;
    public static double north, east;
    public static Cities.city[] globalCities = new Cities.city[5];
    private static ArrayList<Day> daysList = new ArrayList<>();

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case SEARCH:
                mapBoxHandling();
                break;
            case GET_WEATHER_REPORT:
                getWeatherReport();
                break;
            case GO_TO_SECOND_LAYOUT:
                goToSecondThread();
                break;
        }
    }

    private void goToSecondThread() {
        Log.d("aaaaa", "goToSecondThread: A");
        Intent i = new Intent(activity, DailyListActivity.class);
        i.putExtra("day_info",getDaysList());
        activity.startActivity(i);
        Log.d("aaaaa", "goToSecondThread: B");

    }

    public static ArrayList<Day> getDaysList() {
        return daysList;
    }

    private void getWeatherReport() {
        Boolean isConnected = activity.isConectedToInternet();
        if (!isConnected){
            makeAToast(activity.getString(R.string.no_internet_message));
            return;
        }
        daysList = new ArrayList<>();
        ProgressDialog progressDialog = createProgressDialog(
                activity.getString(R.string.dark_sky_PD_title),
                activity.getString(R.string.loading_message));
        progressDialog.show();
        DarkSky result = VolleyRequests.darkSky(north, east);
        setDaysList(daysList,result);
        progressDialog.dismiss();
        Message msg = Message.obtain();
        msg.what = MyHandler.GO_TO_SECOND_LAYOUT;
        activity.looperThread.handler.sendMessage(msg);

    }

    private void mapBoxHandling() {
        //comment for Auto

//        AutoCompleteTextView city = (AutoCompleteTextView) activity.findViewById(R.id.edit_query_city);
//        String[] cities_string = activity.getResources().getStringArray(R.array.citys);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, cities_string);
//        city.setAdapter(adapter);

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
                //activity.textViews[i].setBackground(activity.getResources().getDrawable(R.drawable.text_style));
            }
        }

        //list view
        /*
          ListView listView = (ListView) activity.findViewById(R.id.listView);
        String[] stringList = new String[cities.features.length];
        String all = " ";
        for (int i = 0; i < cities.features.length; i++) {
            String s = cities.features[i].place_name + "  " + cities.features[i].center[0] + "  " + cities.features[i].center[1]+"\n";
            all += s;
            stringList[i] = s;
        }
        Log.d("salam", "mapBoxHandling: " + s);
        Log.d("salam", "mapBoxHandling: " + all);
        ArrayAdapter adapter11 = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, stringList);
        listView.setAdapter(adapter11);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectItem = (String) parent.getItemAtPosition(position);
                String[] params = selectItem.split(" ");
                // todo add request to dark sky
            }
        });
        */



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
    public void setDaysList(ArrayList<Day> daysList,DarkSky weather) {

        for (int i = 0; i < weather.daily.data.length; i++) {

            Day day = new Day();
            day.setTimezone(weather.getTimezone());
            day.setTime(weather.daily.data[i].getTime());
            day.setIcon(weather.daily.data[i].getIcon());
           // day.setSummary(jsonObject.getString("summary"));
            day.setMaxTemperature(weather.daily.data[i].getTemperatureMax());
            daysList.add(day);
        }
    }


}
