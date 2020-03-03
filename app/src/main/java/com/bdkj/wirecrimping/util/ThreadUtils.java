package com.bdkj.wirecrimping.util;

import android.os.Handler;
import android.os.Looper;


/**
 * 线程工具类
 * Created by _Ms on 2016/8/22.
 */
public class ThreadUtils {

    private static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 运行在子线程
     * @param runnable    代码块
     */
    public static void runOnBackground(Runnable runnable) {
        ThreadPoolManager.createThreadPool().execute(runnable);
    }

    /**
     * 运行在主线程
     * @param runnable    代码块
     */
    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    /**
     * 兼容子线程运行到主线程
     * @param runnable    代码块
     */
    public static void compatRunOnUiThread(Runnable runnable) {

        if (Thread.currentThread().getId() == 1) {
            runnable.run();
        } else {
            ThreadUtils.runOnUiThread(runnable);
        }
    }

}
