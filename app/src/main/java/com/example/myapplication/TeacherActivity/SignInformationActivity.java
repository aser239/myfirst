package com.example.myapplication.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Data.SignInformationData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.javaBean.Person;
import com.example.myapplication.javaBean.SignInformation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_information);

        Button Sign = findViewById(R.id.bt_enterSignIn22);
        Sign.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInformationActivity.this, TeacherSignInActivity.class));
            }
        });

        page(CourseData.Detail.getId(), LoginData.loginUser.getId());

    }

    public static void page(int courseId, int userId) {
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/sign/course/teacher/page?"+"courseId="+courseId+"&userId="+userId;

            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", Api.appId)
                    .add("appSecret", Api.appSecret)
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
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Type jsonType = new TypeToken<ResponseBody<SignInformation>>() {
                        }.getType();
                        // 获取响应体的json串
                        String body = response.body().string();
                        Log.d("info", body);
                        // 解析json串到自己封装的状态
                        Gson gson = new Gson();
                        ResponseBody<SignInformation> dataResponseBody = gson.fromJson(body, jsonType);
                        SignInformationData.Information = dataResponseBody.getData();
                        Log.d("info", dataResponseBody.toString());
                    }
                });
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}