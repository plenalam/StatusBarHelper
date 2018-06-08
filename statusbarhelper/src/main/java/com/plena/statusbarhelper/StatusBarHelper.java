package com.plena.statusbarhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Copyright © 1997 - 2018 Plena. All Rights Reserved.
 * Created by Plena on 2018/6/7.
 * Describe：
 */
public class StatusBarHelper {

    private Activity activity;

    public StatusBarHelper(Activity pActivity) {
        activity = pActivity;
    }

    /**
     * 设置沉浸式标题栏
     */
    @TargetApi(19)
    public StatusBarHelper setSystemStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        return this;
    }

    /**
     * 设置沉浸式DIalog
     *
     * @param dialog
     */
    @TargetApi(19)
    public static void setSystemStatusBarDialog(Dialog dialog) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置透明虚拟键盘
     *
     * @return
     */
    @TargetApi(19)
    public StatusBarHelper setSystemNavigationBar() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) && isHasNav()) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        return this;
    }

    /**
     * 获取ActionBar高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TypedValue tv = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
                    tv, true);
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics()) / 2;
        }
        return actionBarHeight;
    }

    /**
     * 是否有虚拟键盘
     *
     * @return
     */
    public static boolean isHasNav() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String sNavBarOverride;
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
                sNavBarOverride = null;
            }
            if ("1".equals(sNavBarOverride)) {
                return false;
            } else if ("0".equals(sNavBarOverride)) {
                return true;
            }
        }
        return false;
    }
}
