package com.example.myapplication.Activity;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.myapplication.R;

import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etFiTime;
    // private TextView txtTime;
    private Button btnDate1;
    private EditText etStTime;
    private Button btnDate2;
    Calendar calendar= Calendar.getInstance(Locale.CHINA);
    private final String coursePhoto = "https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2022/09/22/777f78b9-4b7d-401f-986a-7bc61f903201.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        btnDate1 = findViewById(R.id.bt_fiTime);
        btnDate2 = findViewById(R.id.bt_stTime);

        //txtDate= findViewById(R.id.txtDate);
        etFiTime = findViewById(R.id.et_fiTime);
        etStTime = findViewById(R.id.et_stTime);

        btnDate1.setOnClickListener(this);
        btnDate2.setOnClickListener(this);


    }

    /**
     * 日期选择
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
                Time.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
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
                showDatePickerDialog(this,  4, etFiTime, calendar);;
                break;
            case R.id.bt_stTime:
                showDatePickerDialog(this,4, etStTime, calendar);
            default:
                break;
        }

    }

}