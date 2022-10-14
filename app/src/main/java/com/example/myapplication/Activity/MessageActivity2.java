package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.TeacherActivity.TeacherCourseListActivity;
import com.example.myapplication.JavaBean.CourseDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//全部课程的详情
public class MessageActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);

        Intent intent = getIntent();
        int info = Integer.parseInt(intent.getStringExtra(TeacherCourseListActivity.COURSE_MESSAGE_STRING));
        Detail(info, LoginData.loginUser.getId());
    }

    public void init() {
        TextView etCollegeName = findViewById(R.id.tx_collegeName33);
        TextView etCourseName = findViewById(R.id.tx_courseName33);
        ImageView etCoursePhoto = findViewById(R.id.tx_photo33);
        TextView etIntroduce = findViewById(R.id.tx_introduce33);
        TextView etEndTime = findViewById(R.id.tx_fiTime33);
        TextView etRealName = findViewById(R.id.tx_realName33);
        TextView etStartTime = findViewById(R.id.tx_stTime33);
        TextView etId = findViewById(R.id.tx_id33);
        TextView etChoose = findViewById(R.id.tx_choose33);
        TextView etUserName = findViewById(R.id.tx_userName33);
        TextView etCreateTime = findViewById(R.id.tx_CreateTime33);
        Button btBack = findViewById(R.id.Back2);
        LinearLayout hasSelect = findViewById(R.id.hasSelect2);

        if (LoginData.loginUser.getRoleId() == 1) {
            hasSelect.setVisibility(View.GONE);
        } else {
            etChoose.setText(String.valueOf(CourseData.Detail.isHasSelect()));
            //hasSelect1.setVisibility(View.VISIBLE);
        }

        etCollegeName.setText(CourseData.Detail.getCollegeName());
        etCourseName.setText(CourseData.Detail.getCourseName());
        Picasso.get().load(CourseData.Detail.getCoursePhoto()).into(etCoursePhoto);
        etIntroduce.setText(CourseData.Detail.getIntroduce());
        etEndTime.setText(MessageActivity.getTimeStampString(CourseData.Detail.getEndTime()));
        etRealName.setText(CourseData.Detail.getRealName());
        etStartTime.setText(MessageActivity.getTimeStampString(CourseData.Detail.getStartTime()));
        etId.setText(String.valueOf(CourseData.Detail.getId()));
        etChoose.setText(String.valueOf(CourseData.Detail.isHasSelect()));
        etUserName.setText(CourseData.Detail.getUserName());
        etCreateTime.setText(MessageActivity.getTimeStampString(CourseData.Detail.getCreateTime()));

        btBack.setOnClickListener(v -> finish());
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

            runOnUiThread(() -> {
                Gson gson = new Gson();
                Type jsonType = new TypeToken<ResponseBody<CourseDetail>>() {
                }.getType();
                // 解析json串到自己封装的状态
                ResponseBody<CourseDetail> dataResponseBody = gson.fromJson(body, jsonType);
                Log.d("全部课程详情：", dataResponseBody.getData().toString());
                CourseData.Detail = dataResponseBody.getData();
                init();
            });
        }
    };
}