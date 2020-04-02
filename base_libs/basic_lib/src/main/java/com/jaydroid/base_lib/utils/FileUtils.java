package com.jaydroid.base_lib.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件工具类
 *
 * @author zhanghao
 * @version 1.0
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * boss文件夹
     */
    private static String DIR = "boss" + File.separator;

    public static File getAutoCacheDir(Context context) {

        if (SDCardUtils.isSDCardEnable())
            return context.getExternalCacheDir();
        return context.getCacheDir();
    }


    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public static File creatSDDir(String dirName) {

        File dir = new File(SDCardUtils.getSDCardPath() + dirName);
        //创建目录是否成功
        if (dir.mkdirs()) {
            return dir;
        } else {
            return null;
        }

    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public static File creatSDFile(String fileName) throws IOException {

        File file = new File(SDCardUtils.getSDCardPath() + SDCardUtils.getSDCardPath() + DIR +
                fileName);
        //创建文件是否成功
        if (file.createNewFile()) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public static boolean isFileExist(String filePath) {

        File file = new File(filePath);
        return file.exists();
    }

    public static void store(File file, byte[] bytes) throws IOException {

        if (!file.exists())
            file.createNewFile();
        FileOutputStream localFileOutputStream = new FileOutputStream(file);
        localFileOutputStream.write(bytes);
        localFileOutputStream.close();
    }

    /**
     * 获取指定文件的字符串
     *
     * @param context   上下文
     * @param file_name 文件名称
     * @return 文件内容
     */
    public static String readFileContent(Context context, String file_name) {

        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources()
                    .getAssets().open(file_name),
                    "UTF-8");
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String file_content = "";
            while ((line = bufReader.readLine()) != null)
                file_content += line + "\n";
            bufReader.close();
            inputReader.close();
            return file_content;
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * 获取文件输入流
     *
     * @param urlStr url
     * @return 文件输入流
     * @throws IOException
     */
    public static InputStream getInputStream(String urlStr) throws IOException {

        URL url = null;
        InputStream input = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            input = conn.getInputStream();
        } catch (MalformedURLException e) {
            L.d(TAG, "MalformedURLException:" + e.toString());//printStackTrace()
        }

        return input;
    }

    /**
     * 获得指定文件的byte数组
     *
     * @param fileName 文件名称
     * @return 指定文件的byte数组
     */
    public static byte[] readFileToByte(String fileName) {

        byte[] buffer = null;
        try {
            File file = new File(SDCardUtils.getSDCardPath() + DIR + fileName);
            FileInputStream inputStream = new FileInputStream(file);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = inputStream.read(b)) != -1) {
                byteStream.write(b, 0, n);
            }
            inputStream.close();
            byteStream.close();
            buffer = byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 文件下载
     *
     * @param urlString url
     * @param fileName  文件名称
     */
    public static void downLoadFile(String urlString, String fileName) {

        if (!SDCardUtils.isSDCardEnable()) {
            ToastUtils.showShort("当前设备无SD卡,无法提供下载!");
            return;
        }
        String filePath = SDCardUtils.getSDCardPath() + DIR + fileName;
        if (isFileExist(filePath)) {
            return;
        }

        /*读取文件流*/
        OutputStream output = null;
        InputStream input = null;
        try {
            output = new FileOutputStream(filePath);
            byte buffer[] = new byte[2 * 1024];
            input = getInputStream(urlString);
            while ((input.read(buffer)) != -1) {
                output.write(buffer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }

                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}