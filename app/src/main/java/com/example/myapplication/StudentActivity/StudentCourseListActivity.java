package com.example.myapplication.StudentActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        // url??????
        String url = "http://47.107.52.7:88/member/sign/course/student?" +
                "current=" + current +
                "&size=" + size +
                "&userId=" + userId;
        // ?????????
        Headers headers = new Headers.Builder()
                .add("appId", Api.appId)
                .add("appSecret", Api.appSecret)
                .add("Accept", "application/json, text/plain, */*")
                .build();

        //??????????????????
        Request request = new Request.Builder()
                .url(url)
                // ???????????????????????????
                .headers(headers)
                .get()
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            //?????????????????????callback????????????
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

            // ??????????????????json???
            String body = Objects.requireNonNull(response.body()).string();
            Log.d("?????????????????????", body);

            runOnUiThread(() -> {
                Gson gson = new Gson();
                Type jsonType = new TypeToken<ResponseBody<Records>>() {
                }.getType();
                // ??????json???????????????????????????
                dataResponseBody = gson.fromJson(body, jsonType);

                if (dataResponseBody.getData() != null) {
                    for (Course news : dataResponseBody.getData().getRecords()) {
                        adapter2.add(news);
                    }
                    adapter2.notifyDataSetChanged();
                } else {
                    Toast.makeText(StudentCourseListActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    finish();
                }

            });
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        courseId = dataResponseBody.getData().getRecords().get(position).getCourseId();
        if (flag) {
            startActivity(new Intent(StudentCourseListActivity.this, SignListActivity.class));
            finish();
        } else {
            Intent intent = new Intent(StudentCourseListActivity.this, StudentCourseListDetailActivity.class);
            intent.putExtra(STUDENT_COURSE_MESSAGE_STRING, Integer.toString(courseId));
            startActivity(intent);
            finish();
        }
    }
}