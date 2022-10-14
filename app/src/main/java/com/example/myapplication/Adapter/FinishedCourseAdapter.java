package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.JavaBean.Course;

import java.util.List;

public class FinishedCourseAdapter extends ArrayAdapter<Course> {
    private final Context mContext;
    private final int resourceId;

    public FinishedCourseAdapter(Context context,
                                 int resourceId, List<Course> data) {
        super(context, resourceId, data);
        this.mContext = context;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course news = getItem(position);
        String goodUrls = news.getCoursePhoto();
        String courseName = news.getCourseName();
        View view;
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
        vh.txCourseName.setText(courseName);
        return view;
    }

    static class ViewHolder {
        ImageView ivImage;
        TextView txCourseName;
    }
}
