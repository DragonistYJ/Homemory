package com.example.dragonist.homemory.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dragonist.homemory.Activity.Mine.Setting;
import com.example.dragonist.homemory.R;

public class Mine extends Fragment {
    private View view;
    private Button bt_settting;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        bt_settting = getActivity().findViewById(R.id.bt_setting);
//        bt_settting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Setting.class);
//                getActivity().startActivity(intent);
//            }
//        });
    }
}
