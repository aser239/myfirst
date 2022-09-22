package com.example.myapplication.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.R;

import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TimeActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etStTime;
   // private TextView txtTime;
    private Button btnDate;
    private TextView txtDate;
    DateFormat format= DateFormat.getDateTimeInstance();
    Calendar calendar= Calendar.getInstance(Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_time);
        btnDate= findViewById(R.id.btn_Date);
        txtDate= findViewById(R.id.txtDate);
        //etStTime = findViewById(R.id.et_stTime);
        btnDate.setOnClickListener(this);
    }

    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText("您选择了：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

//    /**
//     * 时间选择
//     * @param activity
//     * @param themeResId
//     * @param tv
//     * @param calendar
//     */
//    public static void showTimePickerDialog(Activity activity,int themeResId, final TextView tv, Calendar calendar) {
//        // Calendar c = Calendar.getInstance();
//        // 创建一个TimePickerDialog实例，并把它显示出来
//        // 解释一哈，Activity是context的子类
//        new TimePickerDialog( activity,themeResId,
//                // 绑定监听器
//                new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        tv.setText("您选择了：" + hourOfDay + "时" + minute  + "分");
//                    }
//                }
//                // 设置初始时间
//                , calendar.get(Calendar.HOUR_OF_DAY)
//                , calendar.get(Calendar.MINUTE)
//                // true表示采用24小时制
//                ,true).show();
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Date:
                showDatePickerDialog(this,  4,txtDate, calendar);;
                break;
            default:
                break;
        }
    }

}