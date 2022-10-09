package com.example.myapplication.StudentActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.javaBean.Course;
import com.example.myapplication.javaBean.Records;
import com.example.myapplication.javaBean.Records2;
import com.example.myapplication.javaBean.Records2Detail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
    private ResponseBody<Records2> records2ResponseBody;
    private ResponseBody<Records> recordsResponseBody;
    private static ArrayList<Integer> studentJoinedCourseId;
    private static ArrayList<Integer> userSignId;
    public static int mUserSignId;

    //@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        btdian = findViewById(R.id.btdian);
        courseId = findViewById(R.id.et_CourseID22);

        btdian.setOnClickListener(v -> {
            StudentGetJoinedCourse(LoginData.loginUser.getId());
        });
    }

    private final Handler handler1 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            StudentListActivity.studentJoinedCourseId = bundle.getIntegerArrayList("courseId");
            System.out.println(bundle.getIntegerArrayList("courseId"));
            System.out.println(StudentListActivity.studentJoinedCourseId);
            for (int i : StudentListActivity.studentJoinedCourseId) {
                StudentSignList(i, 0, LoginData.loginUser.getId());
            }
        }
    };

    private final Handler handler2 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            StudentListActivity.userSignId = bundle.getIntegerArrayList("userSignId");
            System.out.println(StudentListActivity.userSignId);
            StudentListActivity.mUserSignId = StudentListActivity.userSignId.get(0);
            System.out.println(StudentListActivity.mUserSignId);
        }
    };

    private void StudentGetJoinedCourse(int userId) {
        new Thread(() -> {
            // url路径
            String url = "http://47.107.52.7:88/member/sign/course/student?" +
                    "userId=" + userId;
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
                client.newCall(request).enqueue(callback1);
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private final Callback callback1 = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            // 获取响应体的json串
            String body = Objects.requireNonNull(response.body()).string();
            Log.d("info：", body);

            runOnUiThread(() -> {
                Message message = new Message();
                Bundle bundle = new Bundle();
                Type jsonType = new TypeToken<ResponseBody<Records>>() {
                }.getType();
                // 解析json串到自己封装的状态
                recordsResponseBody = Api.gson.fromJson(body, jsonType);
                ArrayList<Integer> tempStudentJoinedCourseId = new ArrayList<>();
                if (recordsResponseBody.getData() != null) {
                    for (Course c : recordsResponseBody.getData().getRecords()) {
                        tempStudentJoinedCourseId.add(c.getCourseId());
                        System.out.println(c.getCourseId());
                        System.out.println(tempStudentJoinedCourseId);
                    }
                }
                bundle.putIntegerArrayList("courseId", tempStudentJoinedCourseId);
                message.setData(bundle);
                handler1.sendMessage(message);
            });
        }
    };

    public void StudentSignList(int courseId, int status, int userId) {
        // url路径
        String url = "http://47.107.52.7:88/member/sign/course/student/signList?" + "courseId=" +
                courseId + "&status=" + status + "&userId=" + userId;
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
            client.newCall(request).enqueue(callback2);
        } catch (NetworkOnMainThreadException ex) {
            ex.printStackTrace();
        }
    }

    public final Callback callback2 = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            // 获取响应体的json串
            String body = Objects.requireNonNull(response.body()).string();
            Log.d("info", body);
            Type jsonType = new TypeToken<ResponseBody<Records2>>() {
            }.getType();
            // 解析json串到自己封装的状态
            records2ResponseBody = Api.gson.fromJson(body, jsonType);

            runOnUiThread(() -> {
                Message message = new Message();
                Bundle bundle = new Bundle();
                // 解析json串到自己封装的状态
                ArrayList<Integer> tempUserSignId = new ArrayList<>();
                records2ResponseBody = Api.gson.fromJson(body, jsonType);
                System.out.println(records2ResponseBody.getData());
                if (records2ResponseBody.getData() != null) {
                    for (Records2Detail r : records2ResponseBody.getData().getRecords()) {
                        tempUserSignId.add(r.getUserSignId());
                        System.out.println(tempUserSignId);
                    }
                }
                bundle.putIntegerArrayList("userSignId", tempUserSignId);
                message.setData(bundle);
                handler2.sendMessage(message);
            });
        }
    };
}