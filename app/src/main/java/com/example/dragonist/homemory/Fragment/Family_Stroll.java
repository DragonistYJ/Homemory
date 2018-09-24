package com.example.dragonist.homemory.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.example.dragonist.homemory.R;

public class Family_Stroll extends Fragment {
    private View view;
    private HorizontalScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_family_stroll, container, false);
        return view;
    }

    private void init() {
        scrollView = view.findViewById(R.id.background);
    }
}
