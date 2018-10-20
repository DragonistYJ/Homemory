package com.example.dragonist.homemory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Bean.MemoryBean;

import java.util.List;

public class MemoryAdapter extends BaseAdapter {
    private Context context;
    private List<MemoryBean> memories;

    public MemoryAdapter(Context context, List<MemoryBean> memories) {
        this.context = context;
        this.memories = memories;
    }

    @Override
    public int getCount() {
        return memories.size();
    }

    @Override
    public Object getItem(int position) {
        return memories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_memory, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_appellation = convertView.findViewById(R.id.tv_appellation);
            viewHolder.iv_portrait = convertView.findViewById(R.id.iv_portrait);
            viewHolder.iv_thumbnail = convertView.findViewById(R.id.iv_thumbnail);
            viewHolder.tv_uploadTime = convertView.findViewById(R.id.tv_uploadTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_appellation.setText(memories.get(position).getAppellation());
        viewHolder.iv_portrait.setImageBitmap(memories.get(position).getPortrait());
        viewHolder.iv_thumbnail.setImageBitmap(memories.get(position).getThumbnail());
        viewHolder.tv_uploadTime.setText(memories.get(position).getUploadTime()+"");

        return convertView;
    }

    class ViewHolder {
        public ImageView iv_thumbnail;
        public TextView tv_appellation;
        public ImageView iv_portrait;
        public TextView tv_uploadTime;
    }
}
