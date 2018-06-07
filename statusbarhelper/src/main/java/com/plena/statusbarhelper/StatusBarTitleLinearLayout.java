package com.plena.statusbarhelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;

/**
 * Copyright © 1997 - 2018 Plena. All Rights Reserved.
 * Created by Plena on 2018/6/7.
 * Describe：
 */
public class StatusBarTitleLinearLayout extends LinearLayout {
    public StatusBarTitleLinearLayout(Context context) {
        this(context, null, 0);
    }

    public StatusBarTitleLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarTitleLinearLayout(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(LinearLayout.HORIZONTAL);
        reSetPadding(context);
    }

    @TargetApi(19)
    public void reSetPadding(Context context) {
        int actionBarHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TypedValue tv = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
                    tv, true);
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
            float m = context.getResources().getDisplayMetrics().density;
            actionBarHeight = (int) (actionBarHeight / m + 0.5f) + 5;
            setPadding(getPaddingLeft(), getPaddingTop() + actionBarHeight,
                    getPaddingRight(), getPaddingBottom());
        }
    }
}
