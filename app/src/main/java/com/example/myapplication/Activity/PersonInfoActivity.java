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
import com.squareup.picasso.Picasso;

public class PersonInfoActivity extends AppCompatActivity {
    public static final String MESSAGE_STRING = "com.example.myapplication.Activity.PERSON_INFO";
    private TextView tv_id;
    private TextView tv_username;
    private TextView tv_realName;
    private TextView tv_idNumber;
    private TextView tv_gender;
    private TextView tv_collegeName;
    private TextView tv_phone;
    private TextView tv_inSchoolTime;
    private TextView tv_email;
    private ImageView iv_avatar;
    public static boolean isClickAvatar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);

        Init();
        InitData();
    }

    private void Init() {
        Button login_exit = findViewById(R.id.login_exit);
        login_exit.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        ImageView personInfo_backward = findViewById(R.id.iv_backward);
        personInfo_backward.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
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
        iv_avatar = findViewById(R.id.ri_avatar);

        ImageView iv_avatar = findViewById(R.id.iv_avatar_arrowRight);
        iv_avatar.setOnClickListener(v -> {
            PersonInfoActivity.isClickAvatar = true;
            Intent intent = new Intent(PersonInfoActivity.this, UploadActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        ImageView iv_realName = findViewById(R.id.iv_arrow_right_realName);
        iv_realName.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, AlterActivity.class);
            intent.putExtra(MESSAGE_STRING, "姓名");
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        ImageView iv_idNumber = findViewById(R.id.iv_arrow_right_idNumber);
        iv_idNumber.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, AlterActivity.class);
            intent.putExtra(MESSAGE_STRING, "学号");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        ImageView iv_gender = findViewById(R.id.iv_arrow_right_gender);
        iv_gender.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, AlterActivity.class);
            intent.putExtra(MESSAGE_STRING, "性别");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        ImageView iv_collageName = findViewById(R.id.iv_arrow_right_collegeName);
        iv_collageName.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, AlterActivity.class);
            intent.putExtra(MESSAGE_STRING, "院校");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        ImageView iv_phone = findViewById(R.id.iv_arrow_right_phone);
        iv_phone.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, AlterActivity.class);
            intent.putExtra(MESSAGE_STRING, "手机号");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        ImageView iv_inSchoolTime = findViewById(R.id.iv_arrow_right_inSchoolTime);
        iv_inSchoolTime.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, AlterActivity.class);
            intent.putExtra(MESSAGE_STRING, "入学时间");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
        ImageView iv_email = findViewById(R.id.iv_arrow_right_email);
        iv_email.setOnClickListener(v -> {
            Intent intent = new Intent(PersonInfoActivity.this, AlterActivity.class);
            intent.putExtra(MESSAGE_STRING, "邮箱");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void InitData() {
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

        if (LoginData.loginUser.getAvatar() != null) {
            Picasso.get().load(LoginData.loginUser.getAvatar()).into(iv_avatar);
        }
        tv_id.setText(String.valueOf(LoginData.loginUser.getId()));
        tv_username.setText(LoginData.loginUser.getUsername());
        tv_realName.setText(LoginData.loginUser.getRealName());
        tv_idNumber.setText(String.valueOf(LoginData.loginUser.getIdNumber()));

        if (LoginData.loginUser.getGender()) {
            tv_gender.setText("男");
        } else {
            tv_gender.setText("女");
        }
        tv_collegeName.setText(LoginData.loginUser.getCollegeName());
        tv_phone.setText(LoginData.loginUser.getPhone());
        String tempStringDate = String.valueOf(LoginData.loginUser.getInSchoolTime());
        if (tempStringDate.equals("0")) {
            tv_inSchoolTime.setText("");
        } else {
            String realStringDate = tempStringDate.substring(0, 4) + "-" + tempStringDate.substring(4, 6)
                    + "-" + tempStringDate.substring(6, 8);
            tv_inSchoolTime.setText(realStringDate);
        }
        tv_email.setText(LoginData.loginUser.getEmail());
    }
}
