package com.bdkj.wirecrimping.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;


/**
 * Created by bdkj on 2018/10/17.
 */

public class SpUtils {
    private static final String SP_NAME = "all_info";
    private static SpUtils uniqueInstance = null;
    private static SharedPreferences sharedPreferences = null;
    private Context mContext;

    public static SpUtils getInstance(Context mContext) {
        if (uniqueInstance == null) {
            uniqueInstance = new SpUtils();
            sharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return uniqueInstance;
    }

    public void savaString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void savaInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * 将一个8位字节数组转换为双精度浮点数。<br>
     * 注意，函数中不会对字节数组长度进行判断，请自行保证传入参数的正确性。
     *
     * @param b 字节数组
     * @return 双精度浮点数
     */
    public static double bytesToDouble(byte[] b) {
        return Double.longBitsToDouble(bytesToLong(b));
    }

    /**
     * 将一个8位字节数组转换为长整数。<br>
     * 注意，函数中不会对字节数组长度进行判断，请自行保证传入参数的正确性。
     *
     * @param b 字节数组
     * @return 长整数
     */
    public static long bytesToLong(byte[] b) {
        int doubleSize = 4;
        long l = 0;
        for (int i = 0; i < doubleSize; i++) {
            // 如果不强制转换为long，那么默认会当作int，导致最高32位丢失
            l |= ((long) b[i] << (4 * i)) & (0xFFL << (4 * i));
        }

        return l;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b byte数组
     */
    public static String Bytes2hexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            // sb.append(" ");//每个Byte值之间空格分隔
        }
        return sb.toString().toUpperCase().trim();
    }

    public static String hexStr2Str(String hexStr) {
        return hexStr2Str(hexStr, null);
    }

    /**
     * 16进制字符串转换为字符串
     *
     * @param charsetName 用于编码 String 的 Charset
     */
    public static String hexStr2Str(String hexStr, String charsetName) {
        hexStr = hexStr.toUpperCase();
        // hexStr.replace(" ", "");
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xFF);
        }
        String returnStr = "";// 返回的字符串
        if (charsetName == null) {
            // 编译器默认解码指定的 byte 数组，构造一个新的 String,
            // 比如我的集成开发工具即编码器android studio的默认编码格式为"utf-8"
            returnStr = new String(bytes);
        } else {
            // 指定的 charset 解码指定的 byte 数组，构造一个新的 String
            // utf-8中文字符占三个字节，GB18030兼容GBK兼容GB2312中文字符占两个字节，ISO8859-1是拉丁字符（ASCII字符）占一个字节
            try {
                returnStr = new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // charset还有utf-8,gbk随需求改变
        return returnStr;
    }

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }

    /**
     * 字节数组到double的转换.
     */
    public static double getDouble(byte[] b) {
        long m;
        m = b[0];
        m &= 0xff;
        m |= ((long) b[1] << 8);
        m &= 0xffff;
        m |= ((long) b[2] << 16);
        m &= 0xffffff;
        m |= ((long) b[3] << 24);
        m &= 0xffffffffl;
        return Double.longBitsToDouble(m);
    }

}
