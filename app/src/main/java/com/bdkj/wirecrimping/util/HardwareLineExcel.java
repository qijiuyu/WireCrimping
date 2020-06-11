package com.bdkj.wirecrimping.util;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.bdkj.wirecrimping.bean.HardwareTwoBean;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HardwareLineExcel {
    public static void rwExcel(String filePath, String newFilePath, HardwareTwoBean hardwareTwoBean, String sign) {
        Workbook workBook = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        Bitmap bitmap=null;
        try {
            boolean isFiled = null != filePath && !filePath.equals("")
                    && filePath.substring(filePath.length() - 4, filePath.length()).equals(".xls");
            if (isFiled) {
                // 读取指定路径下的excel
                fis = new FileInputStream(filePath);
                // 加载到workBook
                workBook = new HSSFWorkbook(fis);
            } else {
                System.out.println("模板文件格式不正确，请使用.xls文件！");
                System.exit(1);
            }
            // 获取第一个sheet页
            org.apache.poi.ss.usermodel.Sheet sheetAt = workBook.getSheetAt(0);
            CellStyle style = workBook.createCellStyle();
            // 创建字体
            Font font = workBook.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 11);// 字体大小
            // 设置字体的颜色
            font.setColor(Font.COLOR_RED);
            style.setFont(font);
            // 设置上边框
            style.setBorderTop(BorderStyle.THIN);
            // 垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setAlignment(HorizontalAlignment.CENTER);
            // 获取第2行 第3列的单位格
            Row row = sheetAt.getRow(1);
            Cell cell = row.getCell(2);
            // 设置值
            cell.setCellValue(hardwareTwoBean.getProName());
            cell.setCellStyle(style);

            if ("3".equals(sign)) {
                // 获取第3行 第3列的数据
                row = sheetAt.getRow(2);
                cell = row.getCell(2);
                cell.setCellValue(hardwareTwoBean.getZhuanghaoStr());
                cell.setCellStyle(style);
                for (int i = 0; i < hardwareTwoBean.getTahao().size(); i++) {
                    if (i == 0) {
                        // 获取第3行 第10列的数据
                        row = sheetAt.getRow(2);
                        cell = row.getCell(9);
                        cell.setCellValue(hardwareTwoBean.getTahao().get(i));
                        cell.setCellStyle(style);
                    } else {
                        // 获取第3行 第12列的数据
                        row = sheetAt.getRow(2);
                        cell = row.getCell(11);
                        cell.setCellValue(hardwareTwoBean.getTahao().get(i));
                        cell.setCellStyle(style);
                    }
                }
                // 获取第3行 第15列的数据
                row = sheetAt.getRow(2);
                cell = row.getCell(14);
                cell.setCellValue(hardwareTwoBean.getDaoxian());
                cell.setCellStyle(style);
                // 获取第3行 第18列的数据
                row = sheetAt.getRow(2);
                cell = row.getCell(17);
                cell.setCellValue(hardwareTwoBean.getDate());
                cell.setCellStyle(style);
                // 获取第7 - 31行 第1列的数据
                int aa = 6;
                for (int i = 0; i < hardwareTwoBean.getShigongzhuanghao().size(); i++) {
                    row = sheetAt.getRow(aa);
                    cell = row.getCell(0);
                    cell.setCellValue(hardwareTwoBean.getShigongzhuanghao().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    aa = aa + 1;
                }
                // 获取第7 - 31行 第2列的数据
                int bb = 6;
                for (int i = 0; i < hardwareTwoBean.getMaxOrmin().size(); i++) {
                    row = sheetAt.getRow(bb);
                    cell = row.getCell(1);
                    cell.setCellValue(hardwareTwoBean.getMaxOrmin().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    bb = bb + 1;
                }
                // 获取第7 - 31行 第3列的数据
                int cc = 6;
                for (int i = 0; i < hardwareTwoBean.getXiangbie().size(); i++) {
                    row = sheetAt.getRow(cc);
                    cell = row.getCell(2);
                    cell.setCellValue(hardwareTwoBean.getXiangbie().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    cc = cc + 1;
                }
                // 获取第7 - 31行 第4列的数据
                int dd = 6;
                for (int i = 0; i < hardwareTwoBean.getXianbie().size(); i++) {
                    row = sheetAt.getRow(dd);
                    cell = row.getCell(3);
                    cell.setCellValue(hardwareTwoBean.getXianbie().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    dd = dd + 1;
                }
                // 获取第7 - 31行 第5列的数据
                int ee = 6;
                for (int i = 0; i < hardwareTwoBean.getLyqminmax().size(); i++) {
                    row = sheetAt.getRow(ee);
                    cell = row.getCell(4);
                    cell.setCellValue(hardwareTwoBean.getLyqminmax().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    ee = ee + 1;
                }
                // 获取第7 - 31行 第6列的数据
                int ff = 6;
                for (int i = 0; i < hardwareTwoBean.getLyqminmin().size(); i++) {
                    row = sheetAt.getRow(ff);
                    cell = row.getCell(5);
                    cell.setCellValue(hardwareTwoBean.getLyqminmin().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    ff = ff + 1;
                }
                // 获取第7 - 31行 第7列的数据
                int gg = 6;
                for (int i = 0; i < hardwareTwoBean.getLqlength().size(); i++) {
                    row = sheetAt.getRow(gg);
                    cell = row.getCell(6);
                    cell.setCellValue(hardwareTwoBean.getLqlength().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    gg = gg + 1;
                }
                // 获取第7 - 31行 第8列的数据
                int hh = 6;
                for (int i = 0; i < hardwareTwoBean.getGyqminmax().size(); i++) {
                    row = sheetAt.getRow(hh);
                    cell = row.getCell(7);
                    cell.setCellValue(hardwareTwoBean.getGyqminmax().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    hh = hh + 1;
                }
                // 获取第7 - 31行 第9列的数据
                int kk = 6;
                for (int i = 0; i < hardwareTwoBean.getGyqminmin().size(); i++) {
                    row = sheetAt.getRow(kk);
                    cell = row.getCell(8);
                    cell.setCellValue(hardwareTwoBean.getGyqminmin().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    kk = kk + 1;
                }
                // 获取第7 - 31行 第10列的数据
                int ll = 6;
                for (int i = 0; i < hardwareTwoBean.getGqlength().size(); i++) {
                    row = sheetAt.getRow(ll);
                    cell = row.getCell(9);
                    cell.setCellValue(hardwareTwoBean.getGqlength().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    ll = ll + 1;
                }
                // 获取第7 - 31行 第11列的数据
                int mm = 6;
                for (int i = 0; i < hardwareTwoBean.getLyhminmax().size(); i++) {
                    row = sheetAt.getRow(mm);
                    cell = row.getCell(10);
                    cell.setCellValue(hardwareTwoBean.getLyhminmax().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    mm = mm + 1;
                }
                // 获取第7 - 31行 第12列的数据
                int nn = 6;
                for (int i = 0; i < hardwareTwoBean.getLyhminmin().size(); i++) {
                    row = sheetAt.getRow(nn);
                    cell = row.getCell(11);
                    cell.setCellValue(hardwareTwoBean.getLyhminmin().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    nn = nn + 1;
                }
                // 获取第7 - 31行 第13列的数据
                int oo = 6;
                for (int i = 0; i < hardwareTwoBean.getLhlength().size(); i++) {
                    row = sheetAt.getRow(oo);
                    cell = row.getCell(12);
                    cell.setCellValue(hardwareTwoBean.getLhlength().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    oo = oo + 1;
                }
                // 获取第7 - 31行 第14列的数据
                int pp = 6;
                for (int i = 0; i < hardwareTwoBean.getGyhminmax().size(); i++) {
                    row = sheetAt.getRow(pp);
                    cell = row.getCell(13);
                    cell.setCellValue(hardwareTwoBean.getGyhminmax().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    pp = pp + 1;
                }
                // 获取第7 - 31行 第15列的数据
                int qq = 6;
                for (int i = 0; i < hardwareTwoBean.getGyhminmin().size(); i++) {
                    row = sheetAt.getRow(qq);
                    cell = row.getCell(14);
                    cell.setCellValue(hardwareTwoBean.getGyhminmin().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    qq = qq + 1;
                }
                // 获取第7 - 31行 第16列的数据
                int rr = 6;
                for (int i = 0; i < hardwareTwoBean.getGhlength().size(); i++) {
                    row = sheetAt.getRow(rr);
                    cell = row.getCell(15);
                    cell.setCellValue(hardwareTwoBean.getGhlength().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    rr = rr + 1;
                }
                // 获取第7 - 31行 第17列的数据
                int ss = 6;
                for (int i = 0; i < hardwareTwoBean.getOutercheck().size(); i++) {
                    row = sheetAt.getRow(ss);
                    cell = row.getCell(16);
                    cell.setCellValue(hardwareTwoBean.getOutercheck().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    ss = ss + 1;
                }
                // 获取第7 - 31行 第18列的数据
                int tt = 6;
                for (int i = 0; i < hardwareTwoBean.getYajieren().size(); i++) {
                    row = sheetAt.getRow(tt);
                    cell = row.getCell(17);
                    cell.setCellValue(hardwareTwoBean.getYajieren().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    tt = tt + 1;
                }
                // 获取第7 - 31行 第19列的数据
                int uu = 6;
                for (int i = 0; i < hardwareTwoBean.getDaihao().size(); i++) {
                    row = sheetAt.getRow(uu);
                    cell = row.getCell(18);
                    cell.setCellValue(hardwareTwoBean.getDaihao().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    uu = uu + 1;
                }
                // 获取第7 - 31行 第20列的数据
                int vv = 6;
//                for (int i = 0; i < hardwareTwoBean.getPingding().size(); i++) {
//                    row = sheetAt.getRow(vv);
//                    cell = row.getCell(19);
//                    cell.setCellValue(hardwareTwoBean.getPingding().get(i));
//                    style.setBorderBottom(BorderStyle.THIN);
//                    style.setBorderRight(BorderStyle.THIN);
//                    cell.setCellStyle(style);
//                    vv = vv + 1;
//                }


            } else {
                for (int i = 0; i < hardwareTwoBean.getZhuanghao().size(); i++) {
                    if (i == 0) {
                        // 获取第3行 第3列的数据
                        row = sheetAt.getRow(2);
                        cell = row.getCell(2);
                        cell.setCellValue(hardwareTwoBean.getZhuanghao().get(i));
                        cell.setCellStyle(style);
                    } else {
                        // 获取第3行 第5列的数据
                        row = sheetAt.getRow(2);
                        cell = row.getCell(4);
                        cell.setCellValue(hardwareTwoBean.getZhuanghao().get(i));
                        cell.setCellStyle(style);
                    }
                }

                for (int i = 0; i < hardwareTwoBean.getTahao().size(); i++) {
                    if (i == 0) {
                        // 获取第3行 第10列的数据
                        row = sheetAt.getRow(2);
                        cell = row.getCell(9);
                        cell.setCellValue(hardwareTwoBean.getTahao().get(i));
                        cell.setCellStyle(style);
                    } else {
                        // 获取第3行 第12列的数据
                        row = sheetAt.getRow(2);
                        cell = row.getCell(11);
                        cell.setCellValue(hardwareTwoBean.getTahao().get(i));
                        cell.setCellStyle(style);
                    }
                }
                // 获取第3行 第15列的数据
                row = sheetAt.getRow(2);
                cell = row.getCell(14);
                cell.setCellValue(hardwareTwoBean.getDaoxian());
                cell.setCellStyle(style);
                // 获取第3行 第18列的数据
                row = sheetAt.getRow(2);
                cell = row.getCell(17);
                cell.setCellValue(hardwareTwoBean.getDate());
                cell.setCellStyle(style);

                // 获取第7 - 31行 第1列的数据
                int aa = 6;
                for (int i = 0; i < hardwareTwoBean.getYajieguanweizhi().size(); i++) {
                    row = sheetAt.getRow(aa);
                    cell = row.getCell(0);
                    cell.setCellValue(hardwareTwoBean.getYajieguanweizhi().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    aa = aa + 1;
                }
                // 获取第7 - 31行 第2列的数据
                int cc = 6;
                for (int i = 0; i < hardwareTwoBean.getXiangbie().size(); i++) {
                    row = sheetAt.getRow(cc);
                    cell = row.getCell(1);
                    cell.setCellValue(hardwareTwoBean.getXiangbie().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    cc = cc + 1;
                }
                // 获取第7 - 31行 第3列的数据
                int dd = 6;
                for (int i = 0; i < hardwareTwoBean.getXianbie().size(); i++) {
                    row = sheetAt.getRow(dd);
                    cell = row.getCell(2);
                    cell.setCellValue(hardwareTwoBean.getXianbie().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    dd = dd + 1;
                }
                // 获取第7 - 31行 第4列的数据
                int ee = 6;
                for (int i = 0; i < hardwareTwoBean.getLyqminmax().size(); i++) {
                    row = sheetAt.getRow(ee);
                    cell = row.getCell(3);
                    cell.setCellValue(hardwareTwoBean.getLyqminmax().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    ee = ee + 1;
                }
                // 获取第7 - 31行 5列的数据
                int ff = 6;
                for (int i = 0; i < hardwareTwoBean.getLyqminmin().size(); i++) {
                    row = sheetAt.getRow(ff);
                    cell = row.getCell(4);
                    cell.setCellValue(hardwareTwoBean.getLyqminmin().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    ff = ff + 1;
                }
                // 获取第7 - 31行 第6列的数据
                int gg = 6;
                for (int i = 0; i < hardwareTwoBean.getLqlength().size(); i++) {
                    row = sheetAt.getRow(gg);
                    cell = row.getCell(5);
                    cell.setCellValue(hardwareTwoBean.getLqlength().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    gg = gg + 1;
                }
                // 获取第7 - 31行 第7列的数据
                int hh = 6;
                for (int i = 0; i < hardwareTwoBean.getGyqminmax().size(); i++) {
                    row = sheetAt.getRow(hh);
                    cell = row.getCell(6);
                    cell.setCellValue(hardwareTwoBean.getGyqminmax().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    hh = hh + 1;
                }
                // 获取第7 - 31行 第8列的数据
                int kk = 6;
                for (int i = 0; i < hardwareTwoBean.getGyqminmin().size(); i++) {
                    row = sheetAt.getRow(kk);
                    cell = row.getCell(7);
                    cell.setCellValue(hardwareTwoBean.getGyqminmin().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    kk = kk + 1;
                }
                // 获取第7 - 31行 第9列的数据
                int ll = 6;
                for (int i = 0; i < hardwareTwoBean.getGqlength().size(); i++) {
                    row = sheetAt.getRow(ll);
                    cell = row.getCell(8);
                    cell.setCellValue(hardwareTwoBean.getGqlength().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    ll = ll + 1;
                }
                // 获取第7 - 31行 第10列的数据
                int mm = 6;
                for (int i = 0; i < hardwareTwoBean.getLyhminmax().size(); i++) {
                    row = sheetAt.getRow(mm);
                    cell = row.getCell(9);
                    cell.setCellValue(hardwareTwoBean.getLyhminmax().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    mm = mm + 1;
                }
                // 获取第7 - 31行 第11列的数据
                int nn = 6;
                for (int i = 0; i < hardwareTwoBean.getLyhminmin().size(); i++) {
                    row = sheetAt.getRow(nn);
                    cell = row.getCell(10);
                    cell.setCellValue(hardwareTwoBean.getLyhminmin().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    nn = nn + 1;
                }
                // 获取第7 - 31行 第12列的数据
                int oo = 6;
                for (int i = 0; i < hardwareTwoBean.getLhlength().size(); i++) {
                    row = sheetAt.getRow(oo);
                    cell = row.getCell(11);
                    cell.setCellValue(hardwareTwoBean.getLhlength().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    oo = oo + 1;
                }
                // 获取第7 - 31行 第13列的数据
                int pp = 6;
                for (int i = 0; i < hardwareTwoBean.getGyhminmax().size(); i++) {
                    row = sheetAt.getRow(pp);
                    cell = row.getCell(12);
                    cell.setCellValue(hardwareTwoBean.getGyhminmax().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    pp = pp + 1;
                }
                // 获取第7 - 31行 第14列的数据
                int qq = 6;
                for (int i = 0; i < hardwareTwoBean.getGyhminmin().size(); i++) {
                    row = sheetAt.getRow(qq);
                    cell = row.getCell(13);
                    cell.setCellValue(hardwareTwoBean.getGyhminmin().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    qq = qq + 1;
                }
                // 获取第7 - 31行 第15列的数据
                int rr = 6;
                for (int i = 0; i < hardwareTwoBean.getGhlength().size(); i++) {
                    row = sheetAt.getRow(rr);
                    cell = row.getCell(14);
                    cell.setCellValue(hardwareTwoBean.getGhlength().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    rr = rr + 1;
                }
                // 获取第7 - 31行 第16列的数据
                int ss = 6;
                for (int i = 0; i < hardwareTwoBean.getOutercheck().size(); i++) {
                    row = sheetAt.getRow(ss);
                    cell = row.getCell(15);
                    cell.setCellValue(hardwareTwoBean.getOutercheck().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    ss = ss + 1;
                }
                // 获取第7 - 31行 第17列的数据
                int tt = 6;
                for (int i = 0; i < hardwareTwoBean.getYajieren().size(); i++) {
                    row = sheetAt.getRow(tt);
                    cell = row.getCell(16);
                    cell.setCellValue(hardwareTwoBean.getYajieren().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    tt = tt + 1;
                }
                // 获取第7 - 31行 第18列的数据
                int uu = 6;
                for (int i = 0; i < hardwareTwoBean.getDaihao().size(); i++) {
                    row = sheetAt.getRow(uu);
                    cell = row.getCell(17);
                    cell.setCellValue(hardwareTwoBean.getDaihao().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    uu = uu + 1;
                }
                // 获取第7 - 31行 第19列的数据
                int vv = 6;
                for (int i = 0; i < hardwareTwoBean.getPingding().size(); i++) {
                    row = sheetAt.getRow(vv);
                    cell = row.getCell(18);
                    cell.setCellValue(hardwareTwoBean.getPingding().get(i));
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setBorderRight(BorderStyle.THIN);
                    cell.setCellStyle(style);
                    vv = vv + 1;
                }
            }


            // 获取第33行 第2列的数据
            row = sheetAt.getRow(32);
            if (null == row.getCell(1)) {
                cell = row.createCell(1);
            }
            cell = row.getCell(1);
            cell.setCellValue(hardwareTwoBean.getJianli());
            cell.setCellStyle(style);

            // 获取第33行 第6列的数据
            row = sheetAt.getRow(32);
            if (null == row.getCell(5)) {
                cell = row.createCell(5);
            }
            cell = row.getCell(5);
            cell.setCellValue(hardwareTwoBean.getZhijianyuan());
            cell.setCellStyle(style);

            // 获取第33行 第9列的数据
            row = sheetAt.getRow(32);
            if (null == row.getCell(8)) {
                cell = row.createCell(8);
            }
            cell = row.getCell(8);
            cell.setCellValue(hardwareTwoBean.getFuzheren());
            cell.setCellStyle(style);
            // 获取第33行 第12列的数据
            row = sheetAt.getRow(32);
            if (null == row.getCell(11)) {
                cell = row.createCell(11);
            }
            cell = row.getCell(11);
            cell.setCellValue(hardwareTwoBean.getJiancharen());
            cell.setCellStyle(style);


            // 获取第37行 第9列的数据
            row = sheetAt.getRow(36);
            if (null == row.getCell(8)) {
                cell = row.createCell(8);
            }
            cell = row.getCell(8);
            cell.setCellValue(hardwareTwoBean.getChecked());
            cell.setCellStyle(style);

            // 获取第38行 第9列的数据
            row = sheetAt.getRow(37);
            if (null == row.getCell(8)) {
                cell = row.createCell(8);
            }
            cell = row.getCell(8);
            cell.setCellValue(hardwareTwoBean.getChecked1());
            cell.setCellStyle(style);

            // 插入图片
            if(hardwareTwoBean.getImgUrl()!=null){
                int cellNum=0;
                for(int i=0;i<hardwareTwoBean.getImgUrl().size();i++){
                    String imgPath=hardwareTwoBean.getImgUrl().get(i);
                    if (!TextUtils.isEmpty(imgPath)) {
                        if(imgPath.startsWith("file://")){
                            imgPath=imgPath.replace("file://","");
                        }
                        // 写入内存
                        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                        bitmap=BitMapUtil.openImage(imgPath);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOut);
                        fis = new FileInputStream(imgPath);
                        byte[] bytes = IOUtils.toByteArray(fis);
                        // 创建一个新的excel
                        File file = new File(newFilePath);
                        // 读取excel
                        fos = new FileOutputStream(file);
                        HSSFPatriarch patriarch = (HSSFPatriarch) sheetAt.createDrawingPatriarch();
                        // 设置图片位置
                        cellNum=cellNum+3;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 100, (short) (cellNum+i), 33, (short) (cellNum+3+i), 34);
                        patriarch.createPicture(anchor, workBook.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG));
                        // 写入
                        workBook.write(fos);

                        if(bitmap!=null){
                            bitmap.recycle();
                            bitmap=null;
                        }
                    }
                }
            }

            fos = new FileOutputStream(newFilePath);
            // 写入
            workBook.write(fos);
            System.out.println("success!");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (workBook != null) {
                try {
                    workBook.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
