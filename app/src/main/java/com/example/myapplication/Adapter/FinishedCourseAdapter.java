package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.javaBean.Course;

import java.util.List;

public class FinishedCourseAdapter extends ArrayAdapter<Course>{
    private final List<Course> mNewsData;
    private final Context mContext;
    private final int resourceId;
    private String goodUrls;
    private String CourseName;

    public FinishedCourseAdapter(Context context,
                                 int resourceId, List<Course> data) {
        super(context, resourceId, data);
        this.mContext = context;
        this.mNewsData = data;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course news = getItem(position);
        goodUrls = news.getCoursePhoto();
        CourseName = news.getCourseName();
        View view ;
        final FinishedCourseAdapter.ViewHolder vh;
        if (convertView == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(resourceId, parent, false);
            vh = new ViewHolder();
            vh.ivImage = view.findViewById(R.id.iv_image5);
            vh.txCourseName = view.findViewById(R.id.tv_title5);
            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }
        Glide.with(mContext).load(goodUrls)
                .into(vh.ivImage);
        vh.txCourseName.setText(CourseName);
        return view;
    }


    static class ViewHolder {
        ImageView ivImage;
        TextView txCourseName;
    }
}