package com.bdkj.wirecrimping;

import android.app.Application;
import android.content.Context;

import com.bdkj.wirecrimping.activity.ActivityManager;
import com.vise.baseble.ViseBle;


public class MyApplication extends Application {

    /**
     * 全局唯一实例
     */
    public static MyApplication instance;
    public Context mContext = null;
    private ActivityManager activityManager;

    public static MyApplication getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        activityManager = ActivityManager.getInstance();
        //蓝牙相关配置修改
        ViseBle.config()
                .setScanTimeout(-1)//扫描超时时间，-1表示永久扫描
                .setConnectTimeout(10 * 1000)//连接超时时间
                .setOperateTimeout(5 * 1000)//设置数据操作超时时间
                .setConnectRetryCount(3)//设置连接失败重试次数
                .setConnectRetryInterval(1000)//设置连接失败重试间隔时间
                .setOperateRetryCount(3)//设置数据操作失败重试次数
                .setOperateRetryInterval(1000)//设置数据操作失败重试间隔时间
                .setMaxConnectCount(1);//设置最大连接设备数量
        //蓝牙信息初始化，全局唯一，必须在应用初始化时调用
        ViseBle.getInstance().init(this);

    }


    public Context getContext() {
        return mContext;
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }

}
