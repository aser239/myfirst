package com.example.myapplication.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;
import com.example.myapplication.javaBean.Person;

public class LoginActivity extends AppCompatActivity{
    private Boolean bPwdSwitch = false;
    private EditText etPwd;//密码
    private EditText etUser;
    private CheckBox cbRememberpwd;
    private TextView tvenroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPwd = findViewById(R.id.et_pwd);
        etUser = findViewById(R.id.et_user);
        cbRememberpwd = findViewById(R.id.cb_remember_pwd);

        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        etPwd = findViewById(R.id.et_pwd);

        //读取密码
        read_pwd();

        ivPwdSwitch.setOnClickListener(view -> {
            bPwdSwitch = !bPwdSwitch;
            if (bPwdSwitch){
                ivPwdSwitch.setImageResource(R.drawable.ic_baseline_visibility_24);
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else{
                ivPwdSwitch.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                etPwd.setTypeface(Typeface.DEFAULT);
            }
        });

        //跳转注册
        tvenroll = findViewById(R.id.enroll);
        tvenroll.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, EnrollActivity.class);
            startActivity(intent);
        });

        Button btLogin = findViewById(R.id.bt_login);
        btLogin.setOnClickListener(view -> {
            String password = etPwd.getText().toString();
            String username = etUser.getText().toString();

            rememberPwd(username,password);
            Api.login(username,password);

            if (LoginData.loginUser!=null){
                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }else {
                Toast.makeText(LoginActivity.this, "用户不存在！", Toast.LENGTH_SHORT).show();
            }
        });
    }



    //记住密码
    public void rememberPwd(String account,String password){

        String spFileName = getResources().getString(R.string.share_perferences_file_name);
        String accountKey = getResources().getString(R.string.login_username);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememberPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spFile.edit();
        if(cbRememberpwd.isChecked()){
            editor.putString(accountKey , account);
            editor.putString(passwordKey , password);
            editor.putBoolean(rememberPasswordKey , true);
            editor.apply();
        }else {
            editor.remove(accountKey);
            editor.remove(passwordKey);
            editor.remove(rememberPasswordKey);
            editor.apply();
        }
    }


    public void read_pwd(){

        String spFileName = getResources().getString(R.string.share_perferences_file_name);
        String accountKey = getResources().getString(R.string.login_username);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememberPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);

        String account = spFile.getString(accountKey,null);
        String password = spFile.getString(passwordKey,null);
        Boolean rememberPassword = spFile.getBoolean(rememberPasswordKey,false);

        if (account != null && !TextUtils.isEmpty(account)){
            etUser.setText(account);
        }
        if (password != null && !TextUtils.isEmpty(password)){
            etPwd.setText(password);
        }
        cbRememberpwd.setChecked(rememberPassword);
    }


}