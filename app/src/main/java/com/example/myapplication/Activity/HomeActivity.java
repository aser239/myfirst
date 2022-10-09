package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.R;
import com.example.myapplication.ui.StudentCourseFragment;
import com.example.myapplication.ui.StudentSignFragment;
import com.example.myapplication.ui.MeFragment;
import com.example.myapplication.ui.TeacherCourseFragment;
import com.example.myapplication.ui.TeacherSigninFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView mNavView ;
    private FrameLayout mNavContainer;
    private StudentCourseFragment mStudentCourseFragment;
    private StudentSignFragment mStudentSignFragment;
    public static MeFragment mMeFragment;
    private TeacherCourseFragment mTeacherCourseFragment;
    private TeacherSigninFragment mTeacherSigninFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_container,new MeFragment())
                    .addToBackStack(null)
                    .commit();
        }else if (id == 2){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_container,new TeacherCourseFragment())
                    .addToBackStack(null)
                    .commit();
        }

        mMeFragment = new MeFragment();
        mStudentSignFragment = new StudentSignFragment();
        mStudentCourseFragment = new StudentCourseFragment();
        mTeacherCourseFragment = new TeacherCourseFragment();
        mTeacherSigninFragment = new TeacherSigninFragment();
        mNavContainer = findViewById(R.id.nav_container);
        mNavView=findViewById(R.id.nav_view);

        if (LoginData.loginUser.getRoleId() == 1) {
            switchFragment(mTeacherCourseFragment);
        }else if (LoginData.loginUser.getRoleId() == 0){
            switchFragment(mStudentCourseFragment);
        }


        mNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (item.getItemId()){
                    case R.id.nav_item_course:
                        if (LoginData.loginUser.getRoleId() == 1) {
                            switchFragment(mTeacherCourseFragment);
                        }else if (LoginData.loginUser.getRoleId() == 0){
                            switchFragment(mStudentCourseFragment);
                        }
                        break;
                    case R.id.nav_item_me:
                            switchFragment(mMeFragment);
                        break;
                    case R.id.nav_item_sign:
                        if (LoginData.loginUser.getRoleId() == 1) {
                            switchFragment(mTeacherSigninFragment);
                        }else if (LoginData.loginUser.getRoleId() == 0){
                            switchFragment(mStudentSignFragment);
                        }
                        break;
                }
                return true;
            }
        });

    }

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_container,fragment).commitNow();
    }

}