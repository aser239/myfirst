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
    private TextView tv_collegeName;
    private TextView tv_phone;
    private TextView tv_inSchoolTime;
    private TextView tv_email;

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
        tv_collegeName = findViewById(R.id.tv_collegeName_info);
        tv_phone = findViewById(R.id.tv_phone_info);
        tv_inSchoolTime = findViewById(R.id.tv_inSchoolTime_info);
        tv_email = findViewById(R.id.tv_email_info);
    }

    private void initData() {
        final boolean is_man = true;  //判断是否为男性
        tv_id.setText(String.valueOf(LoginData.loginUser.getId()));
        tv_username.setText(LoginData.loginUser.getUsername());
        tv_realName.setText(LoginData.loginUser.getRealName());
        tv_idNumber.setText(String.valueOf(LoginData.loginUser.getIdNumber()));
        if (LoginData.loginUser.getGender() == is_man) {
            tv_gender.setText("男");
        } else {
            tv_gender.setText("女");
        }
        tv_collegeName.setText(LoginData.loginUser.getCollegeName());
        tv_phone.setText(LoginData.loginUser.getPhone());
        String tempStringDate = String.valueOf(LoginData.loginUser.getInSchoolTime());
        String realStringDate = tempStringDate.substring(0, 4) + "-" + tempStringDate.substring(4, 6) + "-" +
                tempStringDate.substring(6, 8);
        tv_inSchoolTime.setText(realStringDate);
        tv_email.setText(LoginData.loginUser.getEmail());
    }
}