package com.example.weatherrepo;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyHandler extends Handler {
    public static final int SEARCH = 1;
    public static final int ADD_TO_FRAG = 2;
    public static MainActivity activity;

    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case SEARCH:
                getWeatherReport();
                break;
            case ADD_TO_FRAG:
                addToFrag();
        }
    }

    private void addToFrag() {
        //salam

    }

    private void getWeatherReport() {
        EditText city = activity.findViewById(R.id.edit_query_city);
        String cityName = city.getText().toString();
        city.setText("");
        Boolean isConnected = activity.isConectedToInternet();
        if (!isConnected){
            makeAToast("not connected to internet");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("searching cities");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        String[] a = {"sunday", "monday", "tuesday", "wednsday", "friday", "sat"};
        VolleyRequests.mapBox(cityName);

        progressDialog.dismiss();
    }




    private void makeAToast(String text) {
        Toast toast = Toast.makeText(activity.getApplicationContext(),text, Toast.LENGTH_SHORT);
        toast.show();
    }


}
