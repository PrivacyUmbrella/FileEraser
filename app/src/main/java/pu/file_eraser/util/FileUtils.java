package pu.file_eraser.util;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import pu.file_eraser.global.Constant;

public class FileUtils {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "FileUtils";

    private static final int BUFFER_SIZE = 10 * 1024;    //10KiB

    /**
     * 擦除文件信息
     * 用00填充文件内容
     * 为了保证写入的偏移位置和源文件相同，只能用RandomAccessFile而不是用FileChannel
     *
     * @param path 单个文件的路径
     * @return 是否成功
     */
    private static boolean eraseSingleFile(String path) {
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            Log.w(TAG, "eraseSingleFile: " + path + " does not exists or it's a directory!");
            return false;
        }
        long size = file.length();
//        Log.d(TAG, "eraseSingleFile: size=" + size);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            byte[] buffer = new byte[BUFFER_SIZE];
            if (size <= BUFFER_SIZE) for (int i = 0; i < size; i++) raf.write(0);
            else {
                while (raf.getFilePointer() < size - BUFFER_SIZE) raf.write(buffer);
                for (long i = raf.getFilePointer(); i < size; i++) raf.write(0);
            }
            return true;
        } catch (IOException e) {
            Log.e(TAG, "eraseSingleFile: ", e);
            return false;
        }
    }

    private static void traverseDirectory(String path, Runnable executorForSingleFile) {
        File file = new File(path);
        if (file.isFile()) executorForSingleFile.run();
        else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                if (files.length > 0) {
                    for (File f : files) {
                        if (f.isDirectory())
                            traverseDirectory(f.getAbsolutePath(), executorForSingleFile);
                        else if (f.isFile()) executorForSingleFile.run();
                    }
                } else {
                    // 空目录
                    executorForSingleFile.run();
                }
            } else Log.w(TAG, "traverseDirectory: Neither file nor directory...What's this?");
        }
    }

    public static boolean erase(final String path) {
        final boolean[] retVal = new boolean[]{true};
        traverseDirectory(path, () -> {
            boolean val = eraseSingleFile(path);
            if (retVal[0]) retVal[0] = val;
        });
        return retVal[0];
    }
}
