package com.example.myapplication.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;

import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TeacherSignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btFiTime;
    private Button btStTime;
    private Button StartSign;
    private TextView txFiTime;
    private TextView txStTime;
    private EditText CourseAddr;
    private EditText total;
    private EditText courseId;
    private EditText courseName;
    private EditText userId;
    private EditText signCode;


    Calendar calendar= Calendar.getInstance(Locale.CHINA);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_in);

        btStTime = findViewById(R.id.bt_stTime33);
        btFiTime = findViewById(R.id.bt_fiTime33);
        StartSign = findViewById(R.id.StartSign);

        txStTime =  findViewById(R.id.tx_stTime33);
        txFiTime = findViewById(R.id.tx_fiTime33);

        CourseAddr = findViewById(R.id.Addr);
        total = findViewById(R.id.people);
        courseId = findViewById(R.id.et_CourseID);
        courseName = findViewById(R.id.et_courseName);
        userId = findViewById(R.id.userId);
        signCode = findViewById(R.id.Signpassword);

        btStTime.setOnClickListener(this);
        btFiTime.setOnClickListener(this);
        System.out.println("661");
//        int StartTime = Integer.parseInt(txStTime.getText().toString());
//        int EndTime = Integer.parseInt(txFiTime.getText().toString());



        StartSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api.Sign(1137,"123",209,"4354354",1300,520,1,344);
            }
        });
    }

        /**
         * 时间选择
         * @param activity
         * @param themeResId
         * @param tv
         * @param calendar
         */

    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv, Calendar calendar) {
        String year = String.valueOf(calendar.get(java.util.Calendar.YEAR));
        String month = String.valueOf(calendar.get(java.util.Calendar.MONTH)+1);
        String day = String.valueOf(calendar.get(java.util.Calendar.DAY_OF_MONTH));
        // Calendar c = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog( activity,themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hour = String.valueOf(hourOfDay);
                        String Minute = String.valueOf(minute);
                        System.out.println("66");
                      tv.setText( year+month+day+hour+Minute);
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_fiTime33:
                showTimePickerDialog(this,  4, txFiTime, calendar);;
                break;
            case R.id.bt_stTime33:
                showTimePickerDialog(this,  4, txStTime, calendar);
                break;
            default:
                break;
        }
    }
}
