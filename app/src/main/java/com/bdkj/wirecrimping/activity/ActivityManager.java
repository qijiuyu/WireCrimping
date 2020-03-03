package com.bdkj.wirecrimping.activity;

import android.app.Activity;

import java.util.Stack;

/**
 * activity管理类<p>
 * 作者 sunxiaozhen <br>
 * 创建日期 2017-06-12<p>
 * 修改者，修改日期，修改内容。
 */
public class ActivityManager {

    /**
     * 接收activity的Stack
     */
    private static Stack<Activity> activityStack = null;
    private static ActivityManager activityManager = new ActivityManager();

    public ActivityManager(){}

    /**
     * 单实例
     *
     * @return
     */
    public static ActivityManager getInstance() {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        return activityManager;
    }


    /**
     * 将activity移出栈
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定activity
     *
     * @param activity
     */
    public void endActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack == null||activityStack.isEmpty()) {
            return;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                endActivity(activity);
            }
        }
    }

    /**
     * 获得当前的activity(即最上层)
     *
     * @return
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty())
            activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 将activity推入栈内
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 弹出除cls外的所有activity
     *
     * @param cls
     */
    public void popAllActivityExceptOne(Class<? extends Activity> cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 结束除cls之外的所有activity,执行结果都会清空Stack
     *
     * @param cls
     */
    public void finishAllActivityExceptOne(Class<? extends Activity> cls) {
        while (!activityStack.empty()) {
            Activity activity = currentActivity();
            if (activity.getClass().equals(cls)) {
                popActivity(activity);
            } else {
                endActivity(activity);
            }
        }
    }

    /**
     * 结束所有activity
     */
    public void finishAllActivity() {
        while (!activityStack.empty()) {
            Activity activity = currentActivity();
            endActivity(activity);
        }
    }

}
