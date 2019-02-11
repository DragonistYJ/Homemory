package com.example.dragonist.homemory.Activity;

import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dragonist.homemory.R;
import com.example.dragonist.homemory.Utils.Post2Server;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Year;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Register2 extends AppCompatActivity {
    private ImageView ivPortrait;
    private TextView tvPortrait;
    private EditText etNickName;
    private EditText etSex;
    private EditText etYear;
    private EditText etMonth;
    private EditText etDay;
    private EditText etAge;
    private EditText etHomeTown;
    private EditText etLocation;
    private EditText etPassword;
    private Button btnComplete;

    private String nickName;
    private String sex;
    private String year;
    private String month;
    private String day;
    private String age;
    private String homeTown;
    private String location;
    private String account;
    private String password;
    private File file = null;

    private Handler handler;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        init();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String result = (String) msg.obj;
                if (result.equals("ok")) {
                    sp = getSharedPreferences("HOMEMORY", MODE_PRIVATE);
                    editor = sp.edit();
                    editor.putString("account", account);
                    editor.putString("password", password);
                    editor.commit();
                    Intent intent = new Intent(Register2.this, Main.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Register2.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void init() {
        tvPortrait = findViewById(R.id.tvPortrait);
        ivPortrait = findViewById(R.id.portrait);
        tvPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,0x404);
            }
        });

        etNickName = findViewById(R.id.etNickName);
        etSex = findViewById(R.id.etSex);
        etYear = findViewById(R.id.etYear);
        etMonth = findViewById(R.id.etMonth);
        etDay = findViewById(R.id.etDay);
        etAge = findViewById(R.id.etAge);
        etHomeTown = findViewById(R.id.etHomeTown);
        etLocation = findViewById(R.id.etLocation);
        etPassword = findViewById(R.id.etPassword);

        btnComplete = findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = getIntent().getStringExtra("phone");
                password = etPassword.getText().toString();
                nickName = etNickName.getText().toString();
                sex = etSex.getText().toString();
                year = etYear.getText().toString();
                month = etMonth.getText().toString();
                day = etDay.getText().toString();
                age = etAge.getText().toString();
                homeTown = etHomeTown.getText().toString();
                location = etLocation.getText().toString();
                if (password.isEmpty() || nickName.isEmpty() || sex.isEmpty() || year.isEmpty() || month.isEmpty() || day.isEmpty()
                        || age.isEmpty() || homeTown.isEmpty() || location.isEmpty()) {
                    Toast.makeText(Register2.this, "请输入完整的账户信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (file==null) {
                    /*Toast.makeText(Register2.this, "头像不可为空", Toast.LENGTH_SHORT).show();
                    return;*/
                }

                MultipartBody.Builder builder=new MultipartBody.Builder();
                RequestBody requestFile = RequestBody.create(MediaType.parse("application/octet-stream"),file);
                RequestBody requestBody=null;
                try {
                    requestBody = builder.addFormDataPart("account", URLEncoder.encode(account, "UTF-8"))
                            .addFormDataPart("password", URLEncoder.encode(password, "UTF-8"))
                            .addFormDataPart("nickName", URLEncoder.encode(nickName, "UTF-8"))
                            .addFormDataPart("sex", URLEncoder.encode(sex, "UTF-8"))
                            .addFormDataPart("year", URLEncoder.encode(year, "UTF-8"))
                            .addFormDataPart("month", URLEncoder.encode(month, "UTF-8"))
                            .addFormDataPart("day", URLEncoder.encode(day, "UTF-8"))
                            .addFormDataPart("age", URLEncoder.encode(age, "UTF-8"))
                            .addFormDataPart("homeTown", URLEncoder.encode(homeTown, "UTF-8"))
                            .addFormDataPart("location", URLEncoder.encode(location, "UTF-8"))
                            .addFormDataPart("portrait", URLEncoder.encode("icon", "UTF-8"), requestFile)
                            .setType(MultipartBody.FORM).build();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Post2Server post = new Post2Server("http://118.25.210.13:8080/Homemory/Register",requestBody,handler);
                new Thread(post).start();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String filePathImage(Intent data){
        Uri uri=data.getData();
        String docid= DocumentsContract.getDocumentId(uri);
        String id=docid.split(":")[1];
        String selection= MediaStore.Images.Media._ID+"="+id;
        return filePathBeforeImage(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
    }

    public  String filePathBeforeImage(Uri uri, String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode!=0x404) return;
        file = new File(filePathImage(data));
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        ivPortrait.setImageBitmap(bitmap);
    }
}
