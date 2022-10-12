package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.javaBean.Records2Detail;

import java.util.List;

public class SignlistAdapter extends ArrayAdapter<Records2Detail>{
    private final List<Records2Detail> mNewsData;
    private final Context mContext;
    private final int resourceId;
    private String CourseName;
    private String CourseAddr;
    private long createTime;


    public SignlistAdapter(Context context,
                                 int resourceId, List<Records2Detail> data) {
        super(context, resourceId, data);
        this.mContext = context;
        this.mNewsData = data;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Records2Detail news = getItem(position);
        CourseName = news.getCourseName();
        CourseAddr = news.getCourseAddr();
        createTime = news.getCreateTime();
        View view ;
        final SignlistAdapter.ViewHolder vh;
        if (convertView == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(resourceId, parent, false);
            vh = new ViewHolder();
            vh.txCourseName1 = view.findViewById(R.id.tv_title6);
            vh.txCourseName2 = view.findViewById(R.id.tv_title7);
            vh.txCourseName3 = view.findViewById(R.id.tv_title8);
            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }
        vh.txCourseName1.setText(CourseName);
        vh.txCourseName2.setText(CourseAddr);
        vh.txCourseName3.setText(String.valueOf(createTime));
        return view;
    }


    static class ViewHolder {
        TextView txCourseName1;
        TextView txCourseName2;
        TextView txCourseName3;
    }
}
