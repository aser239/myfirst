package com.example.myapplication.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class TitleBar extends FrameLayout {
    LinearLayout titleBarLayout;  //组合控件的布局
    private ImageView iv_backward;  //向左的箭头
    private TextView tv_title;  //标题
    private TextView tv_subTitle;  //副标题

    public TitleBar(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }

    //初始化View
    private void initView(Context context) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context)
                .inflate(R.layout.titlebar, null);
        titleBarLayout = view.findViewById(R.id.layout_titleBar);
        iv_backward = view.findViewById(R.id.iv_backward);
        tv_title = view.findViewById(R.id.tv_title);
        tv_subTitle = view.findViewById(R.id.tv_subtitle);
        addView(view);  //把自定义的这个组合控件的布局加入到当前FrameLayout

        //设置监听器
        //如果点击back则结束活动
        iv_backward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //((Activity) getContext()).finish();
            }
        });
    }

    /**
     * 初始化相关属性，引入相关属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        //标题默认的字体大小，24sp
        float defaultTitleSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24, context.getResources().getDisplayMetrics());
        //标题的默认字体颜色
        int defaultColor = context.getResources().getColor(R.color.black);
        float defaultSubtitleSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, context.getResources().getDisplayMetrics());
        //通过obtainStyledAttributes方法获取到一个TypedArray对象，然后通过TypedArray对象就可以获取到相对应定义的属性值
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.titleBar);
        String title = typedArray.getString(R.styleable.titleBar_title_txt);
        float titleSize = typedArray.getDimension(R.styleable.titleBar_titleTxt_size, defaultTitleSize);
        int titleColor = typedArray.getColor(R.styleable.titleBar_txt_color, defaultColor);
        String subtitle = typedArray.getString(R.styleable.titleBar_subtitle);
        float subtitleSize = typedArray.getDimension(R.styleable.titleBar_subtitle_size, defaultSubtitleSize);
        int subtitleColor = typedArray.getColor(R.styleable.titleBar_txt_color, defaultColor);

        //箭头图标是否可见，默认可见
        boolean showJtIcon = typedArray.getBoolean(R.styleable.ItemGroup_arrowRight_visible, true);
        typedArray.recycle();

        //设置数据
        tv_title.setText(title);
        tv_title.setTextSize(titleSize);
        tv_title.setTextColor(titleColor);
        tv_subTitle.setText(subtitle);
        tv_subTitle.setTextSize(subtitleSize);
        tv_subTitle.setTextColor(subtitleColor);
        iv_backward.setVisibility(showJtIcon ? View.VISIBLE : View.GONE);  //设置向右的箭头图标是否可见
    }
}