package com.example.weatherrepo;

import android.os.Handler;
import android.os.Looper;



public class LooperThread extends Thread {
    public Handler handler;


    @Override
    public void run() {
        Looper.prepare();
        handler = new MyHandler();
        Looper.loop();
    }
}
