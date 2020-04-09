package com.example.weatherrepo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by rohan on 3/10/16.
 */
public class Day implements Parcelable{

    private String icon;
    private String summary;
    private String timezone;
    private double maxTemperature;
    private long time;

    public Day() {
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getMaxTemperature() {
        return (int) Math.round(maxTemperature);
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getIconId(){
        return DarkSky.getIconId(getIcon());
    }

    public String getDayOfWeek(){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(getTimezone()));
            Date date = new Date(getTime() * 1000);
            return simpleDateFormat.format(date);
        } catch (Exception e){
            // TODO blahblah
            Log.d("aaaaa", "getDayOfWeek: error");
            return "error in getDayOfWeek";
        }

    }

    @Override
    public String toString() {
        return "Day{" +
                "icon='" + icon + '\'' +
                ", summary='" + summary + '\'' +
                ", timezone='" + timezone + '\'' +
                ", maxTemperature=" + maxTemperature +
                ", time=" + time +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(summary);
        dest.writeString(timezone);
        dest.writeLong(time);
        dest.writeDouble(maxTemperature);
    }

    private Day(Parcel in){
        icon = in.readString();
        summary=in.readString();
        timezone=in.readString();
        time=in.readLong();
        maxTemperature=in.readDouble();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
}