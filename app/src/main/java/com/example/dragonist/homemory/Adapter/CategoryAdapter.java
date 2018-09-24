package com.example.dragonist.homemory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragonist.homemory.R;

public class CategoryAdapter extends BaseAdapter {
    private String []items = {"宝宝康乐","学生时代","恋爱婚姻","工作成果","家庭历史"};
    private int select_num;
    private Context context;

    public CategoryAdapter(Context context, int select_num) {
        this.context = context;
        this.select_num = select_num;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.content = (TextView) convertView.findViewById(R.id.category);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.content.setText(items[position]);
        if (select_num == position) viewHolder.icon.setImageResource(R.drawable.upload_checked);
        else viewHolder.icon.setImageResource(R.drawable.upload_unchecked);

        return convertView;
    }

    class ViewHolder {
        public TextView content;
        public ImageView icon;
    }
}
