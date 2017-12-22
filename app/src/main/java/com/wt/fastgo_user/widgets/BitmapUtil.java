package com.wt.fastgo_user.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class BitmapUtil {

    // 图片期望尺寸
    private static final int IMAGE_REQUEST_SIZE = 1024;
    // 图片压缩的最大质量
    private static final int IMAGE_COMPRESS_MAX_QUALITY = 80;
    // 最多解码文件次数
    private static final int MAX_DECODE_FILE_COUNT = 10;

    static public Drawable getScaleDraw(String imgPath, Context mContext) {

        Bitmap bitmap = null;
        try {
            File imageFile = new File(imgPath);
            if (!imageFile.exists()) {
                return null;
            }
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imgPath, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, 800 * 480);
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imgPath, opts);

        } catch (OutOfMemoryError err) {

        }
        if (bitmap == null) {
            return null;
        }
        Drawable resizeDrawable = new BitmapDrawable(mContext.getResources(),
                bitmap);
        return resizeDrawable;
    }

    public static void saveMyBitmap(Context mContext, Bitmap bitmap,
                                    String desName) throws IOException {
        FileOutputStream fOut = null;

        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            fOut = mContext.openFileOutput(desName + ".png",
                    Context.MODE_PRIVATE);
        } else {
            File f = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/Asst/cache/" + desName + ".png");
            f.createNewFile();
            fOut = new FileOutputStream(f);
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public Bitmap getScaleBitmap(Resources res, int id) {

        Bitmap bitmap = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, id, opts);

            opts.inSampleSize = computeSampleSize(opts, -1, 800 * 480);
            opts.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeResource(res, id, opts);
        } catch (OutOfMemoryError err) {

        }
        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {

        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;

    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {

        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }

    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                                : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap decodeBitmap(Resources res, int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 通过这个bitmap获取图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeResource(res, id, options);
        if (bitmap == null) {
        }
        float realWidth = options.outWidth;
        float realHeight = options.outHeight;
        // 计算缩放比
        int scale = (int) ((realHeight > realWidth ? realHeight : realWidth) / 100);
        if (scale <= 0) {
            scale = 1;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        // 注意这次要把options.inJustDecodeBounds 设为 false,这次图片是要读取出来的。
        bitmap = BitmapFactory.decodeResource(res, id, options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        return bitmap;
    }

    public static Bitmap getCombineBitmaps(List<MyBitmapEntity> mEntityList,
                                           Bitmap... bitmaps) {
        Bitmap newBitmap = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
        for (int i = 0; i < mEntityList.size(); i++) {
            newBitmap = mixtureBitmap(newBitmap, bitmaps[i], new PointF(
                    mEntityList.get(i).x, mEntityList.get(i).y));
        }
        return newBitmap;
    }

    /**
     * 将多个Bitmap合并成一个图片。
     *
     * @param //int    将多个图合成多少列
     * @param //Bitmap ... 要合成的图片
     * @return
     */
    public static Bitmap combineBitmaps(int columns, Bitmap... bitmaps) {
        if (columns <= 0 || bitmaps == null || bitmaps.length == 0) {
            throw new IllegalArgumentException(
                    "Wrong parameters: columns must > 0 and bitmaps.length must > 0.");
        }
        int maxWidthPerImage = 20;
        int maxHeightPerImage = 20;
        for (Bitmap b : bitmaps) {
            maxWidthPerImage = maxWidthPerImage > b.getWidth() ? maxWidthPerImage
                    : b.getWidth();
            maxHeightPerImage = maxHeightPerImage > b.getHeight() ? maxHeightPerImage
                    : b.getHeight();
        }
        int rows = 0;
        if (columns >= bitmaps.length) {
            rows = 1;
            columns = bitmaps.length;
        } else {
            rows = bitmaps.length % columns == 0 ? bitmaps.length / columns
                    : bitmaps.length / columns + 1;
        }
        Bitmap newBitmap = Bitmap.createBitmap(columns * maxWidthPerImage, rows
                * maxHeightPerImage, Config.ARGB_8888);
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                int index = x * columns + y;
                if (index >= bitmaps.length)
                    break;
                newBitmap = mixtureBitmap(newBitmap, bitmaps[index],
                        new PointF(y * maxWidthPerImage, x * maxHeightPerImage));
            }
        }
        return newBitmap;
    }

    /**
     * Mix two Bitmap as one.
     *
     * @param //bitmapOne
     * @param //bitmapTwo
     * @param //point     where the second bitmap is painted.
     * @return
     */
    public static Bitmap mixtureBitmap(Bitmap first, Bitmap second,
                                       PointF fromPoint) {
        if (first == null || second == null || fromPoint == null) {
            return null;
        }
        Bitmap newBitmap = Bitmap.createBitmap(first.getWidth(),
                first.getHeight(), Config.ARGB_8888);
        Canvas cv = new Canvas(newBitmap);
        cv.drawBitmap(first, 0, 0, null);
        cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return newBitmap;
    }

    public static void getScreenWidthAndHeight(Activity mContext) {
        DisplayMetrics metric = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）
    }

    public static String getNewName(String path, String addStr) {
        String newName = "";
        int lastbackslash = path.lastIndexOf("/");
        newName = path.substring(0, lastbackslash + 1) + addStr + path.substring(lastbackslash + 1, path.length());
        return newName;
    }

    /**
     * 按照传入大小压缩至传入大小以内
     * compressSize:单位KB  传入1将设置为1024B
     * name:原文件路径
     * newName:生成文件路径
     */
    public static void createImageCompress(final String name, final String newName,
                                           final int compressSize) {
        AsyncTask<Object, Object, Object> asyncTask = new AsyncTask<Object, Object, Object>() {

            @Override
            protected Object doInBackground(Object... params) {
                try {
                    File myCaptureFile = new File(newName);
                    if (myCaptureFile.exists() && myCaptureFile.length() > 0) {
                    } else {
                        Bitmap bm = BitmapFactory.decodeFile(name);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int options = 100;
                        bm.compress(Bitmap.CompressFormat.JPEG, options, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
                        while (baos.toByteArray().length / 1024 > compressSize && options >= 10) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                            baos.reset();// 重置baos即清空baos
                            options -= 10;// 每次都减少10
                            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                        }
                        //
                        myCaptureFile.createNewFile();
                        FileOutputStream fos;
                        fos = new FileOutputStream(newName);

                        fos.write(baos.toByteArray());
                        fos.flush();
                        fos.close();
                    }
                } catch (IOException e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
            }

        };
        asyncTask.execute("");
    }

    public static Bitmap getImage(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 压缩图片
     *
     * @param path 源地址
     * @return 压缩后Bitmap
     */
    public static Bitmap compressImage(String path) {
        return compressImage(path, IMAGE_REQUEST_SIZE);
    }

    /**
     * 压缩图片并保存
     *
     * @param srcPath 源地址
     * @return 压缩后图片的路径
     */
    public static String compressImageAndSave(Context context, String srcPath) {
        return compressImageAndSave(context, srcPath, IMAGE_REQUEST_SIZE, false);
    }

    /**
     * 压缩图片并保存
     *
     * @param srcPath  源地址
     * @param isDelSrc 是否删除源文件
     * @return 压缩后图片的路径
     */
    public static String compressImageAndSave(Context context, String srcPath, boolean isDelSrc) {
        return compressImageAndSave(context, srcPath, IMAGE_REQUEST_SIZE, isDelSrc);
    }

    /**
     * 压缩图片
     *
     * @param path
     * @param reqSize
     * @return
     */
    public static Bitmap compressImage(String path, int reqSize) {
        Bitmap targetBitmap = null;
        Bitmap sampleSizeBitmap = null;

        // inSampleSize
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqSize, reqSize);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inDither = false;
        options.inTempStorage = new byte[16 * 1024];
        for (int i = 0; i < MAX_DECODE_FILE_COUNT; i++) {
            try {
                sampleSizeBitmap = BitmapFactory.decodeFile(path, options);
            } catch (OutOfMemoryError error) {
                try {
                    if (null != sampleSizeBitmap && !sampleSizeBitmap.isRecycled()) {
                        sampleSizeBitmap.recycle();
                        sampleSizeBitmap = null;
                    }
                } catch (Exception e) {
                }
                options.inSampleSize *= 2;
                System.gc();
                continue;
            }
            break;
        }
        if (null == sampleSizeBitmap) {
            return null;
        }

        // createBitmap
        final int width = options.outWidth;
        final int height = options.outHeight;
        if (width > reqSize || height > reqSize) {
            int size = width > height ? width : height;
            float scaleSize = ((float) reqSize) / size;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleSize, scaleSize);
            for (int i = 0; i < MAX_DECODE_FILE_COUNT; i++) {
                try {
                    targetBitmap = Bitmap.createBitmap(sampleSizeBitmap, 0, 0, width, height, matrix, true);
                } catch (Exception e) {
                } catch (OutOfMemoryError error) {
                    try {
                        if (null != targetBitmap && !targetBitmap.isRecycled()) {
                            targetBitmap.recycle();
                            targetBitmap = null;
                        }
                    } catch (Exception e) {
                    }
                    scaleSize /= 2;
                    matrix.postScale(scaleSize, scaleSize);
                    System.gc();
                }
            }
            try {
                if (null != sampleSizeBitmap && null != targetBitmap && !sampleSizeBitmap.equals(targetBitmap) && !sampleSizeBitmap.isRecycled()) {
                    sampleSizeBitmap.recycle();
                    sampleSizeBitmap = null;
                }
            } catch (Exception e) {
            }
        }

        if (null == targetBitmap) {
            targetBitmap = sampleSizeBitmap;
        }

        if (null != targetBitmap) {
            int degree = getDegress(path);
            if (degree != 0) {
                targetBitmap = rotateBitmap(targetBitmap, degree);
            }
        }

        return targetBitmap;
    }

    /**
     * 压缩Bitmap并保存
     *
     * @param srcPath  源地址
     * @param reqSize  期望尺寸
     * @param isDelSrc 是否删除源文件
     * @return 压缩后图片的路径
     */
    public static String compressImageAndSave(Context context, String srcPath, int reqSize, boolean isDelSrc) {
        BufferedOutputStream bos = null;
        try {
            File srcFile = new File(srcPath);
            String cacheFileName = StringUtil.md5(srcFile);
            String cacheFilePath = FileUtil.getDiskCacheDirPath(context, FileUtil.CACHE_IMG) + cacheFileName + ".jpg";
            File cacheFile = new File(cacheFilePath);
            // 缓存不存在
            if (!cacheFile.exists()) {
                Bitmap bitmap = compressImage(srcPath, reqSize);
                if (null == bitmap) {
                    return srcPath;
                }

                bos = new BufferedOutputStream(
                        new FileOutputStream(cacheFile));
                bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_COMPRESS_MAX_QUALITY, bos);
                bos.flush();
            }
            if (isDelSrc) {
                srcFile.delete();
            }
            return cacheFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError error) {
            System.gc();
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return srcPath;
    }

    /**
     * 计算缩放比
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * 获取角度
     *
     * @param path 图片路径
     * @return 角度
     */
    public static int getDegress(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转Bitmap
     *
     * @param bitmap  bitmap
     * @param degress 角度
     * @return 旋转后的bitmap
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (null == bitmap) {
            return null;
        }

        Matrix m = new Matrix();
        m.postRotate(degress);
        Bitmap target = null;
        try {
            target = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
        } catch (Exception e) {
        } finally {
            try {
                if (null != bitmap && !bitmap.equals(target) && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                }
            } catch (Exception e) {

            }
        }
        return target;
    }

    /**
     * 保存bitmap到本地目录下
     *
     * @param fileDir 文件路径（包含文件名）
     * @param bitmap
     * @author rico.cheng
     */
    public static void saveBitmap(String fileDir, Bitmap bitmap) {
        FileOutputStream fOut = null;
        try {
            File f = new File(fileDir);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            if (f.exists()) {
                f.delete();
            }
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
            fOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fOut != null)
                    fOut.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 创建视频缩略图
     *
     * @param filePath 视频路径
     * @return 缩略图路径
     */
    public static String createVideoThumbnail(Context context, String filePath, String thumbnailFilename) {
        // MediaMetadataRetriever is available on API Level 8
        // but is hidden until API Level 10
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);

            Bitmap bitmap = null;
            // The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                bitmap = (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                }
                if (bitmap == null) {
                    bitmap = (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
                }
            }
            if (null != bitmap) {
                String path = FileUtil.getDiskCacheDirPath(context, FileUtil.CACHE_IMG) + thumbnailFilename + ".jpg";
                saveBitmap(path, bitmap);
                return path;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

}
