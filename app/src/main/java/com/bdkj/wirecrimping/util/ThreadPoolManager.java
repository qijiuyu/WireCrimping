package com.bdkj.wirecrimping.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程管理类
 * Created by _Ms on 2016/8/23.
 */
public class ThreadPoolManager {

    private static ThreadPoolManager mThreadPoolManager;
    private static ThreadProxy mThreadProxy;
    private static ThreadProxy mLongThreadProxy;

    /**
     * 私有构造
     */
    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance() {

        if (mThreadPoolManager == null) {
            synchronized (ThreadPoolManager.class) {
                if (mThreadPoolManager == null) {
                    mThreadPoolManager = new ThreadPoolManager();
                }
            }
        }
        return mThreadPoolManager;
    }

    /**
     * 创建普通线程代理
     * 读写文件，请求服务器数据
     * @return 线普通程代理
     */
    public static synchronized ThreadProxy createThreadPool() {
        if (mThreadProxy == null) {
            mThreadProxy = new ThreadProxy(3, 3, 5000);
        }
        return mThreadProxy;
    }

    /**
     * 读写文件
     * @return
     */
    public static synchronized ThreadProxy createLongThreadPool() {
        if (mLongThreadProxy == null) {
            mLongThreadProxy = new ThreadProxy(5, 5, 5000);
        }
        return mLongThreadProxy;
    }

    public static class ThreadProxy {

        private int mCorePoolSize;
        private int mMaximumPoolSize;
        private long mKeepAliveTime;
        private ThreadPoolExecutor mThreadPoolExecutor;

        public ThreadProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {

            mCorePoolSize = corePoolSize;
            mMaximumPoolSize = maximumPoolSize;
            mKeepAliveTime = keepAliveTime;

        }

        /**
         * 执行线程任务
         * @param runnable
         */
        public void execute(Runnable runnable) {
            if (mThreadPoolExecutor == null) {
                mThreadPoolExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize, mKeepAliveTime, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<Runnable>());
            }
            mThreadPoolExecutor.execute(runnable);
        }

        /**
         * 取消线程任务
         * @param runnable
         */
        public void cancel(Runnable runnable) {
            if (mThreadPoolExecutor != null && !mThreadPoolExecutor.isShutdown() && !mThreadPoolExecutor.isTerminated()) {
                mThreadPoolExecutor.remove(runnable);
            }
        }

    }
}
