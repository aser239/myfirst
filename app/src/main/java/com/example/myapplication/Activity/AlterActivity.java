package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;

public class AlterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        TextView tv_title_alter = findViewById(R.id.tv_title_alter);
        Intent intent = getIntent();
        String info = intent.getStringExtra(PersonInfoActivity.Info_String);
        if (info != null) {
            if (tv_title_alter != null) {
                tv_title_alter.setText(info);
            }
        }
    }
}