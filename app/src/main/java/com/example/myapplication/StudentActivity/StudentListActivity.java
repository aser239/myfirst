package com.example.myapplication.StudentActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Data.SignInformationData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.TeacherActivity.TeacherCourseListActivity;
import com.example.myapplication.TeacherActivity.TeacherSignInActivity;
import com.example.myapplication.javaBean.Course;
import com.example.myapplication.javaBean.CourseDetail;
import com.example.myapplication.javaBean.Records;
import com.example.myapplication.javaBean.Records2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StudentListActivity extends AppCompatActivity {
    private Button btdian;
    private EditText courseId;
    private ResponseBody<Records2> dataResponseBody;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        btdian = findViewById(R.id.btdian);
        courseId = findViewById(R.id.et_CourseID22);

        System.out.println(TeacherCourseListActivity.courseId);

        btdian.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final String CourseID = courseId.getText().toString();
                if (courseId.getText().toString().equals("")) {
                    Toast.makeText(StudentListActivity.this,"请输入课程ID！", Toast.LENGTH_SHORT).show();

                }else if (!Objects.equals(TeacherCourseListActivity.courseId, Integer.parseInt(CourseID))){
                    Toast.makeText(StudentListActivity.this,"输入与所选课程ID不匹配！", Toast.LENGTH_SHORT).show();

                }else {
                    int courseId4 = Integer.parseInt(CourseID);
                    Student(courseId4, 1, 1, 0, LoginData.loginUser.getId());
                    Toast.makeText(StudentListActivity.this,"获取userSignID成功！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    public void Student(int courseId,int current, int size,int status,int userId) {
        // url路径
        String url = "http://47.107.52.7:88/member/sign/course/student/signList?"+"courseId="+courseId+
                "&current=" + current +
                "&size=" + size+"&status="+status+"&userId="+userId;
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
            client.newCall(request).enqueue(callback);
        } catch (NetworkOnMainThreadException ex) {
            ex.printStackTrace();
        }
    }
    public final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            // 获取响应体的json串
            String body = Objects.requireNonNull(response.body()).string();
            Log.d("签到列表：", body);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    Type jsonType = new TypeToken<ResponseBody<Records2>>() {
                    }.getType();
                    // 解析json串到自己封装的状态
                    dataResponseBody = gson.fromJson(body, jsonType);
                    CourseData.Records2 = dataResponseBody.getData();
                }
            });
        }
    };
}
