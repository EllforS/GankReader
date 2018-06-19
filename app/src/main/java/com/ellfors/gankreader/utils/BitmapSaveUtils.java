package com.ellfors.gankreader.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static android.content.ContentValues.TAG;

/**
 * 保存图片到本地图库
 */
public class BitmapSaveUtils
{
    private static String errorMessage;

    /**
     * 保存图片到SD卡 并通知图库更新
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp)
    {
        errorMessage = "";
        // 首先保存图片
        File appDir = new File(FileUtil.getPath(FileUtil.IMAGE_SAVE_DIR));
        appDir.mkdirs();
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        }
        catch (Exception e)
        {
            errorMessage += e.getMessage();
            Log.e(TAG, "saveImageToGallery: " + e.getMessage());
        }
        // 其次把文件插入到系统图库
        try
        {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        }
        catch (FileNotFoundException e)
        {
            errorMessage += e.getMessage();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        //回收内存
        if (!bmp.isRecycled())
        {
            bmp.recycle();
            System.gc();
        }
        return TextUtils.isEmpty(errorMessage);
    }
}
