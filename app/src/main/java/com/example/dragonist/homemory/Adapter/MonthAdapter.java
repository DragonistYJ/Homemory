package com.example.dragonist.homemory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dragonist.homemory.R;

public class MonthAdapter extends BaseAdapter {
    private Context context;

    public MonthAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 13;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_month_select, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_month = convertView.findViewById(R.id.tv_month);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_month.setText(position+"");

        return convertView;
    }

    class ViewHolder {
        public TextView tv_month;
    }
}
