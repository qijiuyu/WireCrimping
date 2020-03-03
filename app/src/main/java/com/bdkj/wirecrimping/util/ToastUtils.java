package com.bdkj.wirecrimping.util;

import android.widget.Toast;

/**
 * 吐司工具
 * Created by _Ms on 2016/8/20.
 */
public class ToastUtils {

    /**
     * Toast
     */
    private static Toast mToast;

    /**
     * 短时显示Toast
     * @param msg    msg
     */
    public static void showShort(final CharSequence msg) {
        show(Toast.LENGTH_SHORT, msg);
    }

    /**
     * 长时显示Toast
     * @param msg    msg
     */
    public static void showLong(final CharSequence msg) {
        show(Toast.LENGTH_LONG, msg);
    }

    /**
     * 实时显示内容的吐司
     *
     * @param msg 被显示内容
     */
    public static void show(final int duration, final CharSequence msg) {
        CheckUtils.notNull(msg);

        /*
        封装Toast.show代码块
         */
        Runnable runnable =  new Runnable() {
            @Override
            public void run() {

                // 处理Toast
                if (mToast == null) {
                    mToast = Toast.makeText(UiUtils.getContext(), msg.toString(), duration);
                } else {
                    mToast.setText(msg.toString());
                }

                mToast.show();
            }
        };

        ThreadUtils.compatRunOnUiThread(runnable);
        runnable = null;
    }

    /**
     * Toast显示内容
     * @param msg    被显示的Message
     */
    public static void showText(final CharSequence msg) {

        /*
        封装Toast.show代码块
         */
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(UiUtils.getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        };

        ThreadUtils.compatRunOnUiThread(runnable);
        runnable = null;
    }

}
