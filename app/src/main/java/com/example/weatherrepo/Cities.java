package com.example.weatherrepo;

import androidx.annotation.NonNull;

public class Cities {
    public static class city{
        String place_name;
        double[] center;

        @NonNull
        @Override
        public String toString() {
            return place_name;
        }
    }
    public city[] features;
}