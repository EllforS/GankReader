package com.ellfors.gankreader.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * 文件工具类
 */
public class FileUtil
{
    private static final String APP_FILES_DIR = "/gankreader";
    public static final String DOWN_LOAD = "/download/";                //下载APK路径
    public static final String IMAGE_SAVE_DIR = "/images/";             //之前图片压缩目录
    public static final String FILE_IMAGE_SAVE_DIR = "/file_images/";   //当前图片压缩目录

    /**
     * sdcard是否可读写
     */
    public static boolean isCanUseSdCard()
    {
        try
        {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 得到储存路径
     *
     * @param path 传入存入的路径名，不需要传空字符串
     * @return
     */
    public static String getPath(String path)
    {
        String mPath;
        if (isCanUseSdCard())
        {
            mPath = Environment.getExternalStorageDirectory()
                    + APP_FILES_DIR + (TextUtils.isEmpty(path) ? "/" : "/" + path);
        }
        else
        {
            return "";
        }
        //创建目录
        new File(mPath).mkdirs();
        return mPath;
    }

    /**
     * 获取下载Apk路径
     */
    public static String getDownLoadApkPath()
    {
        String path = Environment.getExternalStorageDirectory() + APP_FILES_DIR + DOWN_LOAD;
        File file = new File(path);
        file.mkdirs();
        return path;
    }

    /**
     * @param apkVersion apk versionName
     * @return apkName
     */
    public static String getApkName(String apkVersion)
    {
        return "tjxs_release_v" + apkVersion + ".apk";
    }

    /**
     * 清空文件目录下，所有文件
     */
    public static void deleteAllFiles(File root)
    {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files)
            {
                if (f.isDirectory())
                {
                    // 判断是否为文件夹
                    deleteAllFiles(f);
                    try
                    {
                        f.delete();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    if (f.exists())
                    {
                        // 判断是否存在
                        deleteAllFiles(f);
                        try
                        {
                            f.delete();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
    }

    /**
     * 判断文件是否已创建
     */
    public static boolean fileExist(String fileStr)
    {
        if (!TextUtils.isEmpty(fileStr))
        {
            File file = new File(fileStr);
            return file.exists();
        }
        return false;
    }

    /**
     * 递归删除文件
     *
     * @param dir     需要删除的文件夹
     * @param delSelf 自身是否删除
     * @return 删除的文件数
     **/
    public static long deleteFilesRecursively(File dir, boolean delSelf)
    {
        long length = 0;
        if (dir.exists())
        {
            File[] files = dir.listFiles();
            if (files != null)
            {
                for (int i = 0; i < files.length; ++i)
                {
                    File f = files[i];
                    if (f.isDirectory())
                    {
                        deleteFilesRecursively(f, true);
                    }
                    else
                    {
                        length += f.length();
                        f.delete();
                    }
                }
            }
        }
        if (delSelf)
            dir.delete();
        return length;
    }

    /**
     * 写入文件
     */
    public static void writeFile(String path, String content)
    {
        try
        {
            File file = new File(path);
            if (file.exists())
            {
                file.delete();
            }
            file.createNewFile();

            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(content);
            output.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 读取assests下文件中的字符串
     */
    public static String getFromAssets(String fileName, Context context)
    {
        try
        {
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 适配android 7.0 打开文件
     */
    public static void openFile(Activity mContext, File file)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, "com.kingstarit.tjxs.fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }
        else
        {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);
    }
}
