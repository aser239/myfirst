package com.example.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.Interface.ResponseBody;
import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
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
    public File file ;
    private ImageView bt_upload;
    private ActivityResultLauncher<Intent> mResultLauncher;
    private Context context;
    private Uri picUri;
    private Button upload;
    public static String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        bt_upload = findViewById(R.id.bt_upload);
        bt_upload.setOnClickListener(this);
        context = bt_upload.getContext();
        upload = findViewById(R.id.bt_upload_ok);

        //跳转带系统相册，选择图片并返回
        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                assert intent != null;
                picUri = intent.getData();
                try {
                    file = getFile(context,picUri);
                    System.out.println(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (picUri != null){
                    bt_upload.setImageURI(picUri);
                    Log.d("binge","picUri"+picUri.toString());
                    System.out.println("666");
                    System.out.println(file);

                    upload.setOnClickListener(v->{
                        AvatarUpload(file);
                        finish();
                    });
                }
            }

        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_upload) {//跳转带系统相册，选择图片并返回
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
            Log.e("Save File", ex.getMessage());
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
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    public void AvatarUpload(File file){

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
            MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");

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
                client.newCall(request).enqueue(callback);
            }catch (NetworkOnMainThreadException ex){
                ex.printStackTrace();
            }
        }).start();
    }

    public Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            // 获取响应体的json串
            String body = Objects.requireNonNull(response.body()).string();
            Log.d("上传图片：", body);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Gson gson = new Gson();
                    Type jsonType = new TypeToken<ResponseBody<Object>>() {}.getType();
                    // 解析json串到自己封装的状态
                    ResponseBody<Object> dataResponseBody = gson.fromJson(body, jsonType);
                    LoginData.picture = dataResponseBody.getData();
                    URL = (String) LoginData.picture;
                    PersonInfoActivity.urlphoto = URL;
                }
            });
        }
    };

}