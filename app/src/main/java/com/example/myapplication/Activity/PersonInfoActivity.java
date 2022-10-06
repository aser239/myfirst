package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.R;

public class PersonInfoActivity extends AppCompatActivity {
    public static final String Info_String = "com.example.Activity.Info";
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
        login_exit.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        tv_id = findViewById(R.id.tv_id_info);
        tv_username = findViewById(R.id.tv_username_info);
        tv_realName = findViewById(R.id.tv_realName_info);
        tv_idNumber = findViewById(R.id.tv_idNumber_info);
        tv_gender = findViewById(R.id.tv_gender_info);
        tv_collegeName = findViewById(R.id.tv_collegeName_info);
        tv_phone = findViewById(R.id.tv_phone_info);
        tv_inSchoolTime = findViewById(R.id.tv_inSchoolTime_info);
        tv_email = findViewById(R.id.tv_email_info);

        ImageView iv_name = findViewById(R.id.iv_arrow_right_realName);
        iv_name.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, AlterActivity.class);
            intent.putExtra(Info_String, "姓名");
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        TextView tv_avatar_title = findViewById(R.id.tv_avatar);
        tv_avatar_title.setText("头像");
        TextView tv_id_title = findViewById(R.id.tv_id);
        tv_id_title.setText("ID");
        TextView tv_username_title = findViewById(R.id.tv_username);
        tv_username_title.setText("用户名");
        TextView tv_realName_title = findViewById(R.id.tv_realName);
        tv_realName_title.setText("性名");
        TextView tv_idNumber_title = findViewById(R.id.tv_idNumber);
        tv_idNumber_title.setText("学号");
        TextView tv_gender_title = findViewById(R.id.tv_gender);
        tv_gender_title.setText("性别");
        TextView tv_collegeName_title = findViewById(R.id.tv_collegeName);
        tv_collegeName_title.setText("院校");
        TextView tv_phone_title = findViewById(R.id.tv_phone);
        tv_phone_title.setText("手机号");
        TextView tv_inSchoolTime_title = findViewById(R.id.tv_inSchoolTime);
        tv_inSchoolTime_title.setText("入学时间");
        TextView tv_email_title = findViewById(R.id.tv_email);
        tv_email_title.setText("邮箱");

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