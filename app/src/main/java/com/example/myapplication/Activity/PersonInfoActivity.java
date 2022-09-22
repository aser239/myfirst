package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class PersonInfoActivity extends AppCompatActivity {
    private ImageView personInfo_arrow_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);

        init();
    }

    private void init() {
        Button login_exit = findViewById(R.id.login_exit);
        login_exit.setOnClickListener(v -> startActivity(new Intent(PersonInfoActivity.this,
                LoginActivity.class)));

        ImageView personInfo_backward = findViewById(R.id.iv_backward);
        personInfo_backward.setOnClickListener(v -> startActivity(new Intent(PersonInfoActivity.this,
                MainActivity.class)));

    }
}