package com.example.myapplication.StudentActivity;

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

import com.example.myapplication.Activity.MessageActivity3;
import com.example.myapplication.Activity.SignListActivity;
import com.example.myapplication.Adapter.StudentCourseAdapter;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.JavaBean.Course;
import com.example.myapplication.JavaBean.Records;
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

public class StudentCourseListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String STUDENT_COURSE_MESSAGE_STRING = "com.example.myapplication.Activity.STUDENT_COURSE_INFO";
    private StudentCourseAdapter adapter2;
    private ListView lvNewsList2;
    public static int courseId;
    private ResponseBody<Records> dataResponseBody;
    public static boolean flag = false;

    public StudentCourseListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_list);
        lvNewsList2 = findViewById(R.id.lv_news_list3);

        initData2();
        lvNewsList2.setOnItemClickListener(this);
    }

    private void initData2() {
        List<Course> newsData2 = new ArrayList<>();
        adapter2 = new StudentCourseAdapter(StudentCourseListActivity.this,
                R.layout.list_item2, newsData2);

        lvNewsList2.setAdapter(adapter2);
        getCourse(1, 10, LoginData.loginUser.getId());

    }

    public void getCourse(int current, int size, int userId) {
        // url路径
        String url = "http://47.107.52.7:88/member/sign/course/student?" +
                "current=" + current +
                "&size=" + size +
                "&userId=" + userId;
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
            Log.d("学生选课列表：", body);

            runOnUiThread(() -> {
                Gson gson = new Gson();
                Type jsonType = new TypeToken<ResponseBody<Records>>() {
                }.getType();
                // 解析json串到自己封装的状态
                dataResponseBody = gson.fromJson(body, jsonType);

                if (dataResponseBody.getData() != null) {
                    for (Course news : dataResponseBody.getData().getRecords()) {
                        adapter2.add(news);
                    }
                    adapter2.notifyDataSetChanged();
                } else {
                    Toast.makeText(StudentCourseListActivity.this, "还没有选课！", Toast.LENGTH_SHORT).show();
                    finish();
                }

            });
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        courseId = dataResponseBody.getData().getRecords().get(position).getCourseId();
        if (flag) {
            startActivity(new Intent(StudentCourseListActivity.this, SignListActivity.class));
            finish();
        } else {
            Intent intent = new Intent(StudentCourseListActivity.this, MessageActivity3.class);
            intent.putExtra(STUDENT_COURSE_MESSAGE_STRING, Integer.toString(courseId));
            startActivity(intent);
            finish();
        }
    }
}