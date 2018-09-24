package com.example.dragonist.homemory.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.dragonist.homemory.Adapter.MemoryAdapter;
import com.example.dragonist.homemory.Adapter.MonthAdapter;
import com.example.dragonist.homemory.Adapter.YearAdapter;
import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.MemoryBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Time_Machine extends Fragment {
    private View view;
    private ListView lv_month;
    private ListView lv_year;
    private ListView lv_memory;
    private ScrollView sv_month;
    private ScrollView sv_year;
    private MonthAdapter monthAdapter;
    private YearAdapter yearAdapter;
    private MemoryAdapter memoryAdapter;
    private Button bt_month;
    private Button bt_year;

    private int year;
    private List<MemoryBean> memories = new ArrayList<MemoryBean>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time_machine, container, false);
        init();
        return view;
    }

    private void init() {
        year = Calendar.getInstance().get(Calendar.YEAR);

        sv_month = view.findViewById(R.id.sv_month);
        sv_month.setVisibility(View.INVISIBLE);
        sv_year = view.findViewById(R.id.sv_year);
        sv_year.setVisibility(View.INVISIBLE);

        //月份的收放
        bt_month = view.findViewById(R.id.bt_month);
        bt_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sv_month.getVisibility() == View.INVISIBLE) sv_month.setVisibility(View.VISIBLE);
                else sv_month.setVisibility(View.INVISIBLE);
            }
        });

        //年份的收放
        bt_year = view.findViewById(R.id.bt_year);
        bt_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sv_year.getVisibility() == View.INVISIBLE) sv_year.setVisibility(View.VISIBLE);
                else sv_year.setVisibility(View.INVISIBLE);
            }
        });

        //月份选择下拉列表
        lv_month = view.findViewById(R.id.lv_month);
        monthAdapter = new MonthAdapter(getContext());
        lv_month.setAdapter(monthAdapter);
        lv_month.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bt_month.setText(position+"");
                sv_month.setVisibility(View.INVISIBLE);
            }
        });

        //年份选择下拉列表
        lv_year = view.findViewById(R.id.lv_year);
        yearAdapter = new YearAdapter(getContext());
        lv_year.setAdapter(yearAdapter);
        lv_year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bt_year.setText((year-position)+"");
                sv_year.setVisibility(View.INVISIBLE);
            }
        });

        lv_memory = view.findViewById(R.id.lv_memory);
        memoryAdapter = new MemoryAdapter(getContext(),memories);
    }

    private void setMemories() {

    }
}
