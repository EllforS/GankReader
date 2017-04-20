package com.ellfors.gankreader.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtil
{
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>()
    {
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>()
    {
        @Override
        protected SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将字符串转位日期类型(yyyy-MM-dd HH:mm:ss)
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate)
    {
        try
        {
            return dateFormater.get().parse(sdate);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 将字符串转位日期类型(yyyy-MM-dd)
     *
     * @param sdate
     * @return
     */
    public static Date toDate_2(String sdate)
    {
        try
        {
            return dateFormater2.get().parse(sdate);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate)
    {
        Date time = toDate(sdate);

        if (time == null)
        {
            return "Unknown";
        }

        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate))
        {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            int minute = (int) ((cal.getTimeInMillis() - time.getTime()) / 60000);

            if (hour == 0)
            {
                if (minute < 1)
                {
                    ftime = "刚刚";
                }
                else
                {
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
                }
            }
            else
            {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);

        if (days == 0)
        {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
            {
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            }
            else
            {
                ftime = hour + "小时前";
            }
        }
        else if (days == 1)
        {
            ftime = "昨天";
        }
        else if (days == 2)
        {
            ftime = "前天";
        }
        else if (days > 2 && days <= 10)
        {
            ftime = days + "天前";
        }
        else if (days > 10)
        {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 字符串日期转换成中文格式日期
     *
     * @param date 字符串日期 yyyy-MM-dd
     * @return yyyy年MM月dd日
     * @throws ParseException
     * @throws Exception
     */
    public String dateToCnDate(String date)
    {

        String result = "";
        String[] cnDate = new String[]{"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String ten = "十";
        String[] dateStr = date.split("-");
        for (int i = 0; i < dateStr.length; i++)
        {
            for (int j = 0; j < dateStr[i].length(); j++)
            {
                String charStr = dateStr[i];
                String str = String.valueOf(charStr.charAt(j));
                if (charStr.length() == 2)
                {
                    if (charStr.equals("10"))
                    {
                        result += ten;
                        break;
                    }
                    else
                    {
                        if (j == 0)
                        {
                            if (charStr.charAt(j) == '1')
                                result += ten;
                            else if (charStr.charAt(j) == '0')
                                result += "";
                            else
                                result += cnDate[Integer.parseInt(str)] + ten;
                        }
                        if (j == 1)
                        {
                            if (charStr.charAt(j) == '0')
                                result += "";
                            else
                                result += cnDate[Integer.parseInt(str)];
                        }
                    }
                }
                else
                {
                    result += cnDate[Integer.parseInt(str)];
                }
            }
            if (i == 0)
            {
                result += "年";
                continue;
            }
            if (i == 1)
            {
                result += "月";
                continue;
            }
            if (i == 2)
            {
                result += "日";
                continue;
            }
        }
        return result;
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date)
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获取当前时间
     */
    public static String getNowTime()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String time = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

        return time;
    }

}
