package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Data.MsgData;
import com.example.myapplication.Data.PictureData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;
import com.example.myapplication.TeacherActivity.AddCourseActivity;
import com.example.myapplication.javaBean.Picture;
import com.example.myapplication.ui.MeFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    public File file;
    private ImageView iv_upload;
    private ActivityResultLauncher<Intent> mResultLauncher;
    private Context context;
    private Uri picUri;
    private Button bt_upload;
    public static String URL;
    public static boolean isClickCoursePicture = false;

    @SuppressLint("NewApi")
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
                            //System.out.println(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (picUri != null) {
                            iv_upload.setImageURI(picUri);
                            //System.out.println("666");
                            bt_upload.setOnClickListener(v -> {
                                Api.PictureUpload(file);
                                try {
                                    Thread.sleep(750);
                                    //System.out.println(PictureData.tempAvatar.getURL());
                                    if (MeFragment.isClickAvatar) {
                                        MeFragment.isClickAvatar = false;
                                        if (PictureData.tempAvatar.getURL() != null) {
                                            //System.out.println("123");
                                            AlterActivity.LoadData("头像", PictureData.tempAvatar.getURL());
                                            try {
                                                Thread.sleep(500);
                                                if (MsgData.alterMsgData.getCode() == 200) {
                                                    System.out.println("hello");
                                                    AlterActivity.UpdateData("头像", PictureData.tempAvatar
                                                            .getURL());
                                                    System.out.println("hello");
                                                    Toast.makeText(UploadActivity.this, "修改成功！",
                                                            Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(UploadActivity.this, "修改失败！",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Toast.makeText(UploadActivity.this, "修改失败！",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    System.out.println("123");
                                    System.out.println(PictureData.coursePicture.getURL());
                                    if (UploadActivity.isClickCoursePicture) {
                                        UploadActivity.isClickCoursePicture = false;
                                        if (PictureData.coursePicture.getURL() != null) {
                                            AddCourseActivity.CoursePhoto(AddCourseActivity.etCoursePhoto, PictureData.coursePicture.getURL());
                                            Toast.makeText(UploadActivity.this, "修改成功！",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(UploadActivity.this, "修改失败！",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                    System.out.println(PictureData.coursePicture.getURL());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Intent intent1 = new Intent(UploadActivity.this, HomeActivity.class);
                                intent1.putExtra("id",1);
                                startActivity(intent1);

                            });
                        }
                    }
                });
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
            //Log.e("Save File", ex.getMessage());
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
}