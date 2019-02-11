package com.example.dragonist.homemory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.FamilyMember;

import java.util.ArrayList;
import java.util.List;

public class FamilyMemberAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FamilyMember> familyMembers = new ArrayList<FamilyMember>();

    public FamilyMemberAdapter(Context context, ArrayList<FamilyMember>familyMembers) {
        this.context = context;
        this.familyMembers = familyMembers;
    }

    public void setFamilyMembers(ArrayList<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    @Override
    public int getCount() {
        return familyMembers.size();
    }

    @Override
    public Object getItem(int position) {
        return familyMembers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_family_member, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_appellation = convertView.findViewById(R.id.appellation);
            viewHolder.iv_portrait = convertView.findViewById(R.id.portrait);
            viewHolder.rl_detail = convertView.findViewById(R.id.rl_detail);
            viewHolder.tv_nickName = convertView.findViewById(R.id.tv_nickName);
            viewHolder.tv_birthday = convertView.findViewById(R.id.tv_birthday);
            viewHolder.btn_delete = convertView.findViewById(R.id.btn_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_appellation.setText(familyMembers.get(position).getNickName());
        viewHolder.iv_portrait.setImageBitmap(familyMembers.get(position).getPortrait());
        viewHolder.tv_birthday.setText(familyMembers.get(position).getBirthday());
        viewHolder.tv_nickName.setText(familyMembers.get(position).getNickName());
        if (!familyMembers.get(position).isExpend()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,0);
            viewHolder.rl_detail.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            viewHolder.rl_detail.setLayoutParams(params);
        }
        if (!familyMembers.get(position).isAdministrator()) viewHolder.btn_delete.setVisibility(View.INVISIBLE);
        else viewHolder.btn_delete.setVisibility(View.VISIBLE);
        return convertView;
    }

    class ViewHolder {
        public TextView tv_appellation;
        public ImageView iv_portrait;
        public RelativeLayout rl_detail;
        public TextView tv_nickName;
        public TextView tv_birthday;
        public Button btn_delete;
    }
}
