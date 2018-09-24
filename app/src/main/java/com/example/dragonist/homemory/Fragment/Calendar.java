package com.example.dragonist.homemory.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dragonist.homemory.Adapter.DateAdapter;
import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.Date;

public class Calendar extends Fragment {
    private View view;
    private GridView gv_date;
    private ImageView pre_month;
    private ImageView pre_year;
    private ImageView next_month;
    private ImageView next_year;
    private TextView calendar_title;
    private int year;
    private int month;
    private int [][]days = new int[6][7];
    private DateAdapter dateAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_calendar, container, false);
        init();
        return view;
    }

    private void init() {
        pre_month = view.findViewById(R.id.pre_month);
        pre_year = view.findViewById(R.id.pre_year);
        next_month = view.findViewById(R.id.next_month);
        next_year = view.findViewById(R.id.next_year);
        calendar_title = view.findViewById(R.id.calendar_title);
        gv_date = view.findViewById(R.id.calendar);

        year = Date.getYear();
        month = Date.getMonth();
        days = Date.getDayOfMonthFormat(2018, 9);
        dateAdapter = new DateAdapter(getContext(), days, year, month);
        gv_date.setAdapter(dateAdapter);
        setTitle();

        pre_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = preYear();
                dateAdapter = new DateAdapter(getContext(),days,year,month);
                gv_date.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTitle();
            }
        });

        next_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = nextYear();
                dateAdapter = new DateAdapter(getContext(),days,year,month);
                gv_date.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTitle();
            }
        });

        pre_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = prevMonth();
                dateAdapter = new DateAdapter(getContext(),days,year,month);
                gv_date.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTitle();
            }
        });

        next_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days = nextMonth();
                dateAdapter = new DateAdapter(getContext(),days,year,month);
                gv_date.setAdapter(dateAdapter);
                dateAdapter.notifyDataSetChanged();
                setTitle();
            }
        });
    }

    private void setTitle() {
        String title = year + "年" + month + "月";
        calendar_title.setText(title);
    }

    private int[][] nextMonth() {
        if (month == 12) {
            month = 1;
            year++;
        } else {
            month++;
        }
        days = Date.getDayOfMonthFormat(year, month);
        return days;
    }

    private int[][] prevMonth() {
        if (month == 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        days = Date.getDayOfMonthFormat(year, month);
        return days;
    }

    private int[][] preYear() {
        year--;
        days = Date.getDayOfMonthFormat(year, month);
        return days;
    }

    private int[][] nextYear() {
        year++;
        days = Date.getDayOfMonthFormat(year, month);
        return days;
    }
}
