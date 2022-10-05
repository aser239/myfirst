package com.example.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.MsgData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {
    private Boolean bPwdSwitch = false;
    private EditText etPwd;
    private EditText etUser;
    private CheckBox cbRememberPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPwd = findViewById(R.id.et_pwd);
        etUser = findViewById(R.id.et_user);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);

        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        etPwd = findViewById(R.id.et_pwd);

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

        cbRememberPwd.setOnClickListener(v -> RememberPwd(etUser.getText().toString(),
                etPwd.getText().toString()));
        //读取密码
        Read_pwd();

        Button btLogin = findViewById(R.id.bt_login);
        btLogin.setOnClickListener(view -> {
            String username = etUser.getText().toString();
            String password = etPwd.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
            }
            Api.Login(username, password);

            try {
                Thread.sleep(350);
                String msg = MsgData.loginMsgData.getMsg();
                switch (msg) {
                    case "登录成功":
                        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
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
            } catch (Exception e) {
                e.printStackTrace();
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
}