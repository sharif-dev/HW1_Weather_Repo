package com.example.weatherrepo;

import android.content.Context;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.weatherrepo.R;
import com.example.weatherrepo.Day;

/**
 * Created by rohan on 3/11/16.
 * TODO Understand code
 */
public class DayAdapter extends BaseAdapter {

    Context context;
    ArrayList<Day> daysList;

    public DayAdapter(Context context, ArrayList<Day> daysList) {
        this.context = context;
        this.daysList = daysList;
    }

    @Override
    public int getCount() {
        return daysList.size();
    }

    @Override
    public Object getItem(int position) {
        return daysList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_daily_view, null);
            viewHolder = new ViewHolder();
            viewHolder.imageViewIcon = (ImageView)convertView.findViewById(R.id.imageViewIcon);
            viewHolder.textViewDay = (TextView)convertView.findViewById(R.id.textViewDay);
            viewHolder.textViewTemperature = (TextView)convertView.findViewById(R.id.textViewTemperature);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Day day = daysList.get(position);
        viewHolder.imageViewIcon.setImageResource(day.getIconId());
        viewHolder.textViewTemperature.setText(day.getMaxTemperature());
        if(position == 0){
            viewHolder.textViewDay.setText("Today");
        }else {
            viewHolder.textViewDay.setText(day.getDayOfWeek());
        }

        return convertView;
    }

    public static class ViewHolder{
        ImageView imageViewIcon;
        TextView textViewTemperature;
        TextView textViewDay;
    }
}