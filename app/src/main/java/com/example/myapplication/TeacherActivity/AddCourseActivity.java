package com.example.myapplication.TeacherActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.myapplication.Activity.UploadActivity;
import com.example.myapplication.Data.PictureData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//老师选课
@RequiresApi(api = Build.VERSION_CODES.N)
public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener {

    // private TextView txtTime;
    private Button btnDate1;
    private Button btnDate2;
    private Button btnAdd;
    private Button btSearch;
    private Button btPhoto;
    public static EditText etCollegeName;
    public static EditText etCourseName;
    public static EditText etCoursePhoto;
    public static EditText etIntroduce;
    public static EditText etEndTime;
    public static EditText etRealName;
    public static EditText etStartTime;

    Calendar calendar = Calendar.getInstance(Locale.CHINA);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        btnDate1 = findViewById(R.id.bt_fiTime);
        btnDate2 = findViewById(R.id.bt_stTime);
        btnAdd = findViewById(R.id.confirmAddCourse);
        btSearch = findViewById(R.id.search);
        btPhoto = findViewById(R.id.bt_photo);

        //txtDate= findViewById(R.id.txtDate);
        etEndTime = findViewById(R.id.et_fiTime);
        etStartTime = findViewById(R.id.et_stTime);
        etCollegeName = findViewById(R.id.et_collegeName);
        etCourseName = findViewById(R.id.et_courseName);
        etIntroduce = findViewById(R.id.et_introduce);
        etRealName = findViewById(R.id.et_realName);
        etCoursePhoto = findViewById(R.id.et_photo);
        btnDate1.setOnClickListener(this);
        btnDate2.setOnClickListener(this);

        btPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadActivity.isClickCoursePicture = true;
                Intent intent = new Intent(AddCourseActivity.this, UploadActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // etCoursePhoto.setText("https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2022/09/22/777f78b9-4b7d-401f-986a-7bc61f903201.jpg");
                long endTime = Long.parseLong(etEndTime.getText().toString());
                long startTime = Long.parseLong(etStartTime.getText().toString());

                String CoursePhoto = etCoursePhoto.getText().toString();
                String CollegeName = etCollegeName.getText().toString();
                String CourseName = etCourseName.getText().toString();
                String Introduce = etIntroduce.getText().toString();
                String RealName = etRealName.getText().toString();

                Api.AddCourse(CollegeName, CourseName, CoursePhoto, Introduce, endTime, RealName, startTime);
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCourseActivity.this, TeacherCourseListActivity.class));
            }
        });

    }

    public static void CoursePhoto(EditText photo,String Url){
        photo.setText(Url);
    }
    /**
     * 日期选择
     *
     * @param activity
     * @param themeResId
     * @param Time
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final EditText Time, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                String month = add0(String.valueOf(monthOfYear + 1));
                String day = add0(String.valueOf(dayOfMonth));
                try {
                    Time.setText(dateToStamp(year + "-" + month + "-" + day));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_fiTime:
                showDatePickerDialog(this, 4, etEndTime, calendar);
                ;
                break;
            case R.id.bt_stTime:
                showDatePickerDialog(this, 4, etStartTime, calendar);

            default:
                break;
        }

    }

    //时间转换时间戳
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    //补足月份、日期
    public static String add0(String m) {
        return Integer.parseInt(m) > 10 ? m : "0" + m;
    }

}