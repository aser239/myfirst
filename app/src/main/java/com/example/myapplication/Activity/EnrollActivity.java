package com.example.myapplication.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.JavaBean.Data;
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

public class EnrollActivity extends AppCompatActivity {
    private EditText etName;
    private EditText password;
    private EditText newPassword;
    private Boolean bPwdSwitch1 = false;
    private Boolean bPwdSwitch2 = false;
    private ImageView iv_pwd_switch1;
    private ImageView iv_pwd_switch2;
    private RadioButton rbt_student;
    private RadioButton rbt_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        Button btEnroll = findViewById(R.id.bt_enroll);
        etName = findViewById(R.id.input_enroll_username);
        password = findViewById(R.id.input_enroll_password);
        newPassword = findViewById(R.id.input_enroll_nPassword);
        iv_pwd_switch1 = findViewById(R.id.iv_pwd_switch1);
        iv_pwd_switch2 = findViewById(R.id.iv_pwd_switch2);
        rbt_student = findViewById(R.id.role_student);
        rbt_teacher = findViewById(R.id.role_teacher);

        iv_pwd_switch1.setOnClickListener(view -> {  //第一眼睛
            bPwdSwitch1 = !bPwdSwitch1;
            if (bPwdSwitch1) {
                iv_pwd_switch1.setImageResource(R.drawable.ic_baseline_visibility_24);
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                iv_pwd_switch1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                password.setTypeface(Typeface.DEFAULT);
            }
        });
        iv_pwd_switch2.setOnClickListener(view -> {  //第二眼睛
            bPwdSwitch2 = !bPwdSwitch2;
            if (bPwdSwitch2) {
                iv_pwd_switch2.setImageResource(R.drawable.ic_baseline_visibility_24);
                newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                iv_pwd_switch2.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                newPassword.setTypeface(Typeface.DEFAULT);
            }
        });

        //注册
        btEnroll.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String password1 = password.getText().toString();
            String password2 = newPassword.getText().toString();
            int tempRoleId = -1;
            if (rbt_student.isChecked()) {
                tempRoleId = 0;
            } else if (rbt_teacher.isChecked()) {
                tempRoleId = 1;
            }
            int roleId = tempRoleId;
            if (tempRoleId == -1) {
                roleId = 0;
            }
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password1) || TextUtils.isEmpty(password2)) {
                Toast.makeText(EnrollActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
            } else if (password1.equals(password2)) {
                Enroll(name, roleId, password1);
            } else {
                Toast.makeText(EnrollActivity.this, "两次密码输入不一致,请重新输入",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //跳转登录
        TextView tvLogin = findViewById(R.id.login);
        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(EnrollActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        });
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int enrollCode = bundle.getInt("code");
            if (enrollCode == 200) {
                Toast.makeText(EnrollActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EnrollActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(EnrollActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void Enroll(String username, int roleId, String password) {
        new Thread(() -> {
            // url路径
            String url = "http://47.107.52.7:88/member/sign/user/register";
            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", Api.appId)
                    .add("appSecret", Api.appSecret)
                    .add("Content-Type", "application/json")
                    .build();
            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("password", password);
            bodyMap.put("roleId", roleId);
            bodyMap.put("userName", username);
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
                        String body = Objects.requireNonNull(response.body()).string();
                        Log.d("注册信息：", body);
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