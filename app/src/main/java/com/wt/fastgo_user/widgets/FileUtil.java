package com.wt.fastgo_user.widgets;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileUtil {

	public static final String CACHE_IMG = "img";
	
	public static final int MAX_VIDEO_SIZE = 30;

	public static boolean delete(String file_path) {
        return delete(new File(file_path));
    }

    public static boolean delete(File file) {
        return file.delete();
    }

    public static void copyFile(String sourceFile, String destFile)
            throws IOException {
        copyFile(new File(sourceFile), new File(destFile));
    }

    public static void copyFile(File sourceFile, File destFile)
            throws IOException {
        File parent = destFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    /**
     * 删除目录或文件
     *
     * @param file 目录或文件
     */
    public static void deleteFile(File file) {
        try {
            if (file == null) {
                return;
            }

            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                file.delete();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 删除目录中文件
     *
     * @param file 目录
     */
    public static void deleteDirectoryFile(File file) {
        try {
            if (file == null) {
                return;
            }

            if (file.isFile()) {
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    return;
                }
                for (File f : childFile) {
                    f.delete();
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 获取目录中文件地址
     *
     * @param file 目录
     */
    public static String getDirectoryFilePath(File file, String name) {
        try {
            if (file == null || file.isFile()) {
                return null;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    return null;
                }
                String path = null;
                for (File f : childFile) {
                    if (f.getName().equals(name)) {
                        path = f.getAbsolutePath();
                        break;
                    }
                }
                return path;
            }
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 获取app文件路径
     *
     * @return
     */
    public static String getAppFilePath() {
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                + File.separator
                + "fansz"
                + File.separator;
        return path;
    }

    /**
     * 获取Disk缓存目录
     *
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment
                .getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        File file = new File(cachePath + File.separator + uniqueName + File.separator);
        if (!file.exists()) {
            file.mkdir();
        }

        return file;
    }

    public static String getDiskCacheDirPath(Context context, String uniqueName) {
        File file = getDiskCacheDir(context, uniqueName);
        return file.getAbsolutePath() + File.separator;
    }

    /**
     * 是否可选视频
     *
     * @return
     */
    public static boolean isChoiceVideo(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists() && (file.length() / 1024 / 1024) <= MAX_VIDEO_SIZE) {
                return true;
            }
        }

        return false;
    }

    /**
     * 是否存在文件
     *
     * @return
     */
    public static boolean isExistsFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                return true;
            }
        }

        return false;
    }

}
