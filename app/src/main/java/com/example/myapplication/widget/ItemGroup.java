package com.example.myapplication.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.utils.TextChangeWatcher;

public class ItemGroup extends FrameLayout implements View.OnClickListener {
    LinearLayout itemGroupLayout;  //组合控件的布局
    private TextView titleTv;  //标题
    private EditText contentEdt;  //输入框
    private ImageView clearIv;  //清除输入内容
    private ImageView arrowRightIv;  //向右的箭头

    public ItemGroup(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public ItemGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    public ItemGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }

    //初始化View
    private void initView(Context context) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context)
                .inflate(R.layout.item_group_layout, null);
        itemGroupLayout = view.findViewById(R.id.item_group_layout);
        titleTv = view.findViewById(R.id.title_tv);
        contentEdt = view.findViewById(R.id.content_edt);
        clearIv = view.findViewById(R.id.clear_iv);
        arrowRightIv = view.findViewById(R.id.arrow_right_iv);
        addView(view);  //把自定义的这个组合控件的布局加入到当前FrameLayout

        itemGroupLayout.setOnClickListener(this);
        clearIv.setOnClickListener(this);
        contentEdt.addTextChangedListener(new TextChangeWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                //输入框的输入内容改变
                String content = contentEdt.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    //输入内容不为空的时候，清除输入的Icon可见
                    clearIv.setVisibility(VISIBLE);
                } else {
                    clearIv.setVisibility(GONE);
                }
            }
        });
    }

    /**
     * 初始化相关属性，引入相关属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        //标题默认的字体大小，18sp
        float defaultTitleSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, context.getResources().getDisplayMetrics());
        //标题的默认字体颜色
        int defaultTitleColor = context.getResources().getColor(R.color.black);
        //输入框默认的字体大小，16sp
        float defaultEdtSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        //输入框的默认字体颜色
        int defaultEdtColor = context.getResources().getColor(R.color.EdtColor);
        //输入框的默认的提示内容的字体颜色
        int defaultHintColor = context.getResources().getColor(R.color.EdtColor);

        //通过obtainStyledAttributes方法获取到一个TypedArray对象，然后通过TypedArray对象就可以获取到相对应定义的属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemGroup);
        String title = typedArray.getString(R.styleable.ItemGroup_title);
        float titleSize = typedArray.getDimension(R.styleable.ItemGroup_title_size, defaultTitleSize);
        int titleColor = typedArray.getColor(R.styleable.ItemGroup_title_color, defaultTitleColor);

        String content = typedArray.getString(R.styleable.ItemGroup_edt_content);
        float contentSize = typedArray.getDimension(R.styleable.ItemGroup_edt_text_size, defaultEdtSize);
        int contentColor = typedArray.getColor(R.styleable.ItemGroup_edt_text_color, defaultEdtColor);
        String hintContent = typedArray.getString(R.styleable.ItemGroup_edt_hint_content);
        int hintColor = typedArray.getColor(R.styleable.ItemGroup_edt_hint_text_color, defaultHintColor);

        //默认输入框可以编辑
        boolean isEditable = typedArray.getBoolean(R.styleable.ItemGroup_isEditable, true);
        //向右的箭头图标是否可见，默认可见
        boolean showJtIcon = typedArray.getBoolean(R.styleable.ItemGroup_arrowRight_visible, true);
        typedArray.recycle();

        //设置数据
        titleTv.setText(title);
        titleTv.setTextSize(titleSize);
        titleTv.setTextColor(titleColor);

        contentEdt.setText(content);
        contentEdt.setTextSize(contentSize);
        contentEdt.setTextColor(contentColor);
        contentEdt.setHint(hintContent);
        contentEdt.setHintTextColor(hintColor);

        contentEdt.setFocusableInTouchMode(isEditable);  //设置输入框是否可以编辑
        contentEdt.setLongClickable(false);  //输入框不允许长按
        arrowRightIv.setVisibility(showJtIcon ? View.VISIBLE : View.GONE);  //设置向右的箭头图标是否可见
    }

    @Override
    public void onClick(View v) {

    }
}