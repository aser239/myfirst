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
import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.ViewHolder.CollectionViewModel;
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

//已添加的课程列表
public class TeacherCourseListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String COURSE_MESSAGE_STRING = "com.example.myapplication.Activity.COURSE_INFO";
    public static boolean COURSE_MESSAGE_FLAG = false;
    private CollectionAdapter adapter;
    private List<Course> newsData;
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
        newsData = new ArrayList<>();
        adapter = new CollectionAdapter(TeacherCourseListActivity.this,
                R.layout.list_item, newsData);

        lvNewsList.setAdapter(adapter);
        getMyCourses(1,5);

    }

    public void getMyCourses(int current, int size) {
        // url路径
        String url = "http://47.107.52.7:88/member/sign/course/all?"+
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

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    Type jsonType = new TypeToken<ResponseBody<Records>>() {}.getType();
                    // 解析json串到自己封装的状态
                    dataResponseBody = gson.fromJson(body, jsonType);
                    for (Course news:dataResponseBody.getData().getRecords()) {
                        adapter.add(news);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (LoginData.loginUser.getRoleId()==0){
            courseId = dataResponseBody.getData().getRecords().get(position).getCourseId();
            int courseId3 = courseId;
            System.out.println(courseId3);
            Api.SelectCourse(courseId3,LoginData.loginUser.getId());
        }else if (LoginData.loginUser.getRoleId()==1) {
            Log.d("课程列表：", dataResponseBody.getData().getRecords().get(position).toString());
            courseId = dataResponseBody.getData().getRecords().get(position).getCourseId();
            Intent intent = new Intent(TeacherCourseListActivity.this, MessageActivity.class);
            intent.putExtra(COURSE_MESSAGE_STRING,Integer.toString(courseId));
            TeacherCourseListActivity.COURSE_MESSAGE_FLAG = true;
            startActivity(intent);
        }
    }
}