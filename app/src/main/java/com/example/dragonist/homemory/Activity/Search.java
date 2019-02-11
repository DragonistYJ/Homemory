package com.example.dragonist.homemory.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.dragonist.homemory.Bean.Archive;
import com.example.dragonist.homemory.Bean.MemoryBean;
import com.example.dragonist.homemory.BitmapAndType;
import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.SearchCallableDate;
import com.example.dragonist.homemory.Utils.SearchCallableFormat;
import com.example.dragonist.homemory.Utils.SearchCallableKey;
import com.example.dragonist.homemory.Utils.SearchCallableTheme;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Search extends AppCompatActivity {
    private TextView tvCancel;
    private TextView tvConfirm;
    private String layout;
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
    private ArrayList<Archive> archives = new ArrayList<>();
    private UploaderAdapter uploaderAdapter;
    //result
    private Button btnReturn;
    private ListView lvResult;
    private ResultAdapter resultAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    archives = (ArrayList<Archive>) msg.obj;
                    resultAdapter = new ResultAdapter(getApplicationContext(), archives);
                    lvResult.setAdapter(resultAdapter);
                    resultAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = getIntent().getStringExtra("layout");
        switch (layout) {
            case "time":
                setContentView(R.layout.activity_search_time);
                init();
                initTime();
                break;
            case "keyword":
                setContentView(R.layout.activity_search_keyword);
                init();
                initKeyword();
                break;
            case "format":
                setContentView(R.layout.activity_search_format);
                init();
                initFormat();
                break;
            case "theme":
                setContentView(R.layout.activity_search_theme);
                init();
                initTheme();
                break;
            case "authority":
                setContentView(R.layout.activity_search_authority);
                init();
                initAuthority();
                break;
            case "uploader":
                setContentView(R.layout.activity_search_uploader);
                init();
                initUploader();
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
                switch (layout) {
                    case "time":
                        String SearchDate = tvYear.getText().toString() + "-" + tvMonth.getText().toString() + "-" + tvDay.getText().toString();
                        String SearchDateAddOne = tvYear.getText().toString() + "-" + tvMonth.getText().toString() + "-" + (Integer.parseInt(tvDay.getText().toString()) + 1);
                        SearchCallableDate searchCallableDate = new SearchCallableDate(SearchDate, getApplicationContext(), getSharedPreferences("HOMEMORY", MODE_PRIVATE).getString("account", ""), SearchDateAddOne);
                        try {
                            setMemories(searchCallableDate);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "theme":
                        ArrayList<String> Theme = new ArrayList<>();
                        for (int i = 0; i < 6; i++) {
                            if (i == 0) {
                                if (cbBaby.isChecked()) {
                                    Theme.add("宝宝康乐");
                                }
                            } else if (i == 1) {
                                if (cbHistory.isChecked()) {
                                    Theme.add("家庭历史");
                                }
                            } else if (i == 2) {
                                if (cbCustom.isChecked()) {
                                    Theme.add("自定义");
                                }
                            } else if (i == 3) {
                                if (cbStudent.isChecked()) {
                                    Theme.add("学生时代");
                                }
                            } else if (i == 4) {
                                if (cbLove.isChecked()) {
                                    Theme.add("恋爱婚姻");
                                }
                            } else if (i == 5) {
                                if (cbWord.isChecked()) {
                                    Theme.add("工作成果");
                                }
                            }
                        }
                        SearchCallableTheme searchCallableTheme = new SearchCallableTheme(getApplicationContext(), getSharedPreferences("HOMEMORY", MODE_PRIVATE).getString("account", ""), Theme);
                        try {
                            setMemoriesTheme(searchCallableTheme);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "keyword":
                        String Keyword = etKeyword.getText().toString();
                        SearchCallableKey searchCallableKey = new SearchCallableKey(Keyword, getApplicationContext(), getSharedPreferences("HOMEMORY", MODE_PRIVATE).getString("account", ""));
                        try {
                            setMemoriesKey(searchCallableKey);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "format":
                        ArrayList<String> Format = new ArrayList<>();
                        for (int i = 0; i < 4; i++) {
                            if (i == 0) {
                                if (cbImage.isChecked()) {
                                    Format.add("Image");
                                }
                            } else if (i == 1) {
                                if (cbVideo.isChecked()) {
                                    Format.add("Video");
                                }
                            } else if (i == 2) {
                                if (cbMusic.isChecked()) {
                                    Format.add("Music");
                                }
                            } else if (i == 3) {
                                if (cbDocument.isChecked()) {
                                    Format.add("Document");
                                }
                            }
                        }
                        SearchCallableFormat searchCallableFormat = new SearchCallableFormat(Format, getApplicationContext(), getSharedPreferences("HOMEMORY", MODE_PRIVATE).getString("account", ""));
                        try {
                            setMemoriesFormat(searchCallableFormat);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;


                }
                setContentView(R.layout.acitvity_search_result);
                initResult();
            }
        });
    }

    private void initTime() {
        yearAdapter = new SearchDate(this, Calendar.getInstance().get(Calendar.YEAR));
        monthAdapter = new SearchDate(this, 12);
        dayAdapter = new SearchDate(this, 31);

        llMonth = findViewById(R.id.llMonth);
        lvMonth = findViewById(R.id.lvMonth);
        llMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvMonth.getVisibility() == View.VISIBLE) lvMonth.setVisibility(View.INVISIBLE);
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
                tvMonth.setText((12 - position) + "");
                lvMonth.setVisibility(View.INVISIBLE);
            }
        });

        llYear = findViewById(R.id.llYear);
        lvYear = findViewById(R.id.lvYear);
        llYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvYear.getVisibility() == View.VISIBLE) lvYear.setVisibility(View.INVISIBLE);
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
                tvYear.setText((Calendar.getInstance().get(Calendar.YEAR) - position) + "");
                lvYear.setVisibility(View.INVISIBLE);
            }
        });

        llDay = findViewById(R.id.llDay);
        lvDay = findViewById(R.id.lvDay);
        llDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvDay.getVisibility() == View.VISIBLE) lvDay.setVisibility(View.INVISIBLE);
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
                tvDay.setText((31 - position) + "");
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
                if (rlFormat.getVisibility() == View.VISIBLE)
                    rlFormat.setVisibility(View.INVISIBLE);
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
                if (rlTheme.getVisibility() == View.VISIBLE) rlTheme.setVisibility(View.INVISIBLE);
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
                if (rlAuthority.getVisibility() == View.VISIBLE)
                    rlAuthority.setVisibility(View.INVISIBLE);
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
                if (llUploaders.getVisibility() == View.VISIBLE)
                    llUploaders.setVisibility(View.INVISIBLE);
                else llUploaders.setVisibility(View.VISIBLE);
            }
        });

        lvUploader = findViewById(R.id.lvUploader);
        //uploader.add("132154");
        //uploader.add("djfls");
        uploaderAdapter = new UploaderAdapter(uploader, select, this);
        lvUploader.setAdapter(uploaderAdapter);
        lvUploader.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (select.contains(position)) select.remove((Object) position);
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
    }

    private void setMemories(SearchCallableDate searchCallableDate) throws InterruptedException, ExecutionException {
        final ArrayList<Archive> ArchiveBeans = new ArrayList<>(0);//存放memorybean的集合
        final ArrayList<Archive> ArchiveArrayList = getServerPath(searchCallableDate);
        Log.e("服务端文件数量", String.valueOf(ArchiveArrayList.size()));//获得服务器端所有的文件数量
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (final Archive archive : ArchiveArrayList) {
                    OkHttpClient client = new OkHttpClient();
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    RequestBody requestBody = null;
                    requestBody = builder.addFormDataPart("id", archive.getId()).setType(MultipartBody.FORM).build();
                    Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/Memory").post(requestBody).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("error：", e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("转码之后:", URLDecoder.decode(archive.getFileType(), "UTF-8"));
                            switch (archive.getFileType()) {
                                case "Image":
                                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                    archive.setIcon(bitmap);
                                    archive.setFileType("Image");
                                    Message message = new Message();
                                    message.what = 1;
                                    message.obj = ArchiveArrayList;
                                    handler.sendMessage(message);
                                    break;
                                case "Music":
                                    break;
                                case "Video":
//                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//                                    Date date = new Date();
                                    String datestr = UUID.randomUUID().toString().replaceAll("-", "");
                                    Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.video);
                                    archive.setFileType("Video");
                                    File file = new File("/storage/emulated/0/DCIM/Camera/" + datestr + ".mp4");
                                    OutputStream bufferedOutputStream = new FileOutputStream(file);
                                    byte[] bytes = new byte[1024];
                                    int len = 0;
                                    InputStream inputStream = response.body().byteStream();
                                    while ((len = inputStream.read(bytes)) != -1) {
                                        bufferedOutputStream.write(bytes, 0, len);
                                    }
                                    bufferedOutputStream.flush();
                                    bufferedOutputStream.close();
                                    inputStream.close();
                                    archive.setPath(file.getPath());
                                    Message message2 = new Message();
                                    message2.what = 1;
                                    message2.obj = ArchiveArrayList;
                                    handler.sendMessage(message2);
                                    break;
                                case "Document":
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private void setMemoriesTheme(SearchCallableTheme searchCallableTheme) throws InterruptedException, ExecutionException {
        final ArrayList<Archive> ArchiveBeans = new ArrayList<>(0);//存放memorybean的集合
        final ArrayList<Archive> ArchiveArrayList = getServerPathTheme(searchCallableTheme);
        Log.e("服务端文件数量", String.valueOf(ArchiveArrayList.size()));//获得服务器端所有的文件数量
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (final Archive archive : ArchiveArrayList) {
                    OkHttpClient client = new OkHttpClient();
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    RequestBody requestBody = null;
                    requestBody = builder.addFormDataPart("id", archive.getId()).setType(MultipartBody.FORM).build();
                    Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/Memory").post(requestBody).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("error：", e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("转码之后:", URLDecoder.decode(archive.getFileType(), "UTF-8"));
                            switch (archive.getFileType()) {
                                case "Image":
                                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                    archive.setIcon(bitmap);
                                    archive.setFileType("Image");
                                    Message message = new Message();
                                    message.what = 1;
                                    message.obj = ArchiveArrayList;
                                    handler.sendMessage(message);
                                    break;
                                case "Music":
                                    break;
                                case "Video":
//                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//                                    Date date = new Date();
                                    String datestr = UUID.randomUUID().toString().replaceAll("-", "");
                                    Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.video);
                                    archive.setFileType("Video");
                                    File file = new File("/storage/emulated/0/DCIM/Camera/" + datestr + ".mp4");
                                    OutputStream bufferedOutputStream = new FileOutputStream(file);
                                    byte[] bytes = new byte[1024];
                                    int len = 0;
                                    InputStream inputStream = response.body().byteStream();
                                    while ((len = inputStream.read(bytes)) != -1) {
                                        bufferedOutputStream.write(bytes, 0, len);
                                    }
                                    bufferedOutputStream.flush();
                                    bufferedOutputStream.close();
                                    inputStream.close();
                                    archive.setPath(file.getPath());
                                    Message message2 = new Message();
                                    message2.what = 1;
                                    message2.obj = ArchiveArrayList;
                                    handler.sendMessage(message2);
                                    break;
                                case "Document":
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private void setMemoriesKey(SearchCallableKey searchCallableKey) throws InterruptedException, ExecutionException {
        final ArrayList<Archive> ArchiveBeans = new ArrayList<>(0);//存放memorybean的集合
        final ArrayList<Archive> ArchiveArrayList = getServerPathKey(searchCallableKey);
        Log.e("服务端文件数量", String.valueOf(ArchiveArrayList.size()));//获得服务器端所有的文件数量
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (final Archive archive : ArchiveArrayList) {
                    OkHttpClient client = new OkHttpClient();
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    RequestBody requestBody = null;
                    requestBody = builder.addFormDataPart("id", archive.getId()).setType(MultipartBody.FORM).build();
                    Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/Memory").post(requestBody).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("error：", e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("转码之后:", URLDecoder.decode(archive.getFileType(), "UTF-8"));
                            switch (archive.getFileType()) {
                                case "Image":
                                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                    archive.setIcon(bitmap);
                                    archive.setFileType("Image");
                                    Message message = new Message();
                                    message.what = 1;
                                    message.obj = ArchiveArrayList;
                                    handler.sendMessage(message);
                                    break;
                                case "Music":
                                    break;
                                case "Video":
//                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//                                    Date date = new Date();
                                    String datestr = UUID.randomUUID().toString().replaceAll("-", "");
                                    Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.video);
                                    archive.setFileType("Video");
                                    File file = new File("/storage/emulated/0/DCIM/Camera/" + datestr + ".mp4");
                                    OutputStream bufferedOutputStream = new FileOutputStream(file);
                                    byte[] bytes = new byte[1024];
                                    int len = 0;
                                    InputStream inputStream = response.body().byteStream();
                                    while ((len = inputStream.read(bytes)) != -1) {
                                        bufferedOutputStream.write(bytes, 0, len);
                                    }
                                    bufferedOutputStream.flush();
                                    bufferedOutputStream.close();
                                    inputStream.close();
                                    archive.setPath(file.getPath());
                                    Message message2 = new Message();
                                    message2.what = 1;
                                    message2.obj = ArchiveArrayList;
                                    handler.sendMessage(message2);
                                    break;
                                case "Document":
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private void setMemoriesFormat(SearchCallableFormat searchCallableFormat) throws InterruptedException, ExecutionException {
        final ArrayList<Archive> ArchiveBeans = new ArrayList<>(0);//存放memorybean的集合
        final ArrayList<Archive> ArchiveArrayList = getServerPathFormat(searchCallableFormat);
        Log.e("服务端文件数量", String.valueOf(ArchiveArrayList.size()));//获得服务器端所有的文件数量
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (final Archive archive : ArchiveArrayList) {
                    OkHttpClient client = new OkHttpClient();
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    RequestBody requestBody = null;
                    requestBody = builder.addFormDataPart("id", archive.getId()).setType(MultipartBody.FORM).build();
                    Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/Memory").post(requestBody).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("error：", e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("转码之后:", URLDecoder.decode(archive.getFileType(), "UTF-8"));
                            switch (archive.getFileType()) {
                                case "Image":
                                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                    archive.setIcon(bitmap);
                                    archive.setFileType("Image");
                                    Message message = new Message();
                                    message.what = 1;
                                    message.obj = ArchiveArrayList;
                                    handler.sendMessage(message);
                                    break;
                                case "Music":
                                    break;
                                case "Video":
//                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//                                    Date date = new Date();
                                    String datestr = UUID.randomUUID().toString().replaceAll("-", "");
                                    Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.video);
                                    archive.setFileType("Video");
                                    File file = new File("/storage/emulated/0/DCIM/Camera/" + datestr + ".mp4");
                                    OutputStream bufferedOutputStream = new FileOutputStream(file);
                                    byte[] bytes = new byte[1024];
                                    int len = 0;
                                    InputStream inputStream = response.body().byteStream();
                                    while ((len = inputStream.read(bytes)) != -1) {
                                        bufferedOutputStream.write(bytes, 0, len);
                                    }
                                    bufferedOutputStream.flush();
                                    bufferedOutputStream.close();
                                    inputStream.close();
                                    archive.setPath(file.getPath());
                                    Message message2 = new Message();
                                    message2.what = 1;
                                    message2.obj = ArchiveArrayList;
                                    handler.sendMessage(message2);
                                    break;
                                case "Document":
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public ArrayList<Archive> getServerPath(SearchCallableDate searchCallableDate) throws ExecutionException, InterruptedException {
        ArrayList<Archive> serverInformationList = new ArrayList<>(0);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<ArrayList<Archive>> future = executorService.submit(searchCallableDate);
        ArrayList<Archive> result = future.get();
        Log.e("BitmapAndType个数:", String.valueOf(result.size()));
        return result;
    }

    public ArrayList<Archive> getServerPathTheme(SearchCallableTheme searchCallableTheme) throws ExecutionException, InterruptedException {
        ArrayList<Archive> serverInformationList = new ArrayList<>(0);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<ArrayList<Archive>> future = executorService.submit(searchCallableTheme);
        ArrayList<Archive> result = future.get();
        Log.e("BitmapAndType个数:", String.valueOf(result.size()));
        return result;
    }

    public ArrayList<Archive> getServerPathKey(SearchCallableKey searchCallableKey) throws ExecutionException, InterruptedException {
        ArrayList<Archive> serverInformationList = new ArrayList<>(0);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<ArrayList<Archive>> future = executorService.submit(searchCallableKey);
        ArrayList<Archive> result = future.get();
        Log.e("BitmapAndType个数:", String.valueOf(result.size()));
        return result;
    }

    public ArrayList<Archive> getServerPathFormat(SearchCallableFormat searchCallableFormat) throws ExecutionException, InterruptedException {
        ArrayList<Archive> serverInformationList = new ArrayList<>(0);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<ArrayList<Archive>> future = executorService.submit(searchCallableFormat);
        ArrayList<Archive> result = future.get();
        Log.e("BitmapAndType个数:", String.valueOf(result.size()));
        return result;
    }
}
