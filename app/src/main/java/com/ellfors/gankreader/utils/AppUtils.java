package com.ellfors.gankreader.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * UI工具类
 */
public class AppUtils {

    /**
     * Preferences constant
     */
    public static final String SHARED_USSER = "ExUtil";

    /**
     * 打印文本 长时间
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 打印文本 短时间
     */
    public static void showToast(Context context, int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示视图
     */
    public static void showView(View view) {
        if (view == null) {
            return;
        } else {
            if (View.GONE == view.getVisibility() || View.INVISIBLE == view.getVisibility()) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 隐藏视图
     */
    public static void hideViewGone(View view) {
        if (view != null && View.VISIBLE == view.getVisibility()) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏视图 保留位置
     */
    public static void hideViewInvisible(View view) {
        if (view != null && View.VISIBLE == view.getVisibility()) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 判断视图是否显示
     */
    public static boolean isShowView(View view) {
        if (view != null && view.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 写入SharedPreferences数据
     */
    public static void setStringSharedPreferences(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_USSER, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 读取SharedPreferences数据
     */
    public static String getStringSharedPreferences(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_USSER, MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 判断是否为中文版
     */
    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();

        if (language.endsWith("zh")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查是否存在SDCard
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证是否存在可用网络
     */
    public static boolean CheckNetworkState(Context context) {
        int state = currentNetwork(context);
        return state < 2 ? true : false;
    }

    /**
     * 验证网络状态 0 存在wifi网络， 1 存在2/3G网络，2无网络连接
     */
    public static int currentNetwork(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State mobile = null, wifi = null;

        try {
            mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        } catch (Exception e) {

        }

        try {
            wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        } catch (Exception e) {

        }
        // 判断当前连接的网络 返回相对应的状态
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            return 0;
        } else if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 获取屏幕高度像素
     */
    public static int getDisplayHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度像素
     */
    public static int getDisplayWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        return dm.widthPixels;
    }
}
