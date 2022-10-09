package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.Data.MsgData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;
import com.example.myapplication.ui.MeFragment;

public class AlterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter);

        Init();
    }

    private void Init() {
        ImageView Alter_backward = findViewById(R.id.iv_backward_alter);
        Alter_backward.setOnClickListener(v -> {
            Intent intent = new Intent(AlterActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            intent.putExtra("id",1);
            //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        });

        TextView tv_title_alter = findViewById(R.id.tv_title_alter);
        TextView tv_title_alter_text = findViewById(R.id.tv_title_alter_text);
        EditText et_title_alter_text = findViewById(R.id.et_title_alter_text);
        Intent intent = getIntent();
        String info = intent.getStringExtra(PersonInfoActivity.MESSAGE_STRING);
        Button login_preservation = findViewById(R.id.login_preservation);
        if (info != null) {
            tv_title_alter.setText(info);
            tv_title_alter_text.setText(info);
            if (info.equals("性别")) {
                et_title_alter_text.setHint(info + "格式：男 或 女");
            } else if (info.equals("入校时间")) {
                et_title_alter_text.setHint("时间格式：yyyyMMDD");
            } else {
                et_title_alter_text.setHint("请输入" + info);
            }
            login_preservation.setOnClickListener(v -> {
                String newData = et_title_alter_text.getText().toString();
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
                    }/* else if (info.equals("姓名") && !IsRealName(newData)) {
                        Toast.makeText(AlterActivity.this, "姓名信息错误！",
                                Toast.LENGTH_SHORT).show();
                    }*/ else if (info.equals("入校时间") && !IsDate(newData)) {
                        Toast.makeText(AlterActivity.this, "日期格式错误！",
                                Toast.LENGTH_SHORT).show();
                    } else if (info.equals("性别") && !IsGender(newData)) {
                        Toast.makeText(AlterActivity.this, "性别格式错误！",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        LoadData(info, newData);
                        try {
                            Thread.sleep(750);
                            if (MsgData.alterMsgData.getCode() == 200) {
                                System.out.println("hello");
                                UpdateData(info, newData);
                                System.out.println("hello");
                                Toast.makeText(AlterActivity.this, "修改成功！",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AlterActivity.this, "修改失败！",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                Intent intent1 = new Intent(AlterActivity.this, HomeActivity.class);
                intent1.putExtra("id",1);
                startActivity(intent1);
            });
        }
    }

    public static void LoadData(String type, String data) {
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
            case "工号":
                idNumber = Integer.parseInt(data);
                break;
            case "邮箱":
                email = data;
                break;
            case "入校时间":
                inSchoolTime = Integer.parseInt(data);
                break;
        }
        Api.AlterUserInfo(collegeName, realName, gender, phone,
                avatar, id, idNumber, userName, email, inSchoolTime);
    }

    public static void UpdateData(String type, String data) {
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
            case "工号":
                LoginData.loginUser.setIdNumber(Integer.parseInt(data));
                break;
            case "邮箱":
                LoginData.loginUser.setEmail(data);
                break;
            case "入校时间":
                LoginData.loginUser.setInSchoolTime(Integer.parseInt(data));
                break;
        }
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