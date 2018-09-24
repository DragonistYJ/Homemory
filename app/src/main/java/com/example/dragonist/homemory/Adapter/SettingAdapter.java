package com.example.dragonist.homemory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragonist.homemory.R;

public class SettingAdapter extends BaseAdapter {
    private String []settings;
    private Context context;

    public SettingAdapter(Context context, String []settings) {
        this.context = context;
        this.settings = settings;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_setting, null);
            viewHolder = new ViewHolder();
            viewHolder.expend = (ImageView) convertView.findViewById(R.id.expend);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(settings[position]);

        return convertView;
    }

    class ViewHolder {
        public TextView title;
        public ImageView expend;
    }
}
