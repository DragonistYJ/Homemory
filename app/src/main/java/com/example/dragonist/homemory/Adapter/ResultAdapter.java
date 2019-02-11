package com.example.dragonist.homemory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragonist.homemory.Bean.Archive;
import com.example.dragonist.homemory.R;

import java.util.ArrayList;

public class ResultAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Archive> archives;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setArchives(ArrayList<Archive> archives) {
        this.archives = archives;
    }

    public ResultAdapter(Context context, ArrayList<Archive> archives) {
        this.context = context;
        this.archives = archives;
    }

    @Override
    public int getCount() {
        return archives.size();
    }

    @Override
    public Object getItem(int position) {
        return archives.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_result, null);
            viewHolder = new ViewHolder();
            viewHolder.tvUser = convertView.findViewById(R.id.tvUser);
            viewHolder.tvAuthority = convertView.findViewById(R.id.tvAuthority);
            viewHolder.tvClassification = convertView.findViewById(R.id.tvClassification);
            viewHolder.tvDescription = convertView.findViewById(R.id.tvDescription);
            viewHolder.tvLocation = convertView.findViewById(R.id.tvLocation);
            viewHolder.tvUploadTime = convertView.findViewById(R.id.tvUploadTime);
            viewHolder.ivIcon = convertView.findViewById(R.id.ivIcon);
            viewHolder.ivPortrait = convertView.findViewById(R.id.portrait);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvUser.setText(archives.get(position).getNickName());
        viewHolder.tvAuthority.setText(archives.get(position).getVisibleRange());
        viewHolder.tvClassification.setText(archives.get(position).getClassification());
        viewHolder.tvDescription.setText(archives.get(position).getDescription());
        viewHolder.tvLocation.setText(archives.get(position).getLocation());
        viewHolder.tvUploadTime.setText(archives.get(position).getUploadDate().toString());
        viewHolder.ivIcon.setImageBitmap(archives.get(position).getIcon());
        viewHolder.ivPortrait.setImageBitmap(archives.get(position).getPortrait());

        return convertView;
    }

    class ViewHolder {
        public TextView tvUser;
        public TextView tvUploadTime;
        public TextView tvClassification;
        public TextView tvDescription;
        public TextView tvLocation;
        public TextView tvAuthority;
        public ImageView ivIcon;
        public ImageView ivPortrait;
    }
}
