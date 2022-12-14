package com.example.myapplication.StudentActivity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Adapter.SignListAdapter;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.JavaBean.Records2;
import com.example.myapplication.JavaBean.StudentSignListDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private SignListAdapter adapter;
    private ListView lvNewsList;
    private ResponseBody<Records2> dataResponseBody;
    public static int userSignID;

    public SignListActivity() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signlist);
        lvNewsList = findViewById(R.id.lv_news_list66);

        initData();
        lvNewsList.setOnItemClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {
        List<StudentSignListDetail> newsData = new ArrayList<>();
        adapter = new SignListAdapter(SignListActivity.this,
                R.layout.list_item5, newsData);

        lvNewsList.setAdapter(adapter);
        Sign(StudentCourseListActivity.courseId, 1, 10, 0, LoginData.loginUser.getId());
    }

    public void Sign(int courseId, int current, int size, int status, int userId) {
        // url??????
        String url = "http://47.107.52.7:88/member/sign/course/student/signList?" +
                "courseId=" + courseId +
                "&current=" + current +
                "&size=" + size +
                "&status=" + status +
                "&userId=" + userId;
        // ?????????
        Headers headers = new Headers.Builder()
                .add("appId", Api.appId)
                .add("appSecret", Api.appSecret)
                .add("Accept", "application/json, text/plain, */*")
                .build();

        //??????????????????
        Request request = new Request.Builder()
                .url(url)
                // ???????????????????????????
                .headers(headers)
                .get()
                .build();
        try {
            OkHttpClient client = new OkHttpClient();
            //?????????????????????callback????????????
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

            // ??????????????????json???
            String body = Objects.requireNonNull(response.body()).string();
            Log.d("?????????????????????", body);

            runOnUiThread(() -> {
                Gson gson = new Gson();
                Type jsonType = new TypeToken<ResponseBody<Records2>>() {
                }.getType();
                // ??????json???????????????????????????
                dataResponseBody = gson.fromJson(body, jsonType);

                if (dataResponseBody.getData() != null) {
                    for (StudentSignListDetail news : dataResponseBody.getData().getRecords()) {
                        adapter.add(news);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SignListActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        userSignID = dataResponseBody.getData().getRecords().get(position).getUserSignId();
        finish();
    }
}