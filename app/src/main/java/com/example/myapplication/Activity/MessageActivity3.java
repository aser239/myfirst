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

import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.StudentActivity.StudentCourseListActivity;
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

//学生选课的详情
public class MessageActivity3 extends AppCompatActivity {

    private TextView etCollegeName;
    private TextView etCourseName;
    private TextView etCoursePhoto;
    private TextView etIntroduce;
    private TextView etEndTime;
    private TextView etRealName;
    private TextView etStartTime;
    private TextView etId;
    private TextView etChoose;
    private TextView etUserName;
    private TextView etCreateTime;
    private Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message3);

        Intent intent = getIntent();
        int info = Integer.parseInt(intent.getStringExtra(StudentCourseListActivity.STUDENTCOURSE_MESSAGE_STRING));
        Detail(info, LoginData.loginUser.getId());

    }

    public void init() {

        etCollegeName = findViewById(R.id.tx_collegeName44);
        etCourseName = findViewById(R.id.tx_courseName44);
        etCoursePhoto = findViewById(R.id.tx_photo44);
        etIntroduce = findViewById(R.id.tx_introduce44);
        etEndTime = findViewById(R.id.tx_fiTime44);
        etRealName = findViewById(R.id.tx_realName44);
        etStartTime = findViewById(R.id.tx_stTime44);
        etId = findViewById(R.id.tx_id44);
        etChoose = findViewById(R.id.tx_choose44);
        etUserName = findViewById(R.id.tx_userName44);
        etCreateTime = findViewById(R.id.tx_CreateTime44);
        quit = findViewById(R.id.Quit);


        etCollegeName.setText(CourseData.Detail.getCollegeName());
        etCourseName.setText(CourseData.Detail.getCourseName());
        etCoursePhoto.setText(CourseData.Detail.getCoursePhoto());
        etIntroduce.setText(CourseData.Detail.getIntroduce());
        etEndTime.setText(String.valueOf(CourseData.Detail.getEndTime()));
        etRealName.setText(CourseData.Detail.getRealName());
        etStartTime.setText(String.valueOf(CourseData.Detail.getStartTime()));
        etId.setText(String.valueOf(CourseData.Detail.getId()));
        etChoose.setText(String.valueOf(CourseData.Detail.isHasSelect()));
        etUserName.setText(CourseData.Detail.getUserName());
        etCreateTime.setText(String.valueOf(CourseData.Detail.getCreateTime()));

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api.quit(CourseData.Detail.getId(), LoginData.loginUser.getId());
                startActivity(new Intent(MessageActivity3.this, StudentCourseListActivity.class));
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
            Log.d("详情：", body);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    Type jsonType = new TypeToken<ResponseBody<CourseDetail>>() {
                    }.getType();
                    // 解析json串到自己封装的状态
                    ResponseBody<CourseDetail> dataResponseBody = gson.fromJson(body, jsonType);
                    Log.d("学生课程详情：", dataResponseBody.getData().toString());
                    CourseData.Detail = dataResponseBody.getData();
                    init();
                }
            });
        }
    };
}