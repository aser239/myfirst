package com.example.myapplication.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.R;
import com.example.myapplication.StudentActivity.StudentCourseActivity;
import com.example.myapplication.StudentActivity.StudentListActivity;
import com.example.myapplication.StudentActivity.StudentSignInActivity;
import com.example.myapplication.TeacherActivity.SignInformationActivity;
import com.example.myapplication.TeacherActivity.TeacherCourseActivity;
import com.example.myapplication.TeacherActivity.TeacherSignInActivity;

public class MainActivity extends AppCompatActivity {
    private Button course;
    private Button Sign;
    private Button btboo;
    private Button teaSign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        course = findViewById(R.id.bt_enterCourse);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginData.loginUser.getRoleId() == 0){
                    startActivity(new Intent(MainActivity.this, StudentCourseActivity.class));
                }else if (LoginData.loginUser.getRoleId() == 1){
                    startActivity(new Intent(MainActivity.this, TeacherCourseActivity.class));
                }
            }
        });

        Sign = findViewById(R.id.bt_enterSignIn);
        Sign.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (LoginData.loginUser.getRoleId() == 0){
                    startActivity(new Intent(MainActivity.this, StudentSignInActivity.class));
                }else if (LoginData.loginUser.getRoleId() == 1){
                    startActivity(new Intent(MainActivity.this, TeacherSignInActivity.class));
                }
            }
        });

        btboo = findViewById(R.id.bt_enterUserCenter22222);
        btboo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StudentListActivity.class));
            }
        });

        teaSign = findViewById(R.id.bt_enterUserCenter333);
        teaSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignInformationActivity.class));
            }
        });

    }
}