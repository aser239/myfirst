package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.R;

public class PersonInfoActivity extends AppCompatActivity {
    private TextView tv_id;
    private TextView tv_username;
    private TextView tv_realName;
    private TextView tv_idNumber;
    private TextView tv_gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);

        init();
        initData();
    }

    private void init() {
        Button login_exit = findViewById(R.id.login_exit);
        login_exit.setOnClickListener(v -> startActivity(new Intent(PersonInfoActivity.this,
                LoginActivity.class)));

        ImageView personInfo_backward = findViewById(R.id.iv_backward);
        personInfo_backward.setOnClickListener(v -> startActivity(new Intent(PersonInfoActivity.this,
                MainActivity.class)));

        tv_id = findViewById(R.id.tv_id_info);
        tv_username = findViewById(R.id.tv_username_info);
        tv_realName = findViewById(R.id.tv_realName_info);
        tv_idNumber = findViewById(R.id.tv_idNumber_info);
        tv_gender = findViewById(R.id.tv_gender_info);
    }

    private void initData(){
        final boolean is_man = true;
        tv_id.setText(String.valueOf(LoginData.loginUser.getId()));
        tv_username.setText(LoginData.loginUser.getUsername());
        tv_realName.setText(LoginData.loginUser.getRealName());
        tv_idNumber.setText(String.valueOf(LoginData.loginUser.getIdNumber()));
        //if(LoginData.loginUser.get)
    }
}