package com.example.weatherrepo;

import android.app.ProgressDialog;
import android.widget.EditText;
import android.widget.Toast;

public class Controller {
    private static MainActivity activity;
    public Controller(MainActivity activity) {
        this.activity = activity;
    }

    public static void search() {
        EditText city = activity.findViewById(R.id.edit_query_city);
        String cityName = city.getText().toString();
        city.setText("");
        Boolean isConnected = activity.isConectedToInternet();
        if (!isConnected){
            makeAToast("not connected to internet");
            return;
        }




        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("searching cities");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this){
                    try {
                        wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }
        }).start();
    }

    private static void makeAToast(String text) {
        Toast toast = Toast.makeText(activity.getApplicationContext(),text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
