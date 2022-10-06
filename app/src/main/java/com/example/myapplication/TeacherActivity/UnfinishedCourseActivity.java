package com.example.myapplication.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.Activity.MessageActivity;
import com.example.myapplication.Adapter.CollectionAdapter;
import com.example.myapplication.Adapter.UnfinishCourseAdapter;
import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.javaBean.Course;
import com.example.myapplication.javaBean.Records;
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

public class UnfinishedCourseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public static final String UNFINISHED_MESSAGE_STRING = "com.example.myapplication.Activity.UNFINISHED_INFO";
    public static boolean UNFINISHED_MESSAGE_FLAG = false;
    private UnfinishCourseAdapter adapter3;
    private List<Course> newsData3;
    private ListView lvNewsList3;
    public static int courseId;
    private ResponseBody<Records> dataResponseBody;

    public UnfinishedCourseActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfinished);
        lvNewsList3 = findViewById(R.id.lv_news_list44);

        initData();
        lvNewsList3.setOnItemClickListener(this);

    }

    private void initData() {
        newsData3 = new ArrayList<>();
        adapter3 = new UnfinishCourseAdapter(UnfinishedCourseActivity.this,
                R.layout.list_item3, newsData3);

        lvNewsList3.setAdapter(adapter3);
        MyCourses(1,5, LoginData.loginUser.getId());

    }


    public void MyCourses(int current, int size,int userId) {
        // url路径
        String url = "http://47.107.52.7:88/member/sign/course/teacher/unfinished?"+
                "current=" + current +
                "&size=" + size+"&userId="+userId;
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
            Log.d("未结课程列表：", body);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    Type jsonType = new TypeToken<ResponseBody<Records>>() {}.getType();
                    // 解析json串到自己封装的状态
                    dataResponseBody = gson.fromJson(body, jsonType);

                    for (Course news:dataResponseBody.getData().getRecords()) {
                        adapter3.add(news);
                    }
                    adapter3.notifyDataSetChanged();
                }
            });
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        courseId = dataResponseBody.getData().getRecords().get(position).getCourseId();
        Intent intent = new Intent(UnfinishedCourseActivity.this, MessageActivity.class);
        intent.putExtra(UNFINISHED_MESSAGE_STRING,Integer.toString(courseId));
        UnfinishedCourseActivity.UNFINISHED_MESSAGE_FLAG = true;
        startActivity(intent);
    }
}