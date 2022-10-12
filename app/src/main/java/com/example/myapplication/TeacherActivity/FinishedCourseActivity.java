package com.example.myapplication.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapter.FinishedCourseAdapter;
import com.example.myapplication.Adapter.UnfinishCourseAdapter;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.StudentActivity.StudentSignInActivity;
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

public class FinishedCourseActivity extends AppCompatActivity {

    private FinishedCourseAdapter adapter4;
    private List<Course> newsData4;
    private ListView lvNewsList4;
    public static int courseId;
    private ResponseBody<Records> dataResponseBody;

    public FinishedCourseActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_course);
        lvNewsList4 = findViewById(R.id.lv_news_list55);

        initData();

    }

    private void initData() {
        newsData4 = new ArrayList<>();
        adapter4 = new FinishedCourseAdapter(FinishedCourseActivity.this,
                R.layout.list_item4, newsData4);

        lvNewsList4.setAdapter(adapter4);
        MyCourses(1,5, LoginData.loginUser.getId());

    }


    public void MyCourses(int current, int size,int userId) {
        // url路径
        String url = "http://47.107.52.7:88/member/sign/course/teacher/finished?"+
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

                    if (dataResponseBody.getData()!=null){
                        for (Course news:dataResponseBody.getData().getRecords()) {
                            adapter4.add(news);
                        }
                        adapter4.notifyDataSetChanged();
                    }else {
                        Toast.makeText(FinishedCourseActivity.this,"未有已结课课程！", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    };
}