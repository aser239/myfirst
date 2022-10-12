package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapter.SignlistAdapter;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.StudentActivity.StudentCourseListActivity;
import com.example.myapplication.javaBean.Records2;
import com.example.myapplication.javaBean.Records2Detail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignlistActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public static final String UNFINISHED_MESSAGE_STRING = "com.example.myapplication.Activity.UNFINISHED_INFO";
    private SignlistAdapter adapter;
    private List<Records2Detail> newsData;
    private ListView lvNewsList;
    private ResponseBody<Records2> dataResponseBody;
    public static int userSignID;

    public SignlistActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signlist);
        lvNewsList = findViewById(R.id.lv_news_list66);

        initData();
        lvNewsList.setOnItemClickListener(this);

    }

    private void initData() {
        newsData = new ArrayList<>();
        adapter = new SignlistAdapter(SignlistActivity.this,
                R.layout.list_item5, newsData);

        lvNewsList.setAdapter(adapter);
        Sign(StudentCourseListActivity.courseId,1,5, 0,LoginData.loginUser.getId());

    }


    public void Sign(int courseId,int current, int size,int status,int userId) {
        // url路径
        String url = "http://47.107.52.7:88/member/sign/course/student/signList?"+
                "courseId="+courseId+
                "&current=" + current +
                "&size=" + size+
                "&status="+status+
                "&userId="+userId;
        // 请求头
        Headers headers = new Headers.Builder()
                .add("appId", Api.appId)
                .add("appSecret", Api.appSecret)
                .add("Accept", "application/json, text/plain, */*")
                .build();

        //请求组合创建
        Request request = new Request.Builder()
                .url(url)
                // 将请求头加至请求中
                .headers(headers)
                .get()
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            //发起请求，传入callback进行回调
            client.newCall(request).enqueue(callback);
        } catch (NetworkOnMainThreadException ex) {
            ex.printStackTrace();
        }
    }
    public final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            // 获取响应体的json串
            String body = Objects.requireNonNull(response.body()).string();
            Log.d("签到课程列表：", body);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    Type jsonType = new TypeToken<ResponseBody<Records2>>() {}.getType();
                    // 解析json串到自己封装的状态
                    dataResponseBody = gson.fromJson(body, jsonType);

                    if (dataResponseBody.getData() != null){
                        for (Records2Detail news : dataResponseBody.getData().getRecords()) {
                            adapter.add(news);
                        }
                        adapter.notifyDataSetChanged();

                    }else {
                        Toast.makeText(SignlistActivity.this,"课程未发起签到！", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                }
            });
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        userSignID = dataResponseBody.getData().getRecords().get(position).getUserSignId();
        finish();
    }
}