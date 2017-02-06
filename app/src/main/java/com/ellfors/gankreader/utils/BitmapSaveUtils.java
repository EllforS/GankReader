package com.ellfors.gankreader.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 保存图片到本地图库
 */
public class BitmapSaveUtils {
    private static final String TAG = "BitmapSaveUtils";
    private static String errorMessage;

    /**
     * 创建本地文件夹
     */
    public static void createFile(String SavePath) {
        // 文件
        String FilePath = getSDCardPath() + SavePath;
        File file = new File(FilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获取SDCard的目录路径功能
     *
     * @return
     */
    public static String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }

    /**
     * 保存图片到SD卡 并通知图库更新
     *
     * @param context
     * @param bmp
     * @param savePath
     * @return
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp, String savePath) {
        errorMessage = "";
        // 首先保存图片
        String FilePath = getSDCardPath() + savePath;
        File appDir = new File(FilePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            errorMessage += e.getMessage();
            Log.e(TAG, "saveImageToGallery: " + e.getMessage());
        } catch (IOException e) {
            errorMessage += e.getMessage();
            Log.e(TAG, "saveImageToGallery: " + e.getMessage());
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            errorMessage += e.getMessage();
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));

        //回收内存
        if (!bmp.isRecycled()) {
            bmp.recycle();
            System.gc();
        }

        return TextUtils.isEmpty(errorMessage);
    }
}
