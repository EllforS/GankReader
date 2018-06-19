package com.ellfors.gankreader.app;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatDelegate;

import com.ellfors.gankreader.di.component.AppComponent;
import com.ellfors.gankreader.di.component.DaggerAppComponent;
import com.ellfors.gankreader.di.module.AppModule;
import com.ellfors.gankreader.utils.AppUtils;
import com.ellfors.gankreader.utils.L;

import org.litepal.LitePalApplication;

import java.util.Iterator;
import java.util.Stack;

public class MyApplication extends LitePalApplication
{
    private static Stack<Activity> stack;
    private static MyApplication mApp;

    public static MyApplication getInstance()
    {
        return mApp;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        init();
    }

    private void init()
    {
        mApp = this;
        stack = new Stack<>();

        setDayNightMode();
        L.init(AppConfig.LOG_TAG, 2, false, 0);
    }

    /**
     * 设置主题
     */
    private void setDayNightMode()
    {
        switch (AppUtils.getStringSharedPreferences(this, AppConfig.DAY_NIGHT_MODE, AppConfig.MODE_DAY))
        {
            case AppConfig.MODE_DAY:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case AppConfig.MODE_NIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                break;
        }
    }

    /**
     * 获取AppComponent
     */
    public static AppComponent getAppComponent()
    {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule(mApp))
                .build();
    }

    /**
     * 将Activity加入栈
     */
    public void addTask(Activity activity)
    {
        if (stack == null)
            stack = new Stack<>();
        stack.add(activity);
    }

    /**
     * 将Activity移出栈
     */
    public void removeTask(Activity activity)
    {
        if (stack != null && activity != null && stack.contains(activity))
            stack.remove(activity);
    }

    /**
     * 获取栈顶的Activity
     */
    public Context currentActivity()
    {
        try
        {
            return stack.lastElement() == null ? getApplicationContext() : stack.lastElement();
        }
        catch (Exception e)
        {
            return getApplicationContext();
        }
    }

    /**
     * 销毁Acitivity
     */
    public void finishLastActivity()
    {
        Activity activity = stack.lastElement();
        if (activity != null)
            this.finishActivity(activity);
    }

    /**
     * 销毁Acitivity
     */
    public void finishActivity(Activity activity)
    {
        if (activity != null && stack != null)
        {
            stack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 销毁Acitivity
     */
    public void finishActivity(Class<?> cls)
    {
        if (cls == null || stack == null)
            return;
        for (Activity activity : stack)
        {
            if (activity.getClass().equals(cls))
            {
                this.finishActivity(activity);
            }
        }
    }

    /**
     * 判断是否含有Activity
     */
    public boolean hasActivity(Class<?> cls)
    {
        Iterator var2 = stack.iterator();
        Activity activity;
        do
        {
            if (!var2.hasNext())
            {
                return false;
            }

            activity = (Activity) var2.next();
        } while (!activity.getClass().equals(cls));
        return true;
    }

    /**
     * 销毁全部Activity
     */
    public void finishAllActivity()
    {
        int i = 0;
        for (int size = stack.size(); i < size; ++i)
        {
            if (null != stack.get(i))
            {
                stack.get(i).finish();
            }
        }
        stack.clear();
    }

    /**
     * 判断是否大于当前版本
     */
    public static boolean isMethodsCompat(int VersionCode)
    {
        int currentVersion = Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
}
