package com.example.myapplication.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

public class MessageActivity extends AppCompatActivity {

    private TextView etCollegeName2;
    private TextView etCourseName2;
    private TextView etCoursePhoto2;
    private TextView etIntroduce2;
    private TextView etEndTime2;
    private TextView etRealName2;
    private TextView etStartTime2;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        AddCourseActivity.etEndTime = findViewById(R.id.et_fiTime);
        AddCourseActivity.etStartTime = findViewById(R.id.et_stTime);
        AddCourseActivity.etCollegeName = findViewById(R.id.et_collegeName);
        AddCourseActivity.etCourseName =  findViewById(R.id.et_courseName);
        AddCourseActivity.etIntroduce = findViewById(R.id.et_introduce);
        AddCourseActivity.etRealName = findViewById(R.id.et_realName);
        AddCourseActivity.etCoursePhoto = findViewById(R.id.et_photo);

        AddData();

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void AddData(){
        etEndTime2 = findViewById(R.id.tx_fiTime2);
        etStartTime2 = findViewById(R.id.tx_stTime2);
        etCollegeName2 = findViewById(R.id.tx_collegeName2);
        etCourseName2 =  findViewById(R.id.tx_courseName2);
        etIntroduce2 = findViewById(R.id.tx_introduce2);
        etRealName2 = findViewById(R.id.tx_realName2);
        //etCoursePhoto2 = findViewById(R.id.et_photo2);

        if (AddCourseActivity.etCourseName.getText().toString().equals("Math")) {
            etEndTime2.setText(AddCourseActivity.etEndTime.getText());
            etStartTime2.setText(etStartTime2.getText());
            etCollegeName2.setText(AddCourseActivity.etCollegeName.getText());
            etCourseName2.setText(AddCourseActivity.etCourseName.getText());
            etIntroduce2.setText(AddCourseActivity.etIntroduce.getText());
            etRealName2.setText(AddCourseActivity.etRealName.getText());
        }
    }
}