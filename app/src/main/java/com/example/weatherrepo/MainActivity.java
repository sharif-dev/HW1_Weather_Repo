package com.example.weatherrepo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private LooperThread looperThread = new LooperThread();
    TextView[] textViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        looperThread.start();
        MyHandler.activity = this;
        Button btn = findViewById(R.id.button_city);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchThread();
                //goTo2LayoutThread(49.6, 37.28333);// rasht
            }
        });

        textViews = new TextView[]{findViewById(R.id.city0), findViewById(R.id.city1), findViewById(R.id.city2), findViewById(R.id.city3), findViewById(R.id.city4)};
        for(int i = 0; i < 5; i++) {
            MyHandler.globalCities[i] = new Cities.city();
            MyHandler.globalCities[i].place_name = "";
            MyHandler.globalCities[i].center = new double[]{0, 0};
        }
        for(int i = 0; i < 5; i++) {
            final int finalI = i;
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cities.city c = MyHandler.globalCities[finalI];
                    if(!c.place_name.equals("")){
                        goTo2LayoutThread(c.center[0], c.center[1]);
                    }
                }
            });
        }

    }


    public void searchThread() {
        Message msg = Message.obtain();
        msg.what = MyHandler.SEARCH;
        looperThread.handler.sendMessage(msg);
    }

    public void goTo2LayoutThread(double east, double north) {
        // east = Cities.city.center[0]
        Log.d("salam", "goTo2LayoutThread: " + east + "  " + north);
        MyHandler.north = north;
        MyHandler.east = east;
        Message msg = Message.obtain();
        msg.what = MyHandler.GET_WEATHER_REPORT;
        looperThread.handler.sendMessage(msg);
    }

    public Boolean isConectedToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}