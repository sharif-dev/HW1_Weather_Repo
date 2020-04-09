package com.example.weatherrepo;


import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import com.example.weatherrepo.R;
import com.example.weatherrepo.DayAdapter;
import com.example.weatherrepo.Day;

public class DailyListActivity extends ListActivity {

    ArrayList<Day> daysList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_list);

        if(getIntent().getExtras() != null){
            daysList = MyHandler.getDaysList();
            Log.d("DailyA", daysList.toString());

        }

        DayAdapter adapter = new DayAdapter(this,daysList);
        setListAdapter(adapter);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String message = daysList.get(position).getSummary();
        String temperature = daysList.get(position).getMaxTemperature()+"";
        String dayOfWeek = daysList.get(position).getDayOfWeek();

        String finalS = String.format("On %s it will be %s and today %s",dayOfWeek,temperature,message);
        Toast.makeText(this,finalS,Toast.LENGTH_SHORT).show();
    }
    public void getDaysList(ArrayList<Day> daysList){

    }
}