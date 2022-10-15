package com.example.myapplication.TeacherActivity;

import android.annotation.SuppressLint;
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
import com.example.myapplication.JavaBean.CourseDetail;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//未结课程的详情
public class UnfinishedCourseDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfinishedcoursedetai);

        Intent intent = getIntent();
        int info = Integer.parseInt(intent.getStringExtra(UnfinishedCourseActivity.UNFINISHED_MESSAGE_STRING));
        Detail(info, LoginData.loginUser.getId());
    }

    public void init() {
        TextView etCollegeName2 = findViewById(R.id.tx_collegeName22);
        TextView etCourseName2 = findViewById(R.id.tx_courseName22);
        ImageView etCoursePhoto2 = findViewById(R.id.tx_photo);
        TextView etIntroduce2 = findViewById(R.id.tx_introduce22);
        TextView etEndTime2 = findViewById(R.id.tx_fiTime22);
        TextView etRealName2 = findViewById(R.id.tx_realName2);
        TextView etStartTime2 = findViewById(R.id.tx_stTime2);
        TextView etId = findViewById(R.id.tx_id);
        TextView etChoose = findViewById(R.id.tx_choose);
        TextView etUserName = findViewById(R.id.tx_userName);
        TextView etCreateTime = findViewById(R.id.tx_CreateTime);
        Button delete = findViewById(R.id.Delete);
        Button btBack = findViewById(R.id.addCourseReturnToCourse11);
        LinearLayout hasSelect = findViewById(R.id.hasSelect1);

        if (LoginData.loginUser.getRoleId() == 1) {
            hasSelect.setVisibility(View.GONE);
        } else {
            etChoose.setText(String.valueOf(CourseData.Detail.isHasSelect()));
            //hasSelect1.setVisibility(View.VISIBLE);
        }

        etCollegeName2.setText(CourseData.Detail.getCollegeName());
        etCourseName2.setText(CourseData.Detail.getCourseName());
        Picasso.get().load(CourseData.Detail.getCoursePhoto()).into(etCoursePhoto2);
        etIntroduce2.setText(CourseData.Detail.getIntroduce());
        etEndTime2.setText(getTimeStampString(CourseData.Detail.getEndTime()));
        etRealName2.setText(CourseData.Detail.getRealName());
        etStartTime2.setText(getTimeStampString(CourseData.Detail.getStartTime()));
        etId.setText(String.valueOf(CourseData.Detail.getId()));
        etUserName.setText(CourseData.Detail.getUserName());
        etCreateTime.setText(getTimeStampString(CourseData.Detail.getCreateTime()));

        delete.setOnClickListener(v -> {
            delete(CourseData.Detail.getId(), LoginData.loginUser.getId());
            finish();
        });

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
            runOnUiThread(() -> {
                Gson gson = new Gson();
                Type jsonType = new TypeToken<ResponseBody<CourseDetail>>() {
                }.getType();
                // 解析json串到自己封装的状态
                ResponseBody<CourseDetail> dataResponseBody = gson.fromJson(body, jsonType);
                Log.d("未结课程详情：", dataResponseBody.getData().toString());
                CourseData.Detail = dataResponseBody.getData();
                init();
            });
        }
    };

    public void delete(int courseId, int userId) {
        new Thread(() -> {
            // url路径
            String url = "http://47.107.52.7:88/member/sign/course/teacher?" + "courseId="
                    + courseId + "&userId=" + userId;
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
                    .delete()
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                //发起请求，传入callback进行回调
                client.newCall(request).enqueue(ResponseBody.callback);
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    //时间戳转换时间
    public static String getTimeStampString(long time) {
        Date date = new Date(time);
        // 格式化日期
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}