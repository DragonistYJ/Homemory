package com.example.dragonist.homemory.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.Date;

public class DateAdapter extends BaseAdapter {
    private int[] days = new int[42];
    private Context context;
    private int year;
    private int month;

    public DateAdapter(Context context, int[][] days, int year, int month) {
        this.context = context;
        this.year = year;
        this.month = month;

        int dayNum = 0;
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                this.days[dayNum] = days[i][j];
                dayNum++;
            }
        }
    }

    @Override
    public int getCount() {
        return days.length;
    }

    @Override
    public Object getItem(int position) {
        return days[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_date, null);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position< 7 && days[position] > 20) {
            viewHolder.date.setTextColor(Color.rgb(204, 204, 204));//将上个月的和下个月的设置为灰色
        } else if (position > 20 && days[position] < 15) {
            viewHolder.date.setTextColor(Color.rgb(204, 204, 204));
        }
        if (year== Date.getYear() && month==Date.getMonth() && days[position]==Date.getDay()) {
            viewHolder.date.setBackgroundResource(R.drawable.calendar_today);
        }
        viewHolder.date.setText(days[position] + "");

        return convertView;
    }

    class ViewHolder {
        public TextView date;
    }
}
