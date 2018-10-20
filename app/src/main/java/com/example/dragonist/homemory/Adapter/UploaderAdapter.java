package com.example.dragonist.homemory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragonist.homemory.R;

import java.util.ArrayList;

public class UploaderAdapter extends BaseAdapter {
    private ArrayList<String> uploaders;
    private ArrayList<Integer> select;
    private Context context;

    public UploaderAdapter(ArrayList<String> uploaders, ArrayList<Integer> select, Context context) {
        this.uploaders = uploaders;
        this.select = select;
        this.context = context;
    }

    public void setUploaders(ArrayList<String> uploaders) {
        this.uploaders = uploaders;
    }

    public void setSelect(ArrayList<Integer> select) {
        this.select = select;
    }

    @Override
    public int getCount() {
        return uploaders.size();
    }

    @Override
    public Object getItem(int position) {
        return uploaders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_uploader, null);
            viewHolder = new ViewHolder();
            viewHolder.ivCheck = (ImageView) convertView.findViewById(R.id.ivCheck);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(uploaders.get(position));
        if (select.contains(position)) viewHolder.ivCheck.setImageResource(R.drawable.upload_checked);
        else viewHolder.ivCheck.setImageResource(R.drawable.upload_unchecked);

        return convertView;
    }

    class ViewHolder {
        public TextView tvName;
        public ImageView ivCheck;
    }
}
