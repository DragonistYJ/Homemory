package com.example.dragonist.homemory.Activity.Mine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.dragonist.homemory.Adapter.FamilyMemberAdapter;
import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.FamilyMember;

import java.util.ArrayList;
import java.util.List;

public class FamilyInformation extends AppCompatActivity {
    private Button bt_return;
    private ListView lv_familyMember;
    private FamilyMemberAdapter familyMemberAdapter;
    private List<FamilyMember> familyMembers = new ArrayList<FamilyMember>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_information);
        set_familyData();
        init();
    }

    private void init() {
        //返回按钮
        bt_return = findViewById(R.id.bt_return);
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //家庭成员列表
        lv_familyMember = findViewById(R.id.lv_familyMember);
        familyMemberAdapter = new FamilyMemberAdapter(this,familyMembers);
        lv_familyMember.setAdapter(familyMemberAdapter);
        //布局里面有按钮会抢焦点，需要去除
        lv_familyMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                familyMembers.get(position).setExpend(!familyMembers.get(position).isExpend());
                familyMemberAdapter = new FamilyMemberAdapter(view.getContext(),familyMembers);
                lv_familyMember.setAdapter(familyMemberAdapter);
                familyMemberAdapter.notifyDataSetChanged();
            }
        });
    }

    private void set_familyData() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bg2);
        FamilyMember father = new FamilyMember(bitmap,"爸爸",3,"大江大海","1999年3月28日");
        FamilyMember mother = new FamilyMember(bitmap,"妈妈",3,"江海","1998年3月28日");
        familyMembers.add(father);
        familyMembers.add(mother);
    }
}
