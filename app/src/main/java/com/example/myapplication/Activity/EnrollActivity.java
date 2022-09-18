package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Activity.LoginActivity;
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

public class EnrollActivity extends AppCompatActivity implements View.OnClickListener{

    private final Gson gson = new Gson();
    private final String appId = "d44f6a157edc4815b907124907b98e63";
    private final String appSecret = "24416e30b926e08a54c7f93fded9670b769f3";
    private EditText etname;
    private EditText password;
    private EditText newpassword;
    private Boolean bPwdSwitch1 = false;
    private Boolean bPwdSwitch2 = false;
    private ImageView iv_pwd_switch1;
    private ImageView iv_pwd_switch2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        etname = findViewById(R.id.input_enroll_account);
        password =  findViewById(R.id.input_enroll_password);
        newpassword = findViewById(R.id.input_enroll_npassword);
        iv_pwd_switch1 = findViewById(R.id.iv_pwd_switch1);
        iv_pwd_switch2 = findViewById(R.id.iv_pwd_switch2);

        Button btenroll = findViewById(R.id.bt_enroll);

        iv_pwd_switch1.setOnClickListener(view -> {//第一眼睛
            bPwdSwitch1 = !bPwdSwitch1;
            if (bPwdSwitch1){
                iv_pwd_switch1.setImageResource(R.drawable.ic_baseline_visibility_24);
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else{
                iv_pwd_switch1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                password.setTypeface(Typeface.DEFAULT);
            }
        });


        iv_pwd_switch2.setOnClickListener(view -> {//第二眼睛
            bPwdSwitch2 = !bPwdSwitch2;
            if (bPwdSwitch2){
                iv_pwd_switch2.setImageResource(R.drawable.ic_baseline_visibility_24);
                newpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else{
                iv_pwd_switch2.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                newpassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                newpassword.setTypeface(Typeface.DEFAULT);
            }
        });

        //注册
        btenroll.setOnClickListener(view -> {
            String name = etname.getText().toString();
            String password1 = password.getText().toString();
            String password2 = newpassword.getText().toString();
            if (password1.equals(password2)) {
                post(name, password1);
                Toast.makeText(EnrollActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EnrollActivity.this, LoginActivity.class);
                startActivity(intent);
            }else {
                    Toast.makeText(EnrollActivity.this,"两次密码输入不一致,请重新输入",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void post(String username,String password){
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/sign/user/register";

            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", appId)
                    .add("appSecret", appSecret)
                    .add("Content-Type", "application/json")
                    .build();

            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("password", password);
            bodyMap.put("roleId", 0);
            bodyMap.put("userName", username);
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

    @Override
    public void onClick(View v) {

    }

    /**
     * http响应体的封装协议
     * @param <T> 泛型
     */
    public  class ResponseBody <T> {

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
