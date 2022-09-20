package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.text.InputType;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;


public class EnrollActivity extends AppCompatActivity{

    private EditText etname;
    private EditText password;
    private EditText newpassword;
    private Boolean bPwdSwitch1 = false;
    private Boolean bPwdSwitch2 = false;
    private ImageView iv_pwd_switch1;
    private ImageView iv_pwd_switch2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        etname = findViewById(R.id.input_enroll_username);
        password =  findViewById(R.id.input_enroll_password);
        newpassword = findViewById(R.id.input_enroll_npassword);
        iv_pwd_switch1 = findViewById(R.id.iv_pwd_switch1);
        iv_pwd_switch2 = findViewById(R.id.iv_pwd_switch2);

        Button btenroll = findViewById(R.id.bt_enroll);

        iv_pwd_switch1.setOnClickListener(view -> {//第一眼睛
            bPwdSwitch1 = !bPwdSwitch1;
            if (bPwdSwitch1){
                iv_pwd_switch1.setImageResource(R.drawable.ic_baseline_visibility_24);
                password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else{
                iv_pwd_switch1.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                password.setTypeface(Typeface.DEFAULT);
            }
        });


        iv_pwd_switch2.setOnClickListener(view -> {//第二眼睛
            bPwdSwitch2 = !bPwdSwitch2;
            if (bPwdSwitch2){
                iv_pwd_switch2.setImageResource(R.drawable.ic_baseline_visibility_24);
                newpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else{
                iv_pwd_switch2.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                newpassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                newpassword.setTypeface(Typeface.DEFAULT);
            }
        });

        //注册
        btenroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etname.getText().toString();
                String password1 = password.getText().toString();
                String password2 = newpassword.getText().toString();

                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(password1)||TextUtils.isEmpty(password2)){
                    Toast.makeText(EnrollActivity.this,"输入不能为空！",Toast.LENGTH_SHORT).show();
                }else if (password1.equals(password2)){
                    Api.enroll(name, password1);
                    Toast.makeText(EnrollActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EnrollActivity.this, LoginActivity.class);
                    startActivity(intent);
                    }else {
                        Toast.makeText(EnrollActivity.this,"两次密码输入不一致,请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }
        });

    }

}
