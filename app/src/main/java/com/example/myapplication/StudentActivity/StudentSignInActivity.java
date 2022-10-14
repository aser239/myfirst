package com.example.myapplication.StudentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.SignListActivity;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
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

public class StudentSignInActivity extends AppCompatActivity {
    private EditText tv_sign_in_psw;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tv_sign_in_psw = findViewById(R.id.tv_sign_in_psw);
        Button bt_sign_in = findViewById(R.id.bt_sign_in);
        Button btBack = findViewById(R.id.Back4);

        bt_sign_in.setOnClickListener(view -> {
            if (tv_sign_in_psw.getText().toString().equals("")) {
                Toast.makeText(StudentSignInActivity.this, "签到码不能为空！",
                        Toast.LENGTH_SHORT).show();
            } else {
                int signCode = Integer.parseInt(tv_sign_in_psw.getText().toString());
                s_SignIn(signCode, LoginData.loginUser.getId(), SignListActivity.userSignID);
            }
        });

        Button teaSign = findViewById(R.id.bt_enterUserCenter33);
        teaSign.setOnClickListener(v -> {
            StudentCourseListActivity.flag = true;
            startActivity(new Intent(StudentSignInActivity.this,
                    StudentCourseListActivity.class));
        });

        btBack.setOnClickListener(v -> finish());
    }


    private void s_SignIn(int signCode, int userId, int userSignId) {
        new Thread(() -> {
            // url路径
            String url = "http://47.107.52.7:88/member/sign/course/student/sign";
            // 请求头
            Headers headers = new Headers.Builder()
                    .add("appId", Api.appId)
                    .add("appSecret", Api.appSecret)
                    .add("Accept", "application/json, text/plain, */*")
                    .build();

            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("signCode", signCode);
            bodyMap.put("userId", userId);
            bodyMap.put("userSignId", userSignId);
            // 将Map转换为字符串类型加入请求体中
            String body = gson.toJson(bodyMap);

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
                client.newCall(request).enqueue(callback);
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * 回调
     */
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            //TODO 请求失败处理
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO 请求成功处理
            Type jsonType = new TypeToken<ResponseBody<Object>>() {
            }.getType();
            // 获取响应体的json串
            String body = Objects.requireNonNull(response.body()).string();
            Log.d("info", body);
            // 解析json串到自己封装的状态
            ResponseBody<Object> dataResponseBody = gson.fromJson(body, jsonType);
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("msg", dataResponseBody.getMsg());
            bundle.putInt("code", dataResponseBody.getCode());
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String info = bundle.getString("msg");
            int code = bundle.getInt("code");
            if (info.equals("签到时间不在规定范围内")) {
                Toast.makeText(StudentSignInActivity.this, "签到时间不在规定范围内！",
                        Toast.LENGTH_SHORT).show();
            } else if (code == 200 & info.equals("")) {
                Toast.makeText(StudentSignInActivity.this, "签到成功！",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(StudentSignInActivity.this, "签到失败！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
}
