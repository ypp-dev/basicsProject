package com.ypp.basicsproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Added by Tom on 2024/06/25.
 * 采用单例设计模式，异常的捕捉类。
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "ExceptionCrashHandler";
    private static ExceptionCrashHandler mInstance;
    // 系统默认的。
    private Thread.UncaughtExceptionHandler mDefaultHandler;


    public static ExceptionCrashHandler getInstance() {
        if (mInstance == null) {
            // 解决多并发问题
            synchronized (ExceptionCrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new ExceptionCrashHandler();
                }
            }
        }
        return mInstance;
    }

    private Context mContext; // 用于获取应用的一些信息，
    public void init(Context context) {
        this.mContext = context;
        // 设置全局异常类 ，是本类。
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefaultHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "捕捉到了异常");
        // 1. 获取信息
        // 1.1 崩溃信息
        // 1.2 手机信息
        // 1.3 版本信息
        // 2.写入文件
        String crashFileName = saveInfoToSD(ex);
        Log.e(TAG, "fileName --> " + crashFileName);

        // 3. 缓存崩溃日志文件
        cacheCrashFile(crashFileName);
        // 系统默认处理
        mDefaultHandler.uncaughtException(thread, ex);
//        Looper.loop();
    }

    // 缓存崩溃日志文件
    private void cacheCrashFile(String crashFileName) {
        SharedPreferences sp = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        sp.edit().putString("CRASH_FILE_NAME", crashFileName).commit();
    }
    // 获取崩溃文件名称
    public File getCrashFile() {
        SharedPreferences sp = mContext.getSharedPreferences("crash", Context.MODE_PRIVATE);
        String crashFileName = sp.getString("CRASH_FILE_NAME", "");
        return new File(crashFileName);
    }

    /**
     * 保存获取的 软件信息，设备信息和出错信息到sdcard中。
     *
     * @param ex
     * @return
     */
    private String saveInfoToSD(Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        // 1.收集信息+应用信息: obtainSimpleInfo
        for (Map.Entry<String, String> entry : obtainSimpleInfo(mContext)
                .entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }
        // 2.崩溃的详细信息
        sb.append(obtainExceptionInfo(ex));
        // 保存到手机应用的目录，没有拿收集sdCard目录。6.0+要动态申请权限，才能拿到sdCard的根目录。
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(mContext.getFilesDir() + File.separator + "crash"
                    + File.separator);

            // 先删除之前的异常信息
            if (dir.exists()) {
                deleteDir(dir); // 删除该目录下的所有子文件。
            }
            // 再重新创建文件夹
            if (!dir.exists()) {
                dir.mkdir();
            }
            try {
                fileName = dir.toString()
                        + File.separator
                        + getAssignTime("yyyy_MM_dd_HH_mm") + ".txt";
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 返回当前日期根据格式
     **/
    private String getAssignTime(String dateFormatStr) {
        DateFormat dataFormat = new SimpleDateFormat(dateFormatStr);
        long currentTime = System.currentTimeMillis();
        return dataFormat.format(currentTime);
    }

    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     *
     * @return
     */
    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", "" + mPackageInfo.versionCode);
        map.put("MODEL", "" + Build.MODEL);
        map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
        map.put("PRODUCT", "" + Build.PRODUCT);
        map.put("MOBLE_INFO", getMobileInfo());
        return map;
    }

    /**
     * Cell phone information
     * 通过类反射，获取手机的信息。
     * @return
     */
    public static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) { // 遍历所有属性
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取系统未捕捉的错误信息
     *
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     * deletion fails, the method stops attempting to delete and returns
     * "false".
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return true;
    }
}