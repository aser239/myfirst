package com.example.myapplication.TeacherActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Data.SignInformationData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

//老师端发起签到
@RequiresApi(api = Build.VERSION_CODES.N)
public class TeacherSignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etFiTime;
    private EditText etStTime;
    private EditText CourseAddr;
    private EditText total;
    private EditText signCode;
    private static long startTime2;
    private static long endTime2;
    Calendar calendar = Calendar.getInstance(Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_in);

        Button btStTime = findViewById(R.id.bt_stTime33);
        Button btFiTime = findViewById(R.id.bt_fiTime33);
        Button startSign = findViewById(R.id.StartSign);
        Button btBack = findViewById(R.id.Back);

        etStTime = findViewById(R.id.et_stTime33);
        etFiTime = findViewById(R.id.et_fiTime33);

        CourseAddr = findViewById(R.id.Addr);
        total = findViewById(R.id.people);
        signCode = findViewById(R.id.Signpassword33);

        btStTime.setOnClickListener(this);
        btFiTime.setOnClickListener(this);

        btBack.setOnClickListener(v -> finish());

        startSign.setOnClickListener(v -> {
            if (etFiTime.getText().toString().equals("") ||
                    etStTime.getText().toString().equals("") ||
                    CourseAddr.getText().toString().equals("") ||
                    signCode.getText().toString().equals("") ||
                    total.getText().toString().equals("")

            ) {
                Toast.makeText(TeacherSignInActivity.this, "输入不能为空！",
                        Toast.LENGTH_SHORT).show();

            } else {
                String addr = CourseAddr.getText().toString();
                int Code = Integer.parseInt(signCode.getText().toString());
                String Total = total.getText().toString();

                if (!Objects.equals(SignInformationData.Information.getCourseNum(), Total)
                        || Total.equals("0")) {
                    Toast.makeText(TeacherSignInActivity.this, "输入与选课人数不匹配或者未选课！",
                            Toast.LENGTH_SHORT).show();

                } else {
                    int Total2 = Integer.parseInt(Total);
                    Sign(startTime2, addr, SignInformationData.Information.getCourseId(),
                            SignInformationData.Information.getCourseName(), endTime2, Code, Total2,
                            LoginData.loginUser.getId());
                    Toast.makeText(TeacherSignInActivity.this, "发起签到成功！",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    /**
     * 时间选择
     */
    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv,
                                            Calendar calendar, boolean isWhatDate) {
        String year = String.valueOf(calendar.get(java.util.Calendar.YEAR));
        String month = add0(String.valueOf(calendar.get(java.util.Calendar.MONTH) + 1));
        String day = add0(String.valueOf(calendar.get(java.util.Calendar.DAY_OF_MONTH)));

        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog(activity, themeResId,
                // 绑定监听器
                (view, hourOfDay, minute) -> {
                    String hour = String.valueOf(hourOfDay);
                    String Minute = String.valueOf(minute);
                    System.out.println("66");
                    try {
                        if (isWhatDate) {
                            TeacherSignInActivity.startTime2 = dateToStamp(year + "-" + month + "-" + day
                                    + " " + hour + ":" + Minute + ":" + "00");
                        } else {
                            TeacherSignInActivity.endTime2 = dateToStamp(year + "-" + month + "-" + day
                                    + " " + hour + ":" + Minute + ":" + "00");
                        }
                        tv.setText(getTimeStampString(dateToStamp(year + "-" + month + "-" + day + " "
                                + hour + ":" + Minute + ":" + "00")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                , true).show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_fiTime33:
                showTimePickerDialog(this, 4, etFiTime, calendar, false);
                break;
            case R.id.bt_stTime33:
                showTimePickerDialog(this, 4, etStTime, calendar, true);
                break;
            default:
                break;
        }
    }

    //时间转换时间戳
    public static long dateToStamp(String s) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        assert date != null;
        return date.getTime();
    }

    //补足月份、日期
    public static String add0(String m) {
        return Integer.parseInt(m) > 10 ? m : "0" + m;
    }

    public static void Sign(long beginTime, String courseAddr,
                            int courseId, String courseName, long endTime,
                            int signCode, int total, int userId) {
        new Thread(() -> {
            // url路径
            String url = "http://47.107.52.7:88/member/sign/course/teacher/initiate";
            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", Api.appId)
                    .add("appSecret", Api.appSecret)
                    .build();
            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("beginTime", beginTime);
            bodyMap.put("courseAddr", courseAddr);
            bodyMap.put("courseId", courseId);
            bodyMap.put("courseName", courseName);
            bodyMap.put("endTime", endTime);
            bodyMap.put("signCode", signCode);
            bodyMap.put("total", total);
            bodyMap.put("userId", userId);
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

    //时间戳转换时间
    public static String getTimeStampString(long time) {
        Date date = new Date(time);
        // 格式化日期
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
