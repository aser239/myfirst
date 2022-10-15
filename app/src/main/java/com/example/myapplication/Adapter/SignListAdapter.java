package com.example.myapplication.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.myapplication.JavaBean.StudentSignListDetail;
import com.example.myapplication.R;
import com.example.myapplication.TeacherActivity.TeacherSignInActivity;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SignListAdapter extends ArrayAdapter<StudentSignListDetail> {
    private final int resourceId;

    public SignListAdapter(Context context,
                           int resourceId, List<StudentSignListDetail> data) {
        super(context, resourceId, data);
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudentSignListDetail news = getItem(position);
        String courseName = news.getCourseName();
        String courseAddr = news.getCourseAddr();
        long createTime = news.getCreateTime();
        View view;
        final SignListAdapter.ViewHolder vh;
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
        vh.txCourseName1.setText(courseName);
        vh.txCourseName2.setText(courseAddr);
        vh.txCourseName3.setText(TeacherSignInActivity.getTimeStampString(createTime));
        return view;
    }

    static class ViewHolder {
        TextView txCourseName1;
        TextView txCourseName2;
        TextView txCourseName3;
    }
}
