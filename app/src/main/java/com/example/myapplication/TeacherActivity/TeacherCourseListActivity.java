package com.example.myapplication.TeacherActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adapter.CollectionAdapter;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.JavaBean.Course;
import com.example.myapplication.JavaBean.Data;
import com.example.myapplication.JavaBean.Records;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//已添加的课程列表
public class TeacherCourseListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String COURSE_MESSAGE_STRING = "com.example.myapplication.Activity.COURSE_INFO";
    private CollectionAdapter adapter;
    private ListView lvNewsList;
    public static int courseId;
    private ResponseBody<Records> dataResponseBody;

    public TeacherCourseListActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        lvNewsList = findViewById(R.id.lv_news_list222);

        initData();
        lvNewsList.setOnItemClickListener(this);
    }

    private void initData() {
        List<Course> newsData = new ArrayList<>();
        adapter = new CollectionAdapter(TeacherCourseListActivity.this,
                R.layout.list_item, newsData);

        lvNewsList.setAdapter(adapter);
        getMyCourses(1,10);
    }

    public void getMyCourses(int current, int size) {
        // url路径
        String url = "http://47.107.52.7:88/member/sign/course/all?" +
                "current=" + current +
                "&size=" + size;
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
            Log.d("课程列表：", body);

            runOnUiThread(() -> {
                Gson gson = new Gson();
                Type jsonType = new TypeToken<ResponseBody<Records>>() {
                }.getType();
                // 解析json串到自己封装的状态
                dataResponseBody = gson.fromJson(body, jsonType);

                if (dataResponseBody.getData() != null) {
                    for (Course news : dataResponseBody.getData().getRecords()) {
                        adapter.add(news);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(TeacherCourseListActivity.this, "还没有添加课程！",
                            Toast.LENGTH_SHORT).show();
                }

            });
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (LoginData.loginUser.getRoleId() == 0) {
            TeacherCourseListActivity.courseId = dataResponseBody.getData().getRecords().
                    get(position).getCourseId();
            int courseId3 = courseId;
            SelectCourse(courseId3, LoginData.loginUser.getId());
        } else if (LoginData.loginUser.getRoleId() == 1) {
            courseId = dataResponseBody.getData().getRecords().get(position).getCourseId();
            Intent intent = new Intent(TeacherCourseListActivity.this, TeacherCourseListDetailActivity.class);
            intent.putExtra(COURSE_MESSAGE_STRING, Integer.toString(courseId));
            startActivity(intent);
        }
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int alterCode = bundle.getInt("code");
            if (alterCode == 200) {
                Toast.makeText(TeacherCourseListActivity.this, "选课成功！",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(TeacherCourseListActivity.this, "请勿重复选课！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void SelectCourse(int courseId, int userId) {
        new Thread(() -> {
            // url路径
            String url = "http://47.107.52.7:88/member/sign/course/student/select?" +
                    "courseId=" + courseId +
                    "&userId=" + userId;
            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", Api.appId)
                    .add("appSecret", Api.appSecret)
                    .build();
            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("courseId", courseId);
            bodyMap.put("userId", userId);
            // 将Map转换为字符串类型加入请求体中
            String body = Api.gson.toJson(bodyMap);

            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody r = RequestBody.Companion.create(body, MEDIA_TYPE_JSON);
            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .post(r)
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                //发起请求，传入callback进行回调
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Type jsonType = new TypeToken<ResponseBody<Data>>() {
                        }.getType();
                        // 获取响应体的json串
                        assert response.body() != null;
                        String body = Objects.requireNonNull(response.body()).string();
                        Log.d("修改用户信息：", body);
                        // 解析json串到自己封装的状态
                        ResponseBody<Data> dataResponseBody = Api.gson.fromJson(body, jsonType);
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("code", dataResponseBody.getCode());
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}