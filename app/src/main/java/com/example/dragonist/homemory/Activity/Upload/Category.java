package com.example.dragonist.homemory.Activity.Upload;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.dragonist.homemory.Adapter.CategoryAdapter;
import com.example.dragonist.homemory.R;

public class Category extends AppCompatActivity {
    private ListView lv_category;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        init();

        lv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryAdapter = new CategoryAdapter(view.getContext(),position);
                lv_category.setAdapter(categoryAdapter);
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        lv_category = findViewById(R.id.lv_categorys);
        categoryAdapter = new CategoryAdapter(this,-1);
        lv_category.setAdapter(categoryAdapter);
    }
}
