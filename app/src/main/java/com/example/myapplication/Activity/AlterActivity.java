package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.javaBean.Data;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AlterActivity extends AppCompatActivity {
    private String info;
    private String newData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter);

        Init();
    }

    private void Init() {
        TextView tv_title_alter = findViewById(R.id.tv_title_alter);
        TextView tv_title_alter_text = findViewById(R.id.tv_title_alter_text);
        EditText et_title_alter_text = findViewById(R.id.et_title_alter_text);
        Intent intent = getIntent();
        info = intent.getStringExtra(PersonInfoActivity.MESSAGE_STRING);
        Button login_preservation = findViewById(R.id.login_preservation);
        if (info != null) {
            tv_title_alter.setText(info);
            tv_title_alter_text.setText(info);
            if (info.equals("性别")) {
                et_title_alter_text.setHint(info + "格式：男 或 女");
            } else if (info.equals("入学时间")) {
                et_title_alter_text.setHint("时间格式：yyyyMMDD");
            } else {
                et_title_alter_text.setHint("请输入" + info);
            }
            login_preservation.setOnClickListener(v -> {
                newData = et_title_alter_text.getText().toString();
                if (newData.equals("")) {
                    Toast.makeText(AlterActivity.this, "输入不能为空！",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (info.equals("邮箱") && !IsEmail(newData)) {
                        Toast.makeText(AlterActivity.this, "邮箱格式错误！",
                                Toast.LENGTH_SHORT).show();
                    } else if (info.equals("手机号") && !IsPhoneNumber(newData)) {
                        Toast.makeText(AlterActivity.this, "手机号格式错误！",
                                Toast.LENGTH_SHORT).show();
                    } else if (info.equals("姓名") && !IsRealName(newData)) {
                        Toast.makeText(AlterActivity.this, "姓名信息错误！",
                                Toast.LENGTH_SHORT).show();
                    } else if (info.equals("入学时间") && !IsDate(newData)) {
                        Toast.makeText(AlterActivity.this, "日期格式错误！",
                                Toast.LENGTH_SHORT).show();
                    } else if (info.equals("性别") && !IsGender(newData)) {
                        Toast.makeText(AlterActivity.this, "性别格式错误！",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        LoadData(info, newData);

                    }
                }
            });
        }
    }

    public void LoadData(String type, String data) {
        String collegeName = LoginData.loginUser.getCollegeName();
        String realName = LoginData.loginUser.getRealName();
        boolean gender = LoginData.loginUser.getGender();
        String phone = LoginData.loginUser.getPhone();
        String avatar = LoginData.loginUser.getAvatar();
        int id = LoginData.loginUser.getId();
        int idNumber = LoginData.loginUser.getIdNumber();
        String userName = LoginData.loginUser.getUsername();
        String email = LoginData.loginUser.getEmail();
        int inSchoolTime = LoginData.loginUser.getInSchoolTime();
        switch (type) {
            case "头像":
                avatar = data;
                break;
            case "院校":
                collegeName = data;
                break;
            case "姓名":
                realName = data;
                break;
            case "性别":
                if (data.equals("男")) {
                    gender = true;
                } else if (data.equals("女")) {
                    gender = false;
                }
                break;
            case "手机号":
                phone = data;
                break;
            case "学号":
                idNumber = Integer.parseInt(data);
                break;
            case "邮箱":
                email = data;
                break;
            case "入校时间":
                inSchoolTime = Integer.parseInt(data);
                break;
        }
        AlterUserInfo(collegeName, realName, gender, phone,
                avatar, id, idNumber, userName, email, inSchoolTime);
    }

    public void UpdateData(String type, String data) {
        switch (type) {
            case "头像":
                LoginData.loginUser.setAvatar(data);
                break;
            case "院校":
                LoginData.loginUser.setCollegeName(data);
                break;
            case "姓名":
                LoginData.loginUser.setRealName(data);
                break;
            case "性别":
                if (data.equals("男")) {
                    LoginData.loginUser.setGender(true);
                } else if (data.equals("女")) {
                    LoginData.loginUser.setGender(false);
                }
                break;
            case "手机号":
                LoginData.loginUser.setPhone(data);
                break;
            case "学号":
                LoginData.loginUser.setIdNumber(Integer.parseInt(data));
                break;
            case "邮箱":
                LoginData.loginUser.setEmail(data);
                break;
            case "入学时间":
                LoginData.loginUser.setInSchoolTime(Integer.parseInt(data));
                break;
        }
    }

    private final Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int alterCode = bundle.getInt("code");
            if (alterCode == 200) {
                System.out.println("hello");
                UpdateData(info, newData);
                Toast.makeText(AlterActivity.this, "修改成功！",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AlterActivity.this, "修改失败！",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void AlterUserInfo(String collegeName, String realName,
                               boolean gender, String phone, String avatar,
                               int id, int idNumber, String userName,
                               String email, int inSchoolTime) {
        new Thread(() -> {
            // url路径
            String url = "http://47.107.52.7:88/member/sign/user/update";
            // 请求头
            Headers headers = new Headers.Builder()
                    .add("Accept", "application/json, text/plain, */*")
                    .add("appId", Api.appId)
                    .add("appSecret", Api.appSecret)
                    .add("Content-Type", "application/json")
                    .build();
            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("collegeName", collegeName);
            bodyMap.put("realName", realName);
            bodyMap.put("gender", gender);
            bodyMap.put("phone", phone);
            bodyMap.put("avatar", avatar);
            bodyMap.put("id", id);
            bodyMap.put("idNumber", idNumber);
            bodyMap.put("userName", userName);
            bodyMap.put("email", email);
            bodyMap.put("inSchoolTime", inSchoolTime);
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
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Type jsonType = new TypeToken<ResponseBody<Data>>() {
                        }.getType();
                        // 获取响应体的json串
                        assert response.body() != null;
                        String body = Objects.requireNonNull(response.body()).string();
                        Log.d("info", body);
                        // 解析json串到自己封装的状态
                        ResponseBody<Data> dataResponseBody = Api.gson.fromJson(body, jsonType);
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("code", dataResponseBody.getCode());
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private boolean IsEmail(String checkInfo) {
        String regex = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
        return checkInfo.matches(regex);
    }

    private boolean IsPhoneNumber(String checkInfo) {
        String regex = "^((13[0-9])|(14(0|[5-7]|9))|(15([0-3]|[5-9]))|(16(2|[5-7]))|(17[0-8])|(18[0-9])|(19([0-3]|[5-9])))\\d{8}$";
        return checkInfo.matches(regex);
    }

    private boolean IsRealName(String checkInfo) {
        String regex = "^[\u4e00-\u9fa5]{2,4}$";
        return checkInfo.matches(regex);
    }

    private boolean IsDate(String checkInfo) {
        String regex = "((\\d{3}[1-9]|\\d{2}[1-9]\\d|\\d[1-9]\\d{2}|[1-9]\\d{3})(((0[13578]|1[02])(0[1-9]|[12]\\d|3[01]))|((0[469]|11)(0[1-9]|[12]\\d|30))|(02(0[1-9]|[1]\\d|2[0-8]))))|(((\\d{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";
        return checkInfo.matches(regex);
    }

    private boolean IsGender(String checkInfo) {
        boolean f1 = checkInfo.matches("男");
        boolean f2 = checkInfo.matches("女");
        return (f1 & !f2) | (!f1 & f2);
    }
}