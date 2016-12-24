package com.ellfors.gankreader.utils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Logger Utils
 */
public class L {
    private static String mTag = "AAA";

    /**
     * Init Your Log
     * @param tag
     * @param methodCount
     * @param hideThread
     * @param methodOffset
     */
    public static void init(String tag, int methodCount, boolean hideThread, int methodOffset) {
        mTag = tag;
        if (hideThread) {
            Logger.init(mTag)
                    .methodCount(methodCount)
                    .methodOffset(methodOffset);
        } else {
            Logger.init(mTag)
                    .hideThreadInfo()
                    .methodCount(methodCount)
                    .methodOffset(methodOffset);
        }
    }

    /**
     * Close Log
     */
    public static void close() {
        Logger.init(mTag).logLevel(LogLevel.NONE);
    }

    /**
     * Open Log
     */
    public static void open() {
        Logger.init(mTag).logLevel(LogLevel.FULL);
    }

    /**
     * Log Json
     * @param str
     */
    public static void json(String str){
        Logger.json(str);
    }

    /**
     * Log Xml
     * @param str
     */
    public static void xml(String str){
        Logger.xml(str);
    }

    /**
     * Log String Verbose
     * @param str
     */
    public static void v(String str) {
        Logger.v(str);
    }

    /**
     * Log String Info
     * @param str
     */
    public static void i(String str){
        Logger.i(str);
    }

    /**
     * Log String Warn
     * @param str
     */
    public static void w(String str) {
        Logger.w(str);
    }

    /**
     * Log String Debug
     * @param str
     */
    public static void d(String str) {
        Logger.d(str);
    }

    /**
     * Log String Error
     * @param str
     */
    public static void e(String str) {
        Logger.e(str);
    }

    /**
     * Log List
     * @param list
     */
    public static void list(List list) {
        Logger.d(list);
    }

    /**
     * Log Map
     * @param map
     */
    public static void map(Map map){
        Logger.d(map);
    }

    /**
     * Log Set
     * @param set
     */
    public static void set(Set set){
        Logger.d(set);
    }

    /**
     * Log Exception
     * @param e
     * @param message
     */
    public static void exception(Exception e, String message){
        Logger.e(e,message);
    }

    /**
     * Use Your tag
     * @param str
     * @return
     */
    public static Printer t(String str){
        return Logger.t(str);
    }

}
