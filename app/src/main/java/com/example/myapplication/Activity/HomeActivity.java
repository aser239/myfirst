package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.R;
import com.example.myapplication.Fragment.MeFragment;
import com.example.myapplication.Fragment.StudentCourseFragment;
import com.example.myapplication.Fragment.StudentSignFragment;
import com.example.myapplication.Fragment.TeacherCourseFragment;
import com.example.myapplication.Fragment.TeacherSignInFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private StudentCourseFragment mStudentCourseFragment;
    private StudentSignFragment mStudentSignFragment;
    @SuppressLint("StaticFieldLeak")
    public static MeFragment mMeFragment;
    private TeacherCourseFragment mTeacherCourseFragment;
    private TeacherSignInFragment mTeacherSignInFragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_container, new MeFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == 2) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_container, new TeacherCourseFragment())
                    .addToBackStack(null)
                    .commit();
        }

        mMeFragment = new MeFragment();
        mStudentSignFragment = new StudentSignFragment();
        mStudentCourseFragment = new StudentCourseFragment();
        mTeacherCourseFragment = new TeacherCourseFragment();
        mTeacherSignInFragment = new TeacherSignInFragment();
        BottomNavigationView mNavView = findViewById(R.id.nav_view);

        if (LoginData.loginUser.getRoleId() == 1) {
            switchFragment(mTeacherCourseFragment);
        } else if (LoginData.loginUser.getRoleId() == 0) {
            switchFragment(mStudentCourseFragment);
        }

        mNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_item_course:
                    if (LoginData.loginUser.getRoleId() == 1) {
                        switchFragment(mTeacherCourseFragment);
                    } else if (LoginData.loginUser.getRoleId() == 0) {
                        switchFragment(mStudentCourseFragment);
                    }
                    break;
                case R.id.nav_item_me:
                    switchFragment(mMeFragment);
                    break;
                case R.id.nav_item_sign:
                    if (LoginData.loginUser.getRoleId() == 1) {
                        switchFragment(mTeacherSignInFragment);
                    } else if (LoginData.loginUser.getRoleId() == 0) {
                        switchFragment(mStudentSignFragment);
                    }
                    break;
            }
            return true;
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_container, fragment).commitNow();
    }
}