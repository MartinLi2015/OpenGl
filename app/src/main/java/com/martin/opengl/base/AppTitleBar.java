package com.martin.opengl.base;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.martin.opengl.R;


/**
 * 标题栏
 */

public class AppTitleBar extends RelativeLayout {

    public AppTitleBar(Context context) {
        super(context);
        init();
    }

    public AppTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ImageButton mIbtnBack;
    private TextView mTvTitle;
    private TextView mTvRight;

    private void init() {
        View.inflate(getContext(), R.layout.activity_title, this);
        mIbtnBack = (ImageButton) findViewById(R.id.activity_title_back);
        mTvTitle = (TextView) findViewById(R.id.activity_title_text);
        mTvRight = (TextView) findViewById(R.id.activity_title_text_right);
    }


    public AppTitleBar setTitleText(@StringRes int titleId) {
        mTvTitle.setText(titleId);
        return this;
    }

    public AppTitleBar setTitleText(String title) {
        mTvTitle.setText(title);
        return this;
    }

    public AppTitleBar setTextRight(@StringRes int textRightId) {
        mTvRight.setText(textRightId);
        mTvRight.setVisibility(VISIBLE);
        return this;
    }

    public AppTitleBar setTextRight(String textRight) {
        mTvRight.setText(textRight);
        mTvRight.setVisibility(VISIBLE);
        return this;
    }


    public AppTitleBar setTitleBarListener(final TitleBarListener listener) {
        mTvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });
        mIbtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });
        return this;
    }

    public AppTitleBar setLeftImageVisiable(boolean visiable) {
        mIbtnBack.setVisibility(visiable ? VISIBLE : GONE);
        return this;
    }

    public AppTitleBar setLeftImage(@DrawableRes int leftImageId) {
        mIbtnBack.setImageResource(leftImageId);
        return this;
    }

    interface TitleBarListener {
        void leftClick();

        void rightClick();
    }


}

