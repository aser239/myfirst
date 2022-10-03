package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.javaBean.Course;
import com.example.myapplication.R;

import java.util.List;

public class CollectionAdapter extends ArrayAdapter<Course> {

    private final List<Course> mNewsData;
    private Context mContext;
    private int resourceId;
    private String goodUrls;
    private String CourseName;
    private AdapterView.OnItemClickListener mListener;



    public CollectionAdapter(Context context,
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
        final ViewHolder vh;
        if (convertView == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(resourceId, parent, false);
            vh = new ViewHolder();
            vh.ivImage = view.findViewById(R.id.iv_image2);
            vh.txCourseName = view.findViewById(R.id.tv_title2);
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



    class ViewHolder {
        ImageView ivImage;
        TextView txCourseName;

//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            ivImage = itemView.findViewById(R.id.iv_image);
//
//            //点击事件放在adapter中使用，也可以写个接口在activity中调用
//            //方法一：在adapter中设置点击事件
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Course data = mNewsData.get(getLayoutPosition());
//                    //可以选择直接在本位置直接写业务处理
//                    Toast.makeText(context, "点击了item", Toast.LENGTH_SHORT).show();
//                    //此处回传点击监听事件
//                    if (onItemClickListener != null) {
//                        onItemClickListener.OnItemClick(v, data);
//                    }
//                }
//            });
//        }
    }


}
