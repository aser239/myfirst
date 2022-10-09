package com.example.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.javaBean.Person;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private Boolean bPwdSwitch = false;
    private EditText etPwd;
    private EditText etUser;
    private CheckBox cbRememberPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.et_user);
        etPwd = findViewById(R.id.et_pwd);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);

        //etPwd = findViewById(R.id.et_pwd);
        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        ivPwdSwitch.setOnClickListener(view -> {
            bPwdSwitch = !bPwdSwitch;
            if (bPwdSwitch) {
                ivPwdSwitch.setImageResource(R.drawable.ic_baseline_visibility_24);
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                ivPwdSwitch.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                etPwd.setTypeface(Typeface.DEFAULT);
            }
        });

        //跳转注册
        TextView tvEnroll = findViewById(R.id.enroll);
        tvEnroll.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, EnrollActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });

        View.OnFocusChangeListener etUserFocusChangedListener = (v, hasFocus) -> {
            if (hasFocus) {
                cbRememberPwd.setChecked(false);
            }
        };
        etUser.setOnFocusChangeListener(etUserFocusChangedListener);

        View.OnFocusChangeListener etPwdFocusChangedListener = (v, hasFocus) -> {
            if (hasFocus) {
                cbRememberPwd.setChecked(false);
            }
        };
        etPwd.setOnFocusChangeListener(etPwdFocusChangedListener);

        cbRememberPwd.setOnClickListener(v -> RememberPwd(etUser.getText().toString(),
                etPwd.getText().toString()));
        //读取密码
        Read_pwd();

        Button btLogin = findViewById(R.id.bt_login);
        btLogin.setOnClickListener(view -> {
            String username = etUser.getText().toString();
            String password = etPwd.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "账号和密码不能为空！", Toast.LENGTH_SHORT).show();
            } else {
                Login(username, password);
            }
        });
    }

    //记住密码
    public void RememberPwd(String account, String password) {
        String spFileName = getResources().getString(R.string.share_perferences_file_name);
        String accountKey = getResources().getString(R.string.login_username);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememberPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spFile.edit();
        if (cbRememberPwd.isChecked()) {
            editor.putString(accountKey, account);
            editor.putString(passwordKey, password);
            editor.putBoolean(rememberPasswordKey, true);
            editor.apply();
        } else {
            editor.remove(accountKey);
            editor.remove(passwordKey);
            editor.remove(rememberPasswordKey);
            editor.apply();
        }
    }

    public void Read_pwd() {
        String spFileName = getResources().getString(R.string.share_perferences_file_name);
        String accountKey = getResources().getString(R.string.login_username);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememberPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        String account = spFile.getString(accountKey, null);
        String password = spFile.getString(passwordKey, null);
        boolean rememberPassword = spFile.getBoolean(rememberPasswordKey, false);
        if (account != null && !TextUtils.isEmpty(account)) {
            etUser.setText(account);
        }
        if (password != null && !TextUtils.isEmpty(password)) {
            etPwd.setText(password);
        }
        cbRememberPwd.setChecked(rememberPassword);
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String loginMsg = bundle.getString("msg");
            System.out.println(loginMsg);
            switch (loginMsg) {
                case "登录成功":
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                    break;
                case "当前登录用户不存在":
                    Toast.makeText(LoginActivity.this, "当前登录用户不存在！",
                            Toast.LENGTH_SHORT).show();
                    break;
                case "密码错误":
                    Toast.makeText(LoginActivity.this, "密码错误！",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(LoginActivity.this, "登录失败！",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void Login(String username, String password) {
        new Thread(() -> {
            // url路径
            String url = "http://47.107.52.7:88/member/sign/user/login";
            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", Api.appId)
                    .add("appSecret", Api.appSecret)
                    .build();
            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            FormBody.Builder params = new FormBody.Builder();
            params.add("username", username); //添加url参数
            params.add("password", password); //添加url参数
            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .post(params.build())
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
                        Type jsonType = new TypeToken<ResponseBody<Person>>() {
                        }.getType();
                        // 获取响应体的json串
                        String body = Objects.requireNonNull(response.body()).string();
                        Log.d("info", body);
                        // 解析json串到自己封装的状态
                        ResponseBody<Person> dataResponseBody = Api.gson.fromJson(body, jsonType);
                        LoginData.loginUser = dataResponseBody.getData();
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("msg", dataResponseBody.getMsg());
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