package com.example.weatherrepo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private LooperThread looperThread = new LooperThread();

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
                //Log.v("EditText", editText.getText().toString());
                searchThread();
            }
        });

    }

    public void searchThread() {
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                AutoCompleteTextView city = findViewById(R.id.edit_query_city);
//                String[] citys = getResources().getStringArray(R.array.citys);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, citys);
//                city.setAdapter(adapter);
//                String cityName = city.getText().toString();
//                city.setText("");
//                Boolean isConnected = isConectedToInternet();
//                if (!isConnected){
//                    makeAToast("not connected to internet");
//                    return;
//                }
//                ProgressDialog progressDialog = new ProgressDialog();
//                progressDialog.setTitle("searching cities");
//                progressDialog.setMessage("Loading...");
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                progressDialog.setCancelable(false);
//                progressDialog.show();
//                SystemClock.sleep(2000);
//                progressDialog.dismiss();
//
//            }
//        };
//        looperThread.handler.post(runnable);

        Message msg = Message.obtain();
        msg.what = MyHandler.SEARCH;
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