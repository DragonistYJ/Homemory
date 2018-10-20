package com.example.dragonist.homemory.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dragonist.homemory.Adapter.ResultAdapter;
import com.example.dragonist.homemory.Adapter.SearchDate;
import com.example.dragonist.homemory.Adapter.UploaderAdapter;
import com.example.dragonist.homemory.R;

import java.util.ArrayList;
import java.util.Calendar;

public class Search extends AppCompatActivity {
    private TextView tvCancel;
    private TextView tvConfirm;
    //time
    private SearchDate yearAdapter;
    private SearchDate monthAdapter;
    private SearchDate dayAdapter;
    private LinearLayout llMonth;
    private LinearLayout llYear;
    private LinearLayout llDay;
    private ListView lvMonth;
    private ListView lvDay;
    private ListView lvYear;
    private TextView tvMonth;
    private TextView tvYear;
    private TextView tvDay;
    //keyword
    private EditText etKeyword;
    //format
    private LinearLayout llFormat;
    private RelativeLayout rlFormat;
    private CheckBox cbImage;
    private CheckBox cbVideo;
    private CheckBox cbMusic;
    private CheckBox cbDocument;
    //theme
    private LinearLayout llTheme;
    private RelativeLayout rlTheme;
    private CheckBox cbBaby;
    private CheckBox cbWord;
    private CheckBox cbStudent;
    private CheckBox cbHistory;
    private CheckBox cbLove;
    private CheckBox cbCustom;
    //authority
    private LinearLayout llAuthority;
    private RelativeLayout rlAuthority;
    private RadioGroup rgAuthority;
    //uploader
    private LinearLayout llUploader;
    private LinearLayout llUploaders;
    private ListView lvUploader;
    private ArrayList<String> uploader = new ArrayList<>();
    private ArrayList<Integer> select = new ArrayList<>();
    private UploaderAdapter uploaderAdapter;
    //result
    private Button btnReturn;
    private ListView lvResult;
    private ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String layout = getIntent().getStringExtra("layout");
        switch (layout){
            case "time":
                setContentView(R.layout.activity_search_time);
                init(); initTime();
                break;
            case "keyword":
                setContentView(R.layout.activity_search_keyword);
                init(); initKeyword();
                break;
            case "format":
                setContentView(R.layout.activity_search_format);
                init(); initFormat();
                break;
            case  "theme":
                setContentView(R.layout.activity_search_theme);
                init(); initTheme();
                break;
            case "authority":
                setContentView(R.layout.activity_search_authority);
                init(); initAuthority();
                break;
            case "uploader":
                setContentView(R.layout.activity_search_uploader);
                init(); initUploader();
                break;
        }
    }

    private void init() {
        tvCancel = findViewById(R.id.btnCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvConfirm = findViewById(R.id.btnConfirm);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.acitvity_search_result);
                initResult();
            }
        });
    }

    private void initTime() {
        yearAdapter = new SearchDate(this,Calendar.getInstance().get(Calendar.YEAR));
        monthAdapter = new SearchDate(this,12);
        dayAdapter = new SearchDate(this,31);

        llMonth = findViewById(R.id.llMonth);
        lvMonth = findViewById(R.id.lvMonth);
        llMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvMonth.getVisibility()==View.VISIBLE) lvMonth.setVisibility(View.INVISIBLE);
                else {
                    lvMonth.setAdapter(monthAdapter);
                    lvMonth.setVisibility(View.VISIBLE);
                }
            }
        });
        tvMonth = findViewById(R.id.tvMonth);
        lvMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvMonth.setText((12-position)+"");
                lvMonth.setVisibility(View.INVISIBLE);
            }
        });

        llYear = findViewById(R.id.llYear);
        lvYear = findViewById(R.id.lvYear);
        llYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvYear.getVisibility()==View.VISIBLE) lvYear.setVisibility(View.INVISIBLE);
                else {
                    lvYear.setAdapter(yearAdapter);
                    lvYear.setVisibility(View.VISIBLE);
                }
            }
        });
        tvYear = findViewById(R.id.tvYear);
        lvYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvYear.setText((Calendar.getInstance().get(Calendar.YEAR)-position)+"");
                lvYear.setVisibility(View.INVISIBLE);
            }
        });

        llDay = findViewById(R.id.llDay);
        lvDay = findViewById(R.id.lvDay);
        llDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvDay.getVisibility()==View.VISIBLE) lvDay.setVisibility(View.INVISIBLE);
                else {
                    lvDay.setAdapter(dayAdapter);
                    lvDay.setVisibility(View.VISIBLE);
                }
            }
        });
        tvDay = findViewById(R.id.tvDay);
        lvDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvDay.setText((31-position)+"");
                lvDay.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initKeyword() {
        etKeyword = findViewById(R.id.etKeyword);
    }

    private void initFormat() {
        llFormat = findViewById(R.id.llFormat);
        rlFormat = findViewById(R.id.rlFormat);
        llFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlFormat.getVisibility()==View.VISIBLE) rlFormat.setVisibility(View.INVISIBLE);
                else rlFormat.setVisibility(View.VISIBLE);
            }
        });

        cbDocument = findViewById(R.id.cbDocument);
        cbImage = findViewById(R.id.cbImage);
        cbMusic = findViewById(R.id.cbMusic);
        cbVideo = findViewById(R.id.cbVideo);
    }

    private void initTheme() {
        llTheme = findViewById(R.id.llTheme);
        rlTheme = findViewById(R.id.rlTheme);
        llTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlTheme.getVisibility()==View.VISIBLE) rlTheme.setVisibility(View.INVISIBLE);
                else rlTheme.setVisibility(View.VISIBLE);
            }
        });

        cbBaby = findViewById(R.id.cbBaby);
        cbCustom = findViewById(R.id.cbCustom);
        cbHistory = findViewById(R.id.cbHistory);
        cbLove = findViewById(R.id.cbLove);
        cbStudent = findViewById(R.id.cbStudent);
        cbWord = findViewById(R.id.cbWork);
    }

    private void initAuthority() {
        llAuthority = findViewById(R.id.llAuthority);
        rlAuthority = findViewById(R.id.rlAuthority);
        llAuthority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlAuthority.getVisibility()==View.VISIBLE) rlAuthority.setVisibility(View.INVISIBLE);
                else rlAuthority.setVisibility(View.VISIBLE);
            }
        });

        rgAuthority = findViewById(R.id.rgAuthority);
        rgAuthority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //选择切换
            }
        });
    }

    private void initUploader() {
        llUploader = findViewById(R.id.llUploader);
        llUploaders = findViewById(R.id.llUploaders);
        llUploader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llUploaders.getVisibility()==View.VISIBLE) llUploaders.setVisibility(View.INVISIBLE);
                else llUploaders.setVisibility(View.VISIBLE);
            }
        });

        lvUploader = findViewById(R.id.lvUploader);
        //uploader.add("132154");
        //uploader.add("djfls");
        uploaderAdapter = new UploaderAdapter(uploader,select,this);
        lvUploader.setAdapter(uploaderAdapter);
        lvUploader.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (select.contains(position)) select.remove((Object)position);
                else select.add(position);
                uploaderAdapter.setSelect(select);
                uploaderAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initResult() {
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvResult = findViewById(R.id.lvResult);
        //resultAdapter = new ResultAdapter();
        //lvResult.setAdapter(resultAdapter);
        //resultAdapter.notifyDataSetChanged();
    }
}
