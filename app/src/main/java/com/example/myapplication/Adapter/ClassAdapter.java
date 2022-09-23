package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.example.myapplication.ViewHolder.ClassItemVH;
import com.example.myapplication.javaBean.Course;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    List<Course> listItem;

    public ClassAdapter(Context context,List<Course> listItem){
        this.inflater = LayoutInflater.from(context);
        this.listItem = listItem;

    }
    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClassItemVH holder;
        if(convertView==null){
            holder = new ClassItemVH();
            convertView = inflater.inflate(R.layout.list_item,parent,false);
//            holder.collegeName =  convertView.findViewById(R.id.et_collegeName);
            holder.courseName = convertView.findViewById(R.id.item_tv);
//            holder.coursePhoto = convertView.findViewById(R.id.et_photo);
//            holder.endTime  = convertView.findViewById(R.id.et_fiTime);
//            holder.introduce  = convertView.findViewById(R.id.et_introduce);
//            holder.realName  = convertView.findViewById(R.id.et_realName);
//            holder.startTime  = convertView.findViewById(R.id.et_stTime);
            convertView.setTag(holder);
        }else{
            holder = (ClassItemVH)convertView.getTag();
        }
//        holder.collegeName.setText(listItem.get(position).getCollegeName());
        holder.courseName.setText(listItem.get(position).getCourseName());
//        holder.coursePhoto.setText(listItem.get(position).getCoursePhoto());
//        holder.endTime.setText(listItem.get(position).getEndTime());
//        holder.introduce.setText(listItem.get(position).getIntroduce());
//        holder.realName.setText(listItem.get(position).getRealName());
//        holder.startTime.setText(listItem.get(position).getStartTime());
        return convertView;
    }
}