package com.example.myapplication.TeacherActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

//老师端界面
public class TeacherCourseActivity extends AppCompatActivity {

    private Button addCourse;
    private Button unfinishedCourse;
    private Button finishedCourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_course_teacher);

        addCourse = findViewById(R.id.enterTeacherAddCourse);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherCourseActivity.this, AddCourseActivity.class));
            }
        });

        unfinishedCourse = findViewById(R.id.enterTeacherHaveNotClassList);
        unfinishedCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherCourseActivity.this,UnfinishedCourseActivity.class));
            }
        });

        finishedCourse = findViewById(R.id.enterTeacherHaveClassList);
        finishedCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherCourseActivity.this,FinishedCourseActivity.class));
            }
        });
    }
}