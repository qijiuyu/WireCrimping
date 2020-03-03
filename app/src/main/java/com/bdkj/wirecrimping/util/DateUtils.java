package com.bdkj.wirecrimping.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    //获取当前日期和时间
    public static String getCurrentDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}
