package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Data.PictureData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.example.myapplication.TeacherActivity.AddCourseActivity;
import com.example.myapplication.JavaBean.Data;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    public static boolean isClickCoursePicture = false;
    public static boolean isClickAvatar = false;
    public File file;
    private ImageView iv_upload;
    private ActivityResultLauncher<Intent> mResultLauncher;
    private Context context;
    private Uri picUri;
    private Button bt_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        iv_upload = findViewById(R.id.bt_upload);
        iv_upload.setOnClickListener(this);
        context = iv_upload.getContext();
        bt_upload = findViewById(R.id.bt_upload_ok);

        //跳转带系统相册，选择图片并返回
        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        assert intent != null;
                        picUri = intent.getData();
                        try {
                            file = getFile(context, picUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (picUri != null) {
                            iv_upload.setImageURI(picUri);
                            bt_upload.setOnClickListener(v -> PictureUpload(file));
                        }
                    }
                });
    }

    @SuppressLint("NewApi")
    private final Handler handler1 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String pictureUrl = bundle.getString("pictureURL");
            if (pictureUrl == null) {
                Toast.makeText(UploadActivity.this, "上传失败！",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (UploadActivity.isClickAvatar) {
                    PictureData.tempAvatar.setURL(pictureUrl);
                    LoadAvatar(PictureData.tempAvatar.getURL());
                }
                if (UploadActivity.isClickCoursePicture) {
                    PictureData.coursePicture.setURL(pictureUrl);
                    UploadActivity.isClickCoursePicture = false;
                    AddCourseActivity.CoursePhoto(AddCourseActivity.etCoursePhoto,
                            PictureData.coursePicture.getURL());
                    Toast.makeText(UploadActivity.this, "添加成功！",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            System.out.println(pictureUrl);
        }
    };

    private final Handler handler2 = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int avatarCode = bundle.getInt("code");
            if (avatarCode == 200) {
                LoginData.loginUser.setAvatar(PictureData.tempAvatar.getURL());
                System.out.println("hello");
                Toast.makeText(UploadActivity.this, "修改成功！",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(UploadActivity.this, "修改失败！",
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
                        Log.d("修改用户信息：", body);
                        // 解析json串到自己封装的状态
                        ResponseBody<Data> dataResponseBody = Api.gson.fromJson(body, jsonType);
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("code", dataResponseBody.getCode());
                        message.setData(bundle);
                        handler2.sendMessage(message);
                    }
                });
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void PictureUpload(File file) {
        new Thread(() -> {
            // url路径
            String url = "http://47.107.52.7:88/member/sign/image/upload";
            // 请求头
            Headers headers = new Headers.Builder()
                    .add("appId", Api.appId)
                    .add("appSecret", Api.appSecret)
                    .add("Accept", "application/json, text/plain, */*")
                    .build();

            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

            RequestBody fileBody = RequestBody.Companion.create(file, MEDIA_TYPE_JSON);
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(), fileBody)
                    .build();

            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .post(body)
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
                        Type jsonType = new TypeToken<ResponseBody<Object>>() {
                        }.getType();
                        // 获取响应体的json串
                        String body = Objects.requireNonNull(response.body()).string();
                        Log.d("图片上传：", body);
                        // 解析json串到自己封装的状态
                        ResponseBody<Object> dataResponseBody = Api.gson.fromJson(body, jsonType);
                        PictureData.picture = dataResponseBody.getData();
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("pictureURL", PictureData.picture.toString());
                        message.setData(bundle);
                        handler1.sendMessage(message);
                        System.out.println(PictureData.picture);
                        System.out.println("dataResponseBody.getCode())");
                    }
                });
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_upload) {  //跳转到系统相册，选择图片并返回
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            mResultLauncher.launch(intent);
        }
    }

    public static File getFile(Context context, Uri picUri) throws IOException {
        File destinationFilename = new File(context.getFilesDir().getPath() +
                File.separatorChar + queryName(context, picUri));
        try (InputStream ins = context.getContentResolver().openInputStream(picUri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return destinationFilename;
    }

    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            //Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null,
                        null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    private void LoadAvatar(String avatar) {
        String collegeName = LoginData.loginUser.getCollegeName();
        String realName = LoginData.loginUser.getRealName();
        boolean gender = LoginData.loginUser.getGender();
        String phone = LoginData.loginUser.getPhone();
        int id = LoginData.loginUser.getId();
        int idNumber = LoginData.loginUser.getIdNumber();
        String userName = LoginData.loginUser.getUsername();
        String email = LoginData.loginUser.getEmail();
        int inSchoolTime = LoginData.loginUser.getInSchoolTime();
        AlterUserInfo(collegeName, realName, gender, phone,
                avatar, id, idNumber, userName, email, inSchoolTime);
    }
}