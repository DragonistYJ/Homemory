package com.example.dragonist.homemory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dragonist.homemory.R;

public class SearchDate extends BaseAdapter {
    private Context context;
    private int number;

    public SearchDate(Context context, int number) {
        this.context = context;
        this.number = number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int getCount() {
        return number;
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
        SearchDate.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_searchdate, null);
            viewHolder = new SearchDate.ViewHolder();
            viewHolder.item = convertView.findViewById(R.id.item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SearchDate.ViewHolder) convertView.getTag();
        }

        viewHolder.item.setText((number-position)+"");

        return convertView;
    }

    class ViewHolder {
        public TextView item;
    }
}
