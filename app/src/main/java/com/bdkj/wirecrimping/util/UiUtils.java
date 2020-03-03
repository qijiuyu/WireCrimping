package com.bdkj.wirecrimping.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.bdkj.wirecrimping.MyApplication;


/**
 * Ui工具类
 * Created by _Ms on 2016/8/20.
 */

public class UiUtils {

    /**
     * Context
     * @return contest
     */
    public static Context getContext() {
        return MyApplication.getApplication().getContext();
    }

    /**
     * 获取Resources
     * @return Resource获取对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * id > String
     * @param id    id
     * @return String
     */
    public static String getString(int id) {
        return getResources().getString(id);
    }

    /**
     * id > stringArray
     * @param id    id
     * @return stringArray
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }

    /**
     * id > hexColor
     * @param colorId    id
     * @return hexColor
     */
    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    /**
     * id > layoutView
     * @param layoutResourceId    id
     * @return  view
     */
    public static View inflate(int layoutResourceId) {
        return View.inflate(getContext(), layoutResourceId, null);
    }

    /**
     * dp > px
     * @param dp    dp
     * @return px
     */
    public static int dp2px(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px > dp
     * @param px px
     * @return dp
     */
    public static int px2dp(int px) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * id > dimens > px
     * @param id    id
     * @return dimens > px
     */
    public static int getDimens(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    /**
     * id > drawable
     * @param id
     * @return drawable
     */
    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

}
