package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.Adapter.ClassAdapter;
import com.example.myapplication.R;
import com.example.myapplication.javaBean.Course;

public class GetCourseListActivity extends AppCompatActivity {
    private ClassAdapter adapter;

    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_course_list);

        initView();
    }

    private void initView(){
        mListView = findViewById(R.id.allCourse);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

}