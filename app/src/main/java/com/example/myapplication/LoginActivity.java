package com.example.myapplication;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Boolean bPwdSwitch = false;
    private EditText etPwd;//密码
    private EditText etAcc;
    private CheckBox cbRememberpwd;
    private TextView tvenroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPwd = findViewById(R.id.et_pwd);
        etAcc = findViewById(R.id.et_acc);
        cbRememberpwd = findViewById(R.id.cb_remember_pwd);

        Button btlogin = findViewById(R.id.bt_login);
        btlogin.setOnClickListener(this);

        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        etPwd = findViewById(R.id.et_pwd);

        String spFileName = getResources().getString(R.string.share_perferences_file_name);
        String accountKey = getResources().getString(R.string.login_account_name);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememberPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);

        String account = spFile.getString(accountKey,null);
        String password = spFile.getString(passwordKey,null);
        Boolean rememberPassword = spFile.getBoolean(rememberPasswordKey,false);

        if (account != null && !TextUtils.isEmpty(account)){
            etAcc.setText(account);
        }
        if (password != null && !TextUtils.isEmpty(password)){
            etPwd.setText(password);
        }
        cbRememberpwd.setChecked(rememberPassword);


        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bPwdSwitch = !bPwdSwitch;
                if (bPwdSwitch){
                    ivPwdSwitch.setImageResource(R.drawable.ic_baseline_visibility_24);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    ivPwdSwitch.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    etPwd.setTypeface(Typeface.DEFAULT);
                }
            }
        });

        //跳转
        tvenroll = findViewById(R.id.enroll);
        tvenroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, EnrollActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        String spFileName = getResources().getString(R.string.share_perferences_file_name);
        String accountKey = getResources().getString(R.string.login_account_name);
        String passwordKey = getResources().getString(R.string.login_password);
        String rememberPasswordKey = getResources().getString(R.string.login_remember_password);
        SharedPreferences spFile = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spFile.edit();
        if(cbRememberpwd.isChecked()){
            String password = etPwd.getText().toString();
            String account = etAcc.getText().toString();
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


}