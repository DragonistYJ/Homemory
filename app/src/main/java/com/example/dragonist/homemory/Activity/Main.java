package com.example.dragonist.homemory.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dragonist.homemory.Activity.Mine.FamilyInformation;
import com.example.dragonist.homemory.Activity.Mine.Setting;
import com.example.dragonist.homemory.Fragment.Calendar;
import com.example.dragonist.homemory.Fragment.Family_Stroll;
import com.example.dragonist.homemory.Fragment.Mine;
import com.example.dragonist.homemory.Fragment.Search;
import com.example.dragonist.homemory.Fragment.Time_Machine;
import com.example.dragonist.homemory.Activity.Upload.Upload;
import com.example.dragonist.homemory.R;

public class Main extends AppCompatActivity {
    private ImageView iv_family_stroll;
    private ImageView iv_search;
    private ImageView iv_calendar;
    private ImageView iv_time_machine;
    private ImageView iv_mine;
    private ImageView iv_upload;
    private Fragment fm_family_stroll;
    private Fragment fm_search;
    private Fragment fm_calendar;
    private Fragment fm_time_machine;
    private Fragment fm_mine;
    private Button bt_setting;
    private Button bt_familyInformation;
    private int selet_num;
    private AlertDialog dialog = null;

    @Override
    protected void onPause() {
        super.onPause();
        if (dialog!= null) dialog.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        iv_family_stroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_selected(1);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(fm_time_machine).hide(fm_search).hide(fm_mine).hide(fm_calendar).show(fm_family_stroll).commit();
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_selected(2);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(fm_time_machine).hide(fm_family_stroll).hide(fm_mine).hide(fm_calendar).show(fm_search).commit();
            }
        });

        iv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_selected(3);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(fm_time_machine).hide(fm_family_stroll).hide(fm_mine).hide(fm_search).show(fm_calendar).commit();
            }
        });

        iv_time_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_selected(4);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(fm_search).hide(fm_family_stroll).hide(fm_mine).hide(fm_calendar).show(fm_time_machine).commit();
            }
        });

        iv_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_selected(5);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(fm_time_machine).hide(fm_family_stroll).hide(fm_search).hide(fm_calendar).show(fm_mine).commit();
            }
        });

        iv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View layout = getLayoutInflater().inflate(R.layout.dialog_uploadmethod, null);
        final Intent intent = new Intent(Main.this, Upload.class);
        final Bundle bundle = new Bundle();

        ImageView iv_local = layout.findViewById(R.id.local);
        iv_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("method","local");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ImageView iv_realtime = layout.findViewById(R.id.real_time);
        iv_realtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("method","realtime");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ImageView iv_medio = layout.findViewById(R.id.medio);
        iv_medio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("method","realtime");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        builder.setView(layout);
        dialog = builder.show();
    }

    private void set_selected(int position){
        switch (selet_num){
            case 1:
                iv_family_stroll.setImageResource(R.drawable.main_home);
                break;
            case 2:
                iv_search.setImageResource(R.drawable.main_search);
                break;
            case 3:
                iv_calendar.setImageResource(R.drawable.main_calendar);
                break;
            case 4:
                iv_time_machine.setImageResource(R.drawable.main_time_machine);
                break;
            case 5:
                iv_mine.setImageResource(R.drawable.main_mine);
                break;
        }
        switch (position){
            case 1:
                iv_family_stroll.setImageResource(R.drawable.main_home_selected);
                selet_num = 1;
                break;
            case 2:
                iv_search.setImageResource(R.drawable.main_search_selected);
                selet_num = 2;
                break;
            case 3:
                iv_calendar.setImageResource(R.drawable.main_calendar_selected);
                selet_num = 3;
                break;
            case 4:
                iv_time_machine.setImageResource(R.drawable.main_time_machine_selected);
                selet_num = 4;
                break;
            case 5:
                iv_mine.setImageResource(R.drawable.main_mine_selected);
                selet_num = 5;
                break;
        }
    }

    private void init(){
        iv_family_stroll = findViewById(R.id.family_stroll);
        iv_search = findViewById(R.id.search);
        iv_calendar = findViewById(R.id.calendar);
        iv_time_machine = findViewById(R.id.time_machine);
        iv_mine = findViewById(R.id.mine);
        iv_upload = findViewById(R.id.upload);
        fm_family_stroll = new Family_Stroll();
        fm_calendar = new Calendar();
        fm_search = new Search();
        fm_time_machine = new Time_Machine();
        fm_mine = new Mine();
        selet_num = 1;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frag, fm_family_stroll);
        ft.add(R.id.frag, fm_calendar);
        ft.add(R.id.frag, fm_mine);
        ft.add(R.id.frag, fm_search);
        ft.add(R.id.frag, fm_time_machine);
        ft.hide(fm_time_machine).hide(fm_search).hide(fm_mine).hide(fm_calendar).commit();
    }

    /*
    fragment跳转到activity需要在activity中获取控件
    不能在fragment中，会因为fragment过期导致空指针
    并且要在onStart中设置监听，不然也会因为没有实例化而空指针
     */
    @Override
    protected void onStart() {
        super.onStart();

        //跳转到设置界面
        bt_setting = findViewById(R.id.bt_setting);
        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this,Setting.class);
                startActivity(intent);
            }
        });

        //跳转到家庭信息界面
        bt_familyInformation = findViewById(R.id.bt_FamilyInformation);
        bt_familyInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, FamilyInformation.class);
                startActivity(intent);
            }
        });
    }
}