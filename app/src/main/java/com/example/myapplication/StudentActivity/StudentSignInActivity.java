package com.example.myapplication.StudentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Activity.SignlistActivity;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StudentSignInActivity extends AppCompatActivity {


    private EditText tv_sign_in_id;
    private EditText SignId;
    private EditText tv_sign_in_psw;
    private Button bt_sign_in;
    private Button btBack;
    private Button btboo;

    private final Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tv_sign_in_psw = findViewById(R.id.tv_sign_in_psw);
        bt_sign_in = findViewById(R.id.bt_sign_in);
        btBack = findViewById(R.id.Back4);

        bt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_sign_in_psw.getText().toString().equals("")){
                    Toast.makeText(StudentSignInActivity.this,"签到码或者签到表主键id不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    int signcode = Integer.parseInt(tv_sign_in_psw.getText().toString());
                    //int signid = Integer.parseInt(SignId.getText().toString());
                    s_SignIn(signcode, LoginData.loginUser.getId(), SignlistActivity.userSignID);
                }

            }
        });

        Button teaSign = findViewById(R.id.bt_enterUserCenter33);
        teaSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentCourseListActivity.flag =true;
                startActivity(new Intent(StudentSignInActivity.this, StudentCourseListActivity.class));
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void s_SignIn(int signCode, int userId, int userSignId){
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

            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .post(RequestBody.create(MEDIA_TYPE_JSON, body))
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                //发起请求，传入callback进行回调
                client.newCall(request).enqueue(callback);
            }catch (NetworkOnMainThreadException ex){
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
            Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
            // 获取响应体的json串
            String body = response.body().string();
            Log.d("info", body);
            // 解析json串到自己封装的状态
            ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);
            Log.d("info", dataResponseBody.toString());
        }
    };

    /**
     * http响应体的封装协议
     * @param <T> 泛型
     */
    public static class ResponseBody <T> {

        /**
         * 业务响应码
         */
        private int code;
        /**
         * 响应提示信息
         */
        private String msg;
        /**
         * 响应数据
         */
        private T data;

        public ResponseBody(){}

        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
        public T getData() {
            return data;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseBody{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

}
