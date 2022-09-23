package com.example.myapplication.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Adapter.ClassAdapter;
import com.example.myapplication.Data.CourseData;
import com.example.myapplication.Interface.Api;
import com.example.myapplication.R;
import com.example.myapplication.javaBean.Course;

public class GetCourseListActivity extends AppCompatActivity {
    ListView mListView;

    int current = 1;
    int size = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_course_list);

        Api.GetCourse(current,size);

        //初始化ListView控件
        mListView = findViewById(R.id.lv);
        //创建一个Adapter的实例
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        //设置Adapter
        mListView.setAdapter(mAdapter);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                startActivity(new Intent(GetCourseListActivity.this,MessageActivity.class));
//            }
//        });
    }


    class MyBaseAdapter extends BaseAdapter {
        //需要适配的数据
        String[] names = {CourseData.Course.getCourseName()};
        //图片集合
        int[] icons = {Integer.parseInt(CourseData.Course.getCoursePhoto())};
        //得到item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return names.length;
        }

        //得到Item代表的对象
        @Override
        public Object getItem(int position) {
            //返回ListView Item条目代表的对象
            return names[position];
        }

        //得到Item的id
        @Override
        public long getItemId(int position) {
            //返回ListView Item的id
            return position;
        }

        //得到Item的View视图
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            //在第一次创建convertView时将控件找出，在第二次重用convertView时直接通过getTag()方法获得这些控件
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).
                        inflate(R.layout.list_item, parent, false);
                holder = new ViewHolder();
                holder.mTextView = convertView.findViewById(R.id.item_tv);
                holder.imageView = convertView.findViewById(R.id.item_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTextView.setText(names[position]);
            holder.imageView.setBackgroundResource(icons[position]);
            return convertView;
        }

        class ViewHolder {
            TextView mTextView;
            ImageView imageView;
        }
    }
}