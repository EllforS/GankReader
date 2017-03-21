package com.ellfors.gankreader.http.config;

/**
 * Retrofit 配置
 */
public class RetrofitConfig
{
    /**
     * Http请求与返回TAG
     */
    public static final String HTTP_TAG = "HTTP_TAG";
    /**
     * Log 的 Tag
     */
    public static final String TAG = "retrofit_log_tag";
    /**
     * Host地址
     */
    public static final String BASE_URL = "http://gank.io";
    /**
     * 超时时间
     */
    public static final int OUTTIME = 30;
    /**
     * ProgressDialog的Message
     */
    public static final String PROGRESS_DIALOG_MESSAGE = "正在连接网络...";
    /**
     * 取消网络请求时的Log信息
     */
    public static final String CANCEL_MESSAGE = "取消网络请求";
    /**
     * 无网络时对用户的提示信息
     */
    public static final String NOT_INTERNET_MESSAGE = "请检查您的网络...";
    /**
     * 请求错误时对用户的提示信息
     */
    public static final String ERROR_TOAST_MSG = "请求失败...";
    /**
     * 网络请求完成时的Log信息
     */
    public static final String COMPLETE_MESSAGE = "Complete";
    /**
     * 网络请求错误时的Log信息
     */
    public static final String ERROR_MESSAGE = "Retrofit Debug === ";
}
