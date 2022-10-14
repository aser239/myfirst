package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Adapter.CollectionAdapter;
import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.TeacherActivity.TeacherCourseListActivity;
import com.example.myapplication.TeacherActivity.UnfinishedCourseActivity;
import com.example.myapplication.javaBean.CourseDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//未结课程的详情
public class MessageActivity extends AppCompatActivity {

    private TextView etCollegeName2;
    private TextView etCourseName2;
    private TextView etCoursePhoto2;
    private TextView etIntroduce2;
    private TextView etEndTime2;
    private TextView etRealName2;
    private TextView etStartTime2;
    private TextView etId;
    private TextView etChoose;
    private TextView etUserName;
    private TextView etCreateTime;
    private Button delete;
    private Button btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        int info = Integer.parseInt(intent.getStringExtra(UnfinishedCourseActivity.UNFINISHED_MESSAGE_STRING));
        Detail(info, LoginData.loginUser.getId());

    }

    public void init() {

        etCollegeName2 = findViewById(R.id.tx_collegeName22);
        etCourseName2 = findViewById(R.id.tx_courseName22);
        etCoursePhoto2 = findViewById(R.id.tx_photo);
        etIntroduce2 = findViewById(R.id.tx_introduce22);
        etEndTime2 = findViewById(R.id.tx_fiTime22);
        etRealName2 = findViewById(R.id.tx_realName2);
        etStartTime2 = findViewById(R.id.tx_stTime2);
        etId = findViewById(R.id.tx_id);
        etChoose = findViewById(R.id.tx_choose);
        etUserName = findViewById(R.id.tx_userName);
        etCreateTime = findViewById(R.id.tx_CreateTime);
        delete = findViewById(R.id.Delete);
        btBack = findViewById(R.id.addCourseReturnToCourse11);


        etCollegeName2.setText(CourseData.Detail.getCollegeName());
        etCourseName2.setText(CourseData.Detail.getCourseName());
        etCoursePhoto2.setText(CourseData.Detail.getCoursePhoto());
        etIntroduce2.setText(CourseData.Detail.getIntroduce());
        etEndTime2.setText(String.valueOf(CourseData.Detail.getEndTime()));
        etRealName2.setText(CourseData.Detail.getRealName());
        etStartTime2.setText(String.valueOf(CourseData.Detail.getStartTime()));
        etId.setText(String.valueOf(CourseData.Detail.getId()));
        etChoose.setText(String.valueOf(CourseData.Detail.isHasSelect()));
        etUserName.setText(CourseData.Detail.getUserName());
        etCreateTime.setText(String.valueOf(CourseData.Detail.getCreateTime()));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api.delete(CourseData.Detail.getId(), LoginData.loginUser.getId());
                finish();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void Detail(int courseId, int userId) {
        // url路径
        String url = "http://47.107.52.7:88/member/sign/course/detail?" +
                "courseId=" + courseId +
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    Type jsonType = new TypeToken<ResponseBody<CourseDetail>>() {
                    }.getType();
                    // 解析json串到自己封装的状态
                    ResponseBody<CourseDetail> dataResponseBody = gson.fromJson(body, jsonType);
                    Log.d("未结课程详情：", dataResponseBody.getData().toString());
                    CourseData.Detail = dataResponseBody.getData();
                    init();
                }
            });
        }
    };
}