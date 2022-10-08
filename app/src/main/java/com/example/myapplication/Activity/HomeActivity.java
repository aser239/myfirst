package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.myapplication.Data.LoginData;
import com.example.myapplication.R;
import com.example.myapplication.ui.CourseFragment;
import com.example.myapplication.ui.SignFragment;
import com.example.myapplication.ui.MeFragment;
import com.example.myapplication.ui.TeacherCourseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView mNavView ;
    private FrameLayout mNavContainer;
    private CourseFragment mCourseFragment;
    private SignFragment mSignFragment;
    private MeFragment mMeFragment;
    private TeacherCourseFragment mTeacherCourseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTeacherCourseFragment = new TeacherCourseFragment();
        mMeFragment = new MeFragment();
        mSignFragment = new SignFragment();
        mCourseFragment = new CourseFragment();
        mNavContainer = findViewById(R.id.nav_container);
        mNavView=findViewById(R.id.nav_view);
        switchFragment(mSignFragment);


        mNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (item.getItemId()){
                    case R.id.nav_item_course:
                        if(true){
                            switchFragment(mTeacherCourseFragment);
                            Log.d("course","课程");
                        }else if(LoginData.loginUser.getRoleId() == 1){
                            switchFragment(mTeacherCourseFragment);
                            Log.d("course","教师课程");
                        }

                        break;
                    case R.id.nav_item_me:
                        switchFragment(mMeFragment);
                        Log.d("me","我的");
                        break;
                    case R.id.nav_item_sign:
                        switchFragment(mSignFragment);
                        Log.d("find","发现");
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