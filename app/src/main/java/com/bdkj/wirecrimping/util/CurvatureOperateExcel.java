package com.bdkj.wirecrimping.util;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.bdkj.wirecrimping.bean.AttributeValuesBean;

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

/**
 * @author liuyongfei
 * @version 创建时间：2020年1月3日 下午2:26:36 类说明
 */

public class CurvatureOperateExcel {
    public static void rwExcel(String filePath, String newFilePath, AttributeValuesBean attributeValuesBean) {
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
            cell.setCellValue(attributeValuesBean.getProname());
            cell.setCellStyle(style);
            // 获取第3行 第3列的数据
            row = sheetAt.getRow(2);
            cell = row.getCell(2);
            cell.setCellValue(attributeValuesBean.getProjianliname());
            cell.setCellStyle(style);
            for (int i = 0; i < attributeValuesBean.getNaizhangnum().size(); i++) {
                if (i == 0) {
                    // 获取第4行 第3列的数据
                    row = sheetAt.getRow(3);
                    cell = row.getCell(2);
                    cell.setCellValue(attributeValuesBean.getNaizhangnum().get(0));
                    cell.setCellStyle(style);
                } else if (i == 1) {
                    // 获取第4行 第5列的数据
                    row = sheetAt.getRow(3);
                    cell = row.getCell(4);
                    cell.setCellValue(attributeValuesBean.getNaizhangnum().get(1));
                    cell.setCellStyle(style);
                }

            }
            // 获取第4行 第8列的数据
            row = sheetAt.getRow(3);
            cell = row.getCell(7);
            cell.setCellValue(attributeValuesBean.getYejieguannum());
            cell.setCellStyle(style);
            // 获取第5行 第4列的数据
            row = sheetAt.getRow(4);
            cell = row.getCell(3);
            cell.setCellValue(attributeValuesBean.getProprson());
            cell.setCellStyle(style);
            // 获取第5行 第10列的数据
            row = sheetAt.getRow(4);
            cell = row.getCell(9);
            cell.setCellValue(attributeValuesBean.getWorkprson());
            cell.setCellStyle(style);

            //写入日期
            String[] rightTime=attributeValuesBean.getDate().split("-");
            if(rightTime!=null && rightTime.length==3){
                // 获取第5行 第20列的数据
                row = sheetAt.getRow(4);
                cell = row.getCell(19);
                cell.setCellValue(rightTime[0]);
                cell.setCellStyle(style);
                // 获取第5行 第22列的数据
                row = sheetAt.getRow(4);
                cell = row.getCell(21);
                cell.setCellValue(rightTime[1]);
                cell.setCellStyle(style);
                // 获取第5行 第24列的数据
                row = sheetAt.getRow(4);
                cell = row.getCell(23);
                cell.setCellValue(rightTime[2]);
                cell.setCellStyle(style);
            }

            // 获取第6行 第7列的数据
            row = sheetAt.getRow(5);
            cell = row.getCell(6);
            cell.setCellValue(attributeValuesBean.getYajieguanweizhi());
            cell.setCellStyle(style);

            for (int i = 0; i < attributeValuesBean.getXbnum().size(); i++) {
                if (i == 0) {
                    // 获取第7行 第7列的数据
                    row = sheetAt.getRow(6);
                    cell = row.getCell(6);
                    cell.setCellValue(attributeValuesBean.getXbnum().get(0));
                    cell.setCellStyle(style);
                } else if (i == 1) {
                    // 获取第7行 第15列的数据
                    row = sheetAt.getRow(6);
                    cell = row.getCell(14);
                    cell.setCellValue(attributeValuesBean.getXbnum().get(1));
                    cell.setCellStyle(style);
                } else if (i == 2) {
                    // 获取第7行 第23列的数据
                    row = sheetAt.getRow(6);
                    cell = row.getCell(22);
                    cell.setCellValue(attributeValuesBean.getXbnum().get(2));
                    cell.setCellStyle(style);
                }
            }
            // 第9行，第7-30列
            int j = 6;
            for (int i = 0; i < attributeValuesBean.getWanqudu().size(); i++) {
                row = sheetAt.getRow(8);
                cell = row.getCell(j);
                cell.setCellValue(attributeValuesBean.getWanqudu().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                j = j + 1;
            }

            // 获取第13行 第13列的数据
            row = sheetAt.getRow(12);
            if (null == row.getCell(12)) {
                cell = row.createCell(12);
            }
            cell = row.getCell(12);
            cell.setCellValue(attributeValuesBean.getChecked());
            style = workBook.createCellStyle();
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            font = workBook.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 11);// 字体大小
            // 设置字体的颜色
            font.setColor(Font.COLOR_RED);
            style.setFont(font);
            cell.setCellStyle(style);

            // 插入图片
            if(attributeValuesBean.getImgUrl()!=null){
                int cellNum=0;
                for(int i=0;i<attributeValuesBean.getImgUrl().size();i++){
                    String imgPath=attributeValuesBean.getImgUrl().get(i);
                    Log.e("tag",imgPath+"++++++++++++++++++++++222");
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
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 100, (short) (cellNum+i), 9, (short) (cellNum+3+i), 10);
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
            Log.e("tag","创建成功+++++++++++++++++++++++");


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
