package com.example.myapplication.TeacherActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Activity.HomeActivity;
import com.example.myapplication.Activity.MessageActivity;
import com.example.myapplication.Activity.UploadActivity;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

//老师选课
@RequiresApi(api = Build.VERSION_CODES.N)
@SuppressLint("StaticFieldLeak")
public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener {
    public static EditText etCollegeName;
    public static EditText etCourseName;
    public static EditText etCoursePhoto;
    public static EditText etIntroduce;
    public static EditText etEndTime;
    public static EditText etStartTime;
    public static int ID = 0;
    private static long endTime;
    private static long startTime;
    Calendar calendar = Calendar.getInstance(Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        Button btnDate1 = findViewById(R.id.bt_fiTime);
        Button btnDate2 = findViewById(R.id.bt_stTime);
        Button btnAdd = findViewById(R.id.confirmAddCourse);
        Button btSearch = findViewById(R.id.search);
        Button btPhoto = findViewById(R.id.bt_photo);
        Button btBack = findViewById(R.id.addCourseReturnToCourse33);

        etEndTime = findViewById(R.id.et_fiTime);
        etStartTime = findViewById(R.id.et_stTime);
        etCollegeName = findViewById(R.id.et_collegeName);
        etCourseName = findViewById(R.id.et_courseName);
        etIntroduce = findViewById(R.id.et_introduce);
        etCoursePhoto = findViewById(R.id.et_photo);
        btnDate1.setOnClickListener(this);
        btnDate2.setOnClickListener(this);

        btBack.setOnClickListener(v -> {
            Intent intent2 = new Intent(AddCourseActivity.this, HomeActivity.class);
            intent2.putExtra("id", 2);
            startActivity(intent2);
        });

        btPhoto.setOnClickListener(v -> {
            UploadActivity.isClickCoursePicture = true;
            Intent intent = new Intent(AddCourseActivity.this, UploadActivity.class);
            ID = 3;
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnAdd.setOnClickListener(v -> {
            if (etEndTime.getText().toString().equals("") ||
                    etStartTime.getText().toString().equals("") ||
                    etCoursePhoto.getText().toString().equals("") ||
                    etCollegeName.getText().toString().equals("") ||
                    etCourseName.getText().toString().equals("") ||
                    etIntroduce.getText().toString().equals("")
            ) {
                Toast.makeText(AddCourseActivity.this, "输入不能为空！",
                        Toast.LENGTH_SHORT).show();
            } else {
                String CoursePhoto = etCoursePhoto.getText().toString();
                String CollegeName = etCollegeName.getText().toString();
                String CourseName = etCourseName.getText().toString();
                String Introduce = etIntroduce.getText().toString();

                AddCourse(CollegeName, CourseName, CoursePhoto, Introduce,
                        endTime, LoginData.loginUser.getRealName(), startTime);
                Toast.makeText(AddCourseActivity.this, "添加课程成功！",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btSearch.setOnClickListener(v -> startActivity(new Intent(AddCourseActivity.this,
                TeacherCourseListActivity.class)));

    }

    public static void CoursePhoto(EditText photo, String Url) {
        photo.setText(Url);
    }

    /**
     * 日期选择
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final EditText Time,
                                            Calendar calendar, boolean isWhatDate) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        // 绑定监听器(How the parent is notified that the date is set.)
        new DatePickerDialog(activity, themeResId, (view, year, monthOfYear, dayOfMonth) -> {
            // 此处得到选择的时间，可以进行你想要的操作
            String month = addDate(String.valueOf(monthOfYear + 1));
            String day = addDate(String.valueOf(dayOfMonth));
            try {
                if (isWhatDate) {
                    AddCourseActivity.startTime = dateToStamp(year + "-" + month + "-" + day);
                } else {
                    AddCourseActivity.endTime = dateToStamp(year + "-" + month + "-" + day);
                }
                Time.setText(MessageActivity.getTimeStampString(dateToStamp(year + "-" + month + "-" + day)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_fiTime:
                showDatePickerDialog(this, 4, etEndTime, calendar, false);
                break;
            case R.id.bt_stTime:
                showDatePickerDialog(this, 4, etStartTime, calendar, true);
            default:
                break;
        }

    }

    //时间转换时间戳
    public static long dateToStamp(String s) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        assert date != null;
        return date.getTime();
    }

    //补足月份、日期
    public static String addDate(String m) {
        return Integer.parseInt(m) > 10 ? m : "0" + m;
    }

    public void AddCourse(String collegeName, String courseName,
                          String coursePhoto, String introduce, long endTime,
                          String realName, long startTime) {
        new Thread(() -> {
            // url路径
            String url = "http://47.107.52.7:88/member/sign/course/teacher";
            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", Api.appId)
                    .add("appSecret", Api.appSecret)
                    .build();
            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("collegeName", collegeName);
            bodyMap.put("courseName", courseName);
            bodyMap.put("coursePhoto", coursePhoto);
            bodyMap.put("endTime", endTime);
            bodyMap.put("introduce", introduce);
            bodyMap.put("realName", realName);
            bodyMap.put("startTime", startTime);
            bodyMap.put("userId", String.valueOf(LoginData.loginUser.getId()));
            bodyMap.put("userName", LoginData.loginUser.getUsername());
            // 将Map转换为字符串类型加入请求体中
            String body = Api.gson.toJson(bodyMap);

            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody r = RequestBody.Companion.create(body, MEDIA_TYPE_JSON);
            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .post(r)
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                //发起请求，传入callback进行回调
                client.newCall(request).enqueue(ResponseBody.callback);
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}