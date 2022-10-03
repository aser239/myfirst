package com.example.myapplication.StudentActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.Adapter.StudentCourseAdapter;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.javaBean.Course;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class StudentCourseListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private StudentCourseAdapter mViewModel2;
    private StudentCourseAdapter adapter2;
    private List<Course> newsData2;
    private ListView lvNewsList2;

    public StudentCourseListActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_list);

        lvNewsList2 = findViewById(R.id.lv_news_list3);

        initData2();
    }

    private void initData2() {
        newsData2 = new ArrayList<>();
        adapter2 = new StudentCourseAdapter(StudentCourseListActivity.this,
                R.layout.list_item2, newsData2);

        lvNewsList2.setAdapter(adapter2);
        getCourse(1,1, LoginData.loginUser.getId());

    }

    public void getCourse(int current, int size,int userId) {
        // url路径
        String url = "http://47.107.52.7:88/member/sign/course/student?"+
                "current=" + current +
                "&size=" + size+
                "&userId="+userId;
        // 请求头
        Headers headers = new Headers.Builder()
                .add("appId", Api.appId)
                .add("appSecret", Api.appSecret)
                .add("Accept", "application/json, text/plain, */*")
                .build();

        //请求组合创建
        Request request = new Request.Builder()
                .url(url)
                // 将请求头加至请求中
                .headers(headers)
                .get()
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            //发起请求，传入callback进行回调
            client.newCall(request).enqueue(ResponseBody.callback);
        } catch (NetworkOnMainThreadException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}