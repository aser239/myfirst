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
import com.example.myapplication.JavaBean.Person;
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

        //????????????
        TextView tvEnroll = findViewById(R.id.enroll);
        tvEnroll.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, EnrollActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
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
        //????????????
        Read_pwd();

        Button btLogin = findViewById(R.id.bt_login);
        btLogin.setOnClickListener(view -> {
            String username = etUser.getText().toString();
            String password = etPwd.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
            } else {
                Login(username, password);
            }
        });
    }

    //????????????
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
        } else {
            editor.remove(accountKey);
            editor.remove(passwordKey);
            editor.remove(rememberPasswordKey);
        }
        editor.apply();
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
                case "????????????":
                    Toast.makeText(LoginActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                    break;
                case "???????????????????????????":
                    Toast.makeText(LoginActivity.this, "??????????????????????????????",
                            Toast.LENGTH_SHORT).show();
                    break;
                case "????????????":
                    Toast.makeText(LoginActivity.this, "???????????????",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(LoginActivity.this, "???????????????",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void Login(String username, String password) {
        new Thread(() -> {
            // url??????
            String url = "http://47.107.52.7:88/member/sign/user/login";
            // ?????????
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", Api.appId)
                    .add("appSecret", Api.appSecret)
                    .build();
            // ?????????
            // PS.??????????????????????????????????????????????????????????????????fastjson???????????????json???
            FormBody.Builder params = new FormBody.Builder();
            params.add("username", username); //??????url??????
            params.add("password", password); //??????url??????
            //??????????????????
            Request request = new Request.Builder()
                    .url(url)
                    // ???????????????????????????
                    .headers(headers)
                    .post(params.build())
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                //?????????????????????callback????????????
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Type jsonType = new TypeToken<ResponseBody<Person>>() {
                        }.getType();
                        // ??????????????????json???
                        String body = Objects.requireNonNull(response.body()).string();
                        Log.d("???????????????", body);
                        // ??????json???????????????????????????
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