package com.example.myapplication.StudentActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.TeacherActivity.TeacherCourseListActivity;

//学生
public class StudentCourseActivity extends AppCompatActivity {

    private Button btSelectCourse;
    private Button btStuCourseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_course_student);

        btSelectCourse = findViewById(R.id.enterSelectCourse111);
        btStuCourseList = findViewById(R.id.enterCourseList111);

        btSelectCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(StudentCourseActivity.this, TeacherCourseListActivity.class));
            }
        });

        btStuCourseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentCourseActivity.this, StudentCourseListActivity.class));
            }
        });
    }


}