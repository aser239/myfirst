package com.example.myapplication.TeacherActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SelectSignInCourseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sign_in_course);

        Button btn = findViewById(R.id.bt_enterSignIn66);
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                UnfinishedCourseActivity.isSelectCourseSignIn = true;
                startActivity(new Intent(SelectSignInCourseActivity.this,
                        UnfinishedCourseActivity.class));
                finish();
            }
        });
    }
}
