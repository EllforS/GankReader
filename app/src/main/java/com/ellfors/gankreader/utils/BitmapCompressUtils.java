package com.ellfors.gankreader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片压缩工具类
 */
public class BitmapCompressUtils
{
    /**
     * 压缩图片
     */
    public static File getCompressImage(String pic_path)
    {
        // 创建目录
        File imageDir = new File(FileUtil.getPath(FileUtil.FILE_IMAGE_SAVE_DIR));
        imageDir.mkdirs();// 会自动判断
        String targetPath;
        if ("gif".equalsIgnoreCase(getExt(pic_path)))
            return new File(pic_path);
        else
        {
            targetPath = imageDir.getAbsolutePath()
                    + "/"
                    + pic_path.substring(pic_path.lastIndexOf("/") + 1,
                    pic_path.lastIndexOf(".")) + ".jpg";
        }
        //调用压缩图片的方法，返回压缩后的图片path
        final File compressImage = compressImage(pic_path, targetPath, 75);
        if (compressImage.exists())
            return compressImage;
        else
            return new File(pic_path);
    }

    /**
     * 获得文件后缀
     */
    private static String getExt(String filename)
    {
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

    private static File compressImage(String filePath, String targetPath, int quality)
    {
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if (degree != 0)
        {
            //旋转照片角度，防止头像横着显示
            bm = rotateBitmap(bm, degree);
        }
        File outputFile = new File(targetPath);
        try
        {
            if (!outputFile.exists())
            {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            }
            else
            {
                return outputFile;
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            if (bm != null && bm.compress(Bitmap.CompressFormat.JPEG, quality, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (bm != null && !bm.isRecycled())
        {
            bm.recycle();  //回收图片所占的内存
            System.gc(); //提醒系统及时回收
        }
        return outputFile;
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    private static Bitmap getSmallBitmap(String filePath)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * 获取照片角度
     */
    private static int readPictureDegree(String path)
    {
        int degree = 0;
        try
        {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     */
    private static Bitmap rotateBitmap(Bitmap bitmap, int degress)
    {
        if (bitmap != null)
        {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return null;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth)
        {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 清除压缩图片
     */
    public static void clearCompressImages()
    {
        File imageDir = new File(FileUtil.getPath(FileUtil.FILE_IMAGE_SAVE_DIR));
        FileUtil.deleteFilesRecursively(imageDir, true);
    }
}
