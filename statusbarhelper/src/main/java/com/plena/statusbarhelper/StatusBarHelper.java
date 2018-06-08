package com.plena.statusbarhelper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
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

    public StatusBarHelper setWindowFullScreen(boolean isFullScreen) {
        if (isFullScreen) {
            activity.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            activity.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        return this;
    }

    @TargetApi(21)
    public StatusBarHelper setStatusBarColor(@ColorInt int color) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
        return this;
    }


    /**
     * 为根布局设置FitsSystemWindows属性
     *
     * @return
     */
    @TargetApi(19)
    public StatusBarHelper setContentFitsSystemWindows() {
        ViewGroup contentView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        contentView.setFitsSystemWindows(true);
        contentView.setClipToPadding(true);
        return this;
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
     * 设置沉浸式Dialog
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


    /**
     * 设置标题栏亮色模式，即白底黑字
     *
     * @param lightMode
     * @return
     */
    public StatusBarHelper setStatusBarLightMode(boolean lightMode) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && lightMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//SDK23
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                window.setAttributes(attributes);
            }
            //高版本的小米魅族需要用原生的设置，但是以下的API依旧返回true
            MIUISetStatusBarLightMode(activity, true);
            FlymeSetStatusBarLightMode(activity, true);
            OPPOSetStatusBarLightMode(activity.getWindow(), true);
        }
        return this;
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     *
     * @param activity 需要设置的窗口
     * @param dark     是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            FlymeStatusbarColorUtils.setStatusBarDarkIcon(activity, true);
            result = true;
        }
        return result;
    }

    /**
     * OPPO手机适配
     *
     * @param window
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return
     */
    public static boolean OPPOSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 0x00000010;
                int vis = window.getDecorView().getSystemUiVisibility();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (dark) {
                        vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    } else {
                        vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (dark) {
                        vis |= SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
                    } else {
                        vis &= ~SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT;
                    }
                }
                window.getDecorView().setSystemUiVisibility(vis);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 需要MIUIV6以上
     *
     * @param activity
     * @param dark     是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }
}
