package com.example.dragonist.homemory.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.EnvironmentalReverb;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.dragonist.homemory.Activity.Videoview;
import com.example.dragonist.homemory.Adapter.MemoryAdapter;
import com.example.dragonist.homemory.Adapter.MonthAdapter;
import com.example.dragonist.homemory.Adapter.YearAdapter;
import com.example.dragonist.homemory.Bean.MemoryBean;
import com.example.dragonist.homemory.BitmapAndType;
import com.example.dragonist.homemory.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
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

import static android.util.Base64.DEFAULT;

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
    private Button btn_month;
    private Button btn_year;
    private Button btn_familyBill;
    private Button btn_personalBill;
    private AlertDialog dialog_bill;

    private String account;

    private ForegroundColorSpan colorSpan = new ForegroundColorSpan(0x48baee);
    private AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(30);

    private int year;
    private List<MemoryBean> memories = new ArrayList<MemoryBean>();
    /* private Handler handler=new Handler(){
         @Override
         public void handleMessage(Message msg) {
             switch (msg.what){
                 case 1:
                     ArrayList<Bitmap> bitmapArrayList=(ArrayList<Bitmap>)msg.obj;
                     for(Bitmap tempBitmap:bitmapArrayList){
                         memories.add(new MemoryBean(tempBitmap,"爸爸",20,tempBitmap));
                     }
                     memoryAdapter = new MemoryAdapter(getContext(),memories);
                     lv_memory.setAdapter(memoryAdapter);
                     memoryAdapter.notifyDataSetChanged();
             }
         }
     };*/
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ArrayList<MemoryBean> memoryBeans = (ArrayList<MemoryBean>) msg.obj;
                    for (MemoryBean e : memoryBeans) {
                        if (!memories.contains(e))
                            memories.add(e);
                    }
                    memoryAdapter = new MemoryAdapter(getContext(), memories);
                    lv_memory.setAdapter(memoryAdapter);
                    memoryAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time_machine, container, false);
        init();
        try {
            setMemories();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void init() {
        year = Calendar.getInstance().get(Calendar.YEAR);

        sv_month = view.findViewById(R.id.sv_month);
        sv_month.setVisibility(View.INVISIBLE);
        sv_year = view.findViewById(R.id.sv_year);
        sv_year.setVisibility(View.INVISIBLE);

        //月份的收放
        btn_month = view.findViewById(R.id.bt_month);
        btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sv_month.getVisibility() == View.INVISIBLE)
                    sv_month.setVisibility(View.VISIBLE);
                else sv_month.setVisibility(View.INVISIBLE);
            }
        });

        //年份的收放
        btn_year = view.findViewById(R.id.bt_year);
        btn_year.setOnClickListener(new View.OnClickListener() {
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
                btn_month.setText(position + "");
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
                btn_year.setText((year - position) + "");
                sv_year.setVisibility(View.INVISIBLE);
            }
        });

        //记忆列表
        lv_memory = view.findViewById(R.id.lv_memory);
        memoryAdapter = new MemoryAdapter(getContext(), memories);
        lv_memory.setAdapter(memoryAdapter);
        memoryAdapter.notifyDataSetChanged();
        lv_memory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MemoryBean memoryBean = memories.get(i);
                Log.e("当前是第：", i + "个Item");
                switch (memoryBean.getType()) {
                    case "Image":
                        break;
                    case "Music":
                        Intent intentMusic = new Intent();
                        intentMusic.setAction(Intent.ACTION_VIEW);
                        intentMusic.setDataAndType(Uri.parse(memoryBean.getPath()), "audio/*");
                        break;
                    case "Document":
                        File file = new File(memoryBean.getPath());
                        Intent intentDocument = new Intent("android.intent.action.View");
                        intentDocument.addCategory("android.intent.category.DEFAULT");
                        intentDocument.setDataAndType(Uri.fromFile(file), "application");
                        startActivity(intentDocument);
                        break;
                    case "Video":
                        String filepath = memoryBean.getPath();
                        Intent intent = new Intent(getContext(), Videoview.class);
                        intent.putExtra("path", filepath);
                        startActivity(intent);
                        break;
                    default:
                        break;

                }
            }
        });
        btn_familyBill = view.findViewById(R.id.btn_familyBill);
        btn_familyBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] blank = {"296", "照片", "宝宝康乐", "图图妈", "图图出生了"};
                Map<String, String> blanks = new HashMap<>();
                blanks.put("title", "家庭集体账单");
                blanks.put("family", "辛普森");
                blanks.put("year", "2018");
                blanks.put("fileNumber", "296");
                blanks.put("fileFormat", "照片");
                blanks.put("fileType", "宝宝康乐");
                blanks.put("uploader", "@图图妈");
                blanks.put("popularFile", "图图出生了");
                show_dialogBill(blanks);
            }
        });
        btn_personalBill = view.findViewById(R.id.btn_personalBill);
    }

    private void show_dialogBill(Map<String, String> blanks) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_bill, null);

        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(blanks.get("title"));
        TextView tv_first = view.findViewById(R.id.tv_first);
        //tv_first.setText(setSpan(blanks.get("year")));

        builder.setView(view);
        dialog_bill = builder.show();
    }

    private ArrayList<BitmapAndType> getServerPath() throws InterruptedException, ExecutionException {
        ArrayList<BitmapAndType> serverInformationList = new ArrayList<>(0);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        firstRequest firstrequest = new firstRequest(serverInformationList, this.getActivity().getSharedPreferences("HOMEMORY", Context.MODE_PRIVATE).getString("account", account));
        Future<ArrayList<BitmapAndType>> future = executorService.submit(firstrequest);
        ArrayList<BitmapAndType> test = new ArrayList<>();
        test = future.get();
        Log.e("BitmapAndType个数:", String.valueOf(test.size()));
        return test;//第一次请求，获得服务端的该账户的所有的存储数据的信息。返回一个列表
    }

    private void setMemories() throws InterruptedException, ExecutionException {
        final ArrayList<MemoryBean> memoryBeans = new ArrayList<>(0);//存放memorybean的集合
        final ArrayList<BitmapAndType> bitmapAndTypeArrayList = getServerPath();
        Log.e("服务端文件数量", String.valueOf(bitmapAndTypeArrayList.size()));//获得服务器端所有的文件数量
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (final BitmapAndType bitmapAndType : bitmapAndTypeArrayList) {
                    OkHttpClient client = new OkHttpClient();
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    RequestBody requestBody = null;
                    requestBody = builder.addFormDataPart("id", bitmapAndType.getId()).setType(MultipartBody.FORM).build();
                    Request request = new Request.Builder().url("http://118.25.210.13:8080/Homemory/Memory").post(requestBody).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("error：", e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("转码之后:", URLDecoder.decode(bitmapAndType.getType(), "UTF-8"));
                            switch (bitmapAndType.getType()) {
                                case "Image":
                                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                                    MemoryBean memoryBean = new MemoryBean(bitmap, "爸爸", 20, bitmap);
                                    memoryBean.setType("Image");
                                    if (!memoryBeans.contains(memoryBean)) {
                                        memoryBeans.add(memoryBean);
                                        Message message = new Message();
                                        message.what = 1;
                                        message.obj = memoryBeans;
                                        handler.sendMessage(message);
                                    }
                                    break;
                                case "Music":
                                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
                                    Date date1 = new Date();
                                    String datestr1 = simpleDateFormat1.format(date1);
                                    Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.music);
                                    MemoryBean memoryBeanMusic = new MemoryBean(bitmap1, "爸爸", 20, bitmap1);
                                    memoryBeanMusic.setType("Music");
                                    File fileMusic = new File("/storage/emulated/0/DCIM/Camera/" + datestr1 + ".mp3");//getURL其实是getFilename
                                    BufferedOutputStream bufferedOutputStreamMusic = new BufferedOutputStream(new FileOutputStream(fileMusic));
                                    byte[] bytesMusic = new byte[1024];
                                    InputStream inputStreamMusic = response.body().byteStream();
                                    while (inputStreamMusic.read(bytesMusic) != -1) {
                                        bufferedOutputStreamMusic.write(bytesMusic);
                                    }
                                    bufferedOutputStreamMusic.flush();
                                    bufferedOutputStreamMusic.close();
                                    inputStreamMusic.close();
                                    memoryBeanMusic.setPath(fileMusic.getPath());
                                    memoryBeans.add(memoryBeanMusic);
                                    Message message1 = new Message();
                                    message1.what = 1;
                                    message1.obj = memoryBeans;
                                    handler.sendMessage(message1);
                                    break;
                                case "Video":
//                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//                                    Date date = new Date();
                                    String datestr = UUID.randomUUID().toString().replaceAll("-", "");
                                    Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.video);
                                    MemoryBean memoryBean1 = new MemoryBean(bitmap2, "爸爸", 20, bitmap2);
                                    memoryBean1.setType("Video");
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
                                    memoryBean1.setPath(file.getPath());
                                    memoryBeans.add(memoryBean1);
                                    Message message2 = new Message();
                                    message2.what = 1;
                                    message2.obj = memoryBeans;
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

    private SpannableStringBuilder setSpan(String context) {
        SpannableStringBuilder builder = new SpannableStringBuilder(context);
        builder.setSpan(colorSpan, 0, context.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(sizeSpan, 0, context.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}

//异步线程
class firstRequest implements Callable<ArrayList<BitmapAndType>> {
    ArrayList<BitmapAndType> serverInformationList;
    CountDownLatch lock;
    String account;

    public firstRequest(ArrayList<BitmapAndType> e, String account) {
        serverInformationList = e;
        lock = new CountDownLatch(1);
        this.account = account;
    }

    @Override
    public ArrayList<BitmapAndType> call() throws Exception {
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody requestBody = null;
        requestBody = builder.addFormDataPart("account", URLEncoder.encode(account, "UTF-8")).setType(MultipartBody.FORM).build();
        Request request = new Request.Builder().post(requestBody).url("http://118.25.210.13:8080/Homemory/TimeMachine").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error:", e.toString());
                lock.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BufferedReader bufferreader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                StringBuilder serverFilePath = new StringBuilder("");
                String temp = "";
                while ((temp = bufferreader.readLine()) != null) {
                    serverFilePath.append(temp);
                }
                try {
                    Log.e("文件信息", serverFilePath.toString());
                    JSONObject jsonObject = new JSONObject(serverFilePath.toString());
                    int amount = Integer.parseInt(jsonObject.getString("amount"));
                    //获取所有的服务端路径，用于等下的请求
                    for (int i = 1; i <= amount; i++) {
                        JSONObject jsonObjectSon = new JSONObject(jsonObject.getString(String.valueOf(i)));
                        Log.e("json", jsonObjectSon.toString());
                        serverInformationList.add(new BitmapAndType(jsonObjectSon.getString("fileName"), jsonObjectSon.getString("fileType"), jsonObjectSon.getString("id")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lock.countDown();
            }
        });
        lock.await();
        return serverInformationList;
    }

    private String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}

