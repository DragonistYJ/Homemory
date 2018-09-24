package com.example.dragonist.homemory.Activity.Mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.dragonist.homemory.R;

public class FamilyInformation extends AppCompatActivity {
    private Button bt_return;
    private ListView lv_familyMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_information);
        init();
    }

    private void init() {
        bt_return = findViewById(R.id.bt_return);
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lv_familyMember = findViewById(R.id.lv_familyMember);
    }
}
