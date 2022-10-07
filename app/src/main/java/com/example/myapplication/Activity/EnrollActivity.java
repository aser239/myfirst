package com.example.myapplication.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;

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

        etName = findViewById(R.id.input_enroll_username);
        password = findViewById(R.id.input_enroll_password);
        newPassword = findViewById(R.id.input_enroll_nPassword);
        iv_pwd_switch1 = findViewById(R.id.iv_pwd_switch1);
        iv_pwd_switch2 = findViewById(R.id.iv_pwd_switch2);
        rbt_student = findViewById(R.id.role_student);
        rbt_teacher = findViewById(R.id.role_teacher);

        Button btEnroll = findViewById(R.id.bt_enroll);

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
                Api.Enroll(name, roleId, password1);
                Toast.makeText(EnrollActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EnrollActivity.this, LoginActivity.class);
                startActivity(intent);
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
}