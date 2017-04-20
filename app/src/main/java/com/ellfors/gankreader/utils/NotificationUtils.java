package com.ellfors.gankreader.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * 通知工具类
 * <p>
 * 需要添加
 * <uses-permission android:name="android.permission.VIBRATE"/>
 */
public class NotificationUtils
{
    private static NotificationManager manager;

    /**
     * 发起普通通知
     * 记得加上 android.permission.VIBRATE 权限
     *
     * @param context   发起通知的Activity
     * @param i         构筑的Intent
     * @param smallIcon 通知的小图标
     * @param largeIcon 通知的大图标
     * @param title     通知标题
     * @param content   通知内容
     * @param time      震动时间
     * @param id        通知ID
     */
    public static void showNormalNotifi(Context context, Intent i, int smallIcon, int largeIcon, String title, String content, int time, int id)
    {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent intent = PendingIntent.getActivity(context, 0, i, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder
                .setSmallIcon(smallIcon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIcon))
                .setContentTitle(title)
                .setContentText(content)
                //给小图标设置颜色
                //                .setColor(Color.parseColor("#00AAFF"))
                .setWhen(System.currentTimeMillis())
                .setContentIntent(intent)
                .build();

        //震动
        if (time != 0)
        {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(time);
        }

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //发起通知
        //如果id不同，则每click，在status哪里增加一个提示
        manager.notify(id, notification);
    }

    /**
     * 取消通知
     *
     * @param id 根据ID取消通知
     * @return true为成功  false失败
     */
    public static boolean cancelNotification(int id)
    {
        if (manager != null)
        {
            manager.cancel(id);

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 发起自定义通知
     *
     * @param context 发起通知的Activity
     * @param view    构造的view
     * @param intent  构造的Intent
     * @param time    震动时间
     * @param id      通知ID
     */
    public static void showCustomNotifi(Context context, int icon, RemoteViews view, Intent intent, int time, int id)
    {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification myNotify = new Notification();
        myNotify.icon = icon;
        myNotify.when = System.currentTimeMillis();
        myNotify.flags = Notification.FLAG_AUTO_CANCEL;// 不能够自动清除
        myNotify.contentView = view;

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //震动
        if (time != 0)
        {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(time);
        }

        myNotify.contentIntent = contentIntent;
        manager.notify(id, myNotify);
    }
}
