package com.example.dragonist.homemory.Activity.Upload;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dragonist.homemory.Adapter.CategoryAdapter;
import com.example.dragonist.homemory.R;

public class Category extends AppCompatActivity {
    private ListView lv_category;
    private CategoryAdapter categoryAdapter;
    private ImageView iv_sure;
    private ImageView iv_cancel;
    private EditText et_self;
    private int select=-1;
    private String []items = {"宝宝康乐","学生时代","恋爱婚姻","工作成果","家庭历史"};

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
                select = position;
            }
        });

        iv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = et_self.getText().toString();
                if (et_self.getText().toString().isEmpty()) {
                    if (select==-1) {
                        Toast.makeText(Category.this, "请选择或输入所属类别", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    category = items[select];
                    Log.e("1",category);
                }
                Intent intent = new Intent();
                intent.putExtra("category",category);
                setResult(56,intent);
                finish();
            }
        });

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(57);
                finish();
            }
        });
    }

    private void init() {
        lv_category = findViewById(R.id.lv_categorys);
        categoryAdapter = new CategoryAdapter(this,-1);
        lv_category.setAdapter(categoryAdapter);
        iv_sure = findViewById(R.id.sure);
        iv_cancel = findViewById(R.id.cancel);
        et_self = findViewById(R.id.et_self);
    }
}
