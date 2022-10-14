package com.example.myapplication.TeacherActivity;

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
import android.widget.Toast;

import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Data.SignInformationData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

//老师端发起签到
@RequiresApi(api = Build.VERSION_CODES.N)
public class TeacherSignInActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btFiTime;
    private Button btStTime;
    private Button StartSign;
    private Button btBack;
    private EditText etFiTime;
    private EditText etStTime;
    private EditText CourseAddr;
    private EditText total;
    private EditText courseId;
    private EditText courseName;
    private EditText userId;
    private EditText signCode;
    public static int CourseID3;


    Calendar calendar= Calendar.getInstance(Locale.CHINA);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_in);

        btStTime = findViewById(R.id.bt_stTime33);
        btFiTime = findViewById(R.id.bt_fiTime33);
        StartSign = findViewById(R.id.StartSign);
        btBack = findViewById(R.id.Back);

        etStTime =  findViewById(R.id.et_stTime33);
        etFiTime = findViewById(R.id.et_fiTime33);

        CourseAddr = findViewById(R.id.Addr);
        total = findViewById(R.id.people);
        userId = findViewById(R.id.userId33);
        signCode = findViewById(R.id.Signpassword33);

        btStTime.setOnClickListener(this);
        btFiTime.setOnClickListener(this);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        StartSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFiTime.getText().toString().equals("")||
                        etStTime.getText().toString().equals("")||
                        CourseAddr.getText().toString().equals("")||
                        signCode.getText().toString().equals("")||
                        userId.getText().toString().equals("")||
                        total.getText().toString().equals("")

                ){
                    Toast.makeText(TeacherSignInActivity.this,"输入不能为空！", Toast.LENGTH_SHORT).show();

                }else {
                    long endTime2 = Long.parseLong(etFiTime.getText().toString());
                    long startTime2 = Long.parseLong(etStTime.getText().toString());
                    String addr = CourseAddr.getText().toString();
                    int Code = Integer.parseInt(signCode.getText().toString());
                    String userID = userId.getText().toString();
                    String Total = total.getText().toString();

                    if (!Objects.equals(SignInformationData.Information.getCourseNum(), Total)||Total.equals("0")){
                        Toast.makeText(TeacherSignInActivity.this,"输入与选课人数不匹配或者未选课！", Toast.LENGTH_SHORT).show();

                    }else if(!Objects.equals(LoginData.loginUser.getId(), Integer.parseInt(userID))){
                        Toast.makeText(TeacherSignInActivity.this,"输入与用户ID不匹配！", Toast.LENGTH_SHORT).show();


                    }else {
                        int Total2 = Integer.parseInt(Total);
                        int userID2 = Integer.parseInt(userID);
                        Api.Sign(startTime2, addr, SignInformationData.Information.getCourseId(),SignInformationData.Information.getCourseName(), endTime2, Code, Total2, userID2);
                        Toast.makeText(TeacherSignInActivity.this,"发起签到成功！", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
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
        String month = add0(String.valueOf(calendar.get(java.util.Calendar.MONTH)+1));
        String day = add0(String.valueOf(calendar.get(java.util.Calendar.DAY_OF_MONTH)));

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

                        try {
                            tv.setText(dateToStamp(year+"-"+month+"-"+day+" "+hour+":"+Minute+":"+"00"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

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
                showTimePickerDialog(this,  4, etFiTime, calendar);;
                break;
            case R.id.bt_stTime33:
                showTimePickerDialog(this,  4, etStTime, calendar);
                break;
            default:
                break;
        }
    }

    //时间转换时间戳
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }



    //补足月份、日期
    public static String add0(String m){
        return Integer.parseInt(m)>10?m: "0"+m;
    }
}
