package com.example.weatherrepo;

public class DarkSky {
    public static class Daily{
        public static class Situation{
            String summary;
            String icon;
            String timezone;

            public String getSummary() {
                return summary;
            }

            public String getIcon() {
                return icon;
            }

            public String getTimezone() {
                return timezone;
            }

            public double getTemperatureMax() {
                return temperatureMax;
            }

            long sunsetTime;
            String precipType;
            double temperatureMin;
            long temperatureMinTime;
            double temperatureMax;
            long temperatureMaxTime;
            double apparentTemperatureMin;
            long apparentTemperatureMinTime;
            double apparentTemperatureMax;
            long apparentTemperatureMaxTime;

            public String toString(){
                return summary + "   " +temperatureMin;
            }

//        "time":1585251000,
//        "summary":"Mostly cloudy throughout the day.",
//        "icon":"partly-cloudy-day","sunriseTime":1585276500,
//        "sunsetTime":1585321260,
//        "moonPhase":0.1,
//        "precipIntensity":0.0005,
//        "precipIntensityMax":0.0014,
//        "precipIntensityMaxTime":1585325640,
//        "precipProbability":0.23,
//        "precipType":"rain",
//        "temperatureHigh":62.98,
//        "temperatureHighTime":1585305360,
//        "temperatureLow":47.58,
//        "temperatureLowTime":1585360800,
//        "apparentTemperatureHigh":62.48,
//        "apparentTemperatureHighTime":1585305360,
//        "apparentTemperatureLow":48.07,
//        "apparentTemperatureLowTime":1585360800,
//        "dewPoint":48.03,"humidity":0.81,
//        "pressure":1023.3,
//        "windSpeed":3,
//        "windGust":10.19,
//        "windGustTime":1585319520,
//        "windBearing":72,
//        "cloudCover":0.66,
//        "uvIndex":4,
//        "uvIndexTime":1585297200,
//        "visibility":10,
//        "ozone":364.2,
//        "temperatureMin":46.86,
//        "temperatureMinTime":1585274640,
//        "temperatureMax":62.98,
//        "temperatureMaxTime":1585305360,
//        "apparentTemperatureMin":47.35,
//        "apparentTemperatureMinTime":1585274640,
//        "apparentTemperatureMax":62.48,
//        "apparentTemperatureMaxTime":1585305360
        }
        Situation[] data;
    }
    Daily daily;
    public static int getIconId(String icon){
        int iconId = R.mipmap.clear_day;
        if (icon.equals("clear-day")) {
            iconId = R.mipmap.clear_day;
        }
        else if (icon.equals("clear-night")) {
            iconId = R.mipmap.clear_night;
        }
        else if (icon.equals("rain")) {
            iconId = R.mipmap.rain;
        }
        else if (icon.equals("snow")) {
            iconId = R.mipmap.snow;
        }
        else if (icon.equals("sleet")) {
            iconId = R.mipmap.sleet;
        }
        else if (icon.equals("wind")) {
            iconId = R.mipmap.wind;
        }
        else if (icon.equals("fog")) {
            iconId = R.mipmap.fog;
        }
        else if (icon.equals("cloudy")) {
            iconId = R.mipmap.cloudy;
        }
        else if (icon.equals("partly-cloudy-day")) {
            iconId = R.mipmap.partly_cloudy;
        }
        else if (icon.equals("partly-cloudy-night")) {
            iconId = R.mipmap.cloudy_night;
        }


        return iconId;
    }
}
