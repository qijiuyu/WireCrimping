package com.bdkj.wirecrimping.util;

import com.bdkj.wirecrimping.bean.AttributeValuesBean;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author liuyongfei
 * @version 创建时间：2020年1月3日 下午2:26:36 类说明
 */

public class OperateExcel {
//	public static void main(String[] args) {
//		// 模板路径
//		String filePath = "C:\\Users\\Administrator\\Desktop\\xianxian5.xls";
//		// 图片路径
//		String imgPath = "D:\\Documents\\Pictures\\t012ab802f68f87d488.png";
//		// 文件路径
//		String newFilePath = "C:\\Users\\Administrator\\Desktop\\xianxian6.xls";
//		rwExcel(filePath, newFilePath, imgPath);
//	}

    public static void rwExcel(String filePath, String newFilePath, AttributeValuesBean attributeValuesBean) {
        Workbook workBook = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
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
            // 获取第2行 第2列的单位格
            Row row = sheetAt.getRow(1);
            Cell cell = row.getCell(1);
            // 设置值
            cell.setCellValue(attributeValuesBean.getProname());
            cell.setCellStyle(style);
            // 获取第3行 第4列的数据
            row = sheetAt.getRow(2);
            cell = row.getCell(3);
            cell.setCellValue(attributeValuesBean.getNaizhangNum());
            cell.setCellStyle(style);
            // 获取第3行 第11列的数据
            row = sheetAt.getRow(2);
            cell = row.getCell(10);
            for (int i = 0; i < attributeValuesBean.getNaizhangtahao().size(); i++) {
                cell.setCellValue(attributeValuesBean.getNaizhangtahao().get(0) + "号 至 " + attributeValuesBean.getNaizhangtahao().get(1) + "号");
            }

            cell.setCellStyle(style);
            // 获取第3行 第19列的数据
            row = sheetAt.getRow(2);
            cell = row.getCell(16);
            cell.setCellValue(attributeValuesBean.getDaoxiannum() + "\\r\\n" + attributeValuesBean.getDixiannum());
            cell.setCellStyle(style);
            // 获取第3行 第25列的数据
            row = sheetAt.getRow(2);
            cell = row.getCell(24);
            cell.setCellValue(attributeValuesBean.getDate());
            cell.setCellStyle(style);
            // 获取第4行 第9列的数据
            row = sheetAt.getRow(3);
            cell = row.getCell(8);
            cell.setCellValue(attributeValuesBean.getYajieguanweizhi());
            cell.setCellStyle(style);
            for (int i = 0; i < attributeValuesBean.getXbnum().size(); i++) {
                if (i == 0) {
                    // 获取第5行 第9列的数据
                    row = sheetAt.getRow(4);
                    cell = row.getCell(8);
                    cell.setCellValue(attributeValuesBean.getXbnum().get(0));
                    cell.setCellStyle(style);
                } else if (i == 1) {
                    // 获取第5行 第17列的数据
                    row = sheetAt.getRow(4);
                    cell = row.getCell(16);
                    cell.setCellValue(attributeValuesBean.getXbnum().get(1));
                    cell.setCellStyle(style);
                } else if (i == 2) {
                    // 获取第5行 第25列的数据
                    row = sheetAt.getRow(4);
                    cell = row.getCell(24);
                    cell.setCellValue(attributeValuesBean.getXbnum().get(2));
                    cell.setCellStyle(style);
                }
            }
//            int i = 8;
//            while (i < 32) {
//                // 第七行，第9-32列
//                row = sheetAt.getRow(6);
//                cell = row.getCell(i);
//                cell.setCellValue("99");
//                style.setBorderBottom(BorderStyle.THIN);
//                style.setBorderRight(BorderStyle.THIN);
//                cell.setCellStyle(style);
//                i++;
//            }
            // 第七行，第9-32列
            int j = 8;
            for (int i = 0; i < attributeValuesBean.getWanqudu().size(); i++) {
                row = sheetAt.getRow(6);
                cell = row.getCell(j);
                cell.setCellValue(attributeValuesBean.getWanqudu().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                j = j + 1;
            }

            // 获取第12行 第14列的数据
            row = sheetAt.getRow(11);
            if (null == row.getCell(13)) {
                cell = row.createCell(13);
            }
            cell = row.getCell(13);
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
//			if (null != imgPath && !imgPath.equals("") && null != newFilePath && !newFilePath.equals("")) {
//				// 写入内存
//				// BufferedImage bufferImg = null;
//				// ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
//				// bufferImg = ImageIO.read(new File(imgPath));
//				// ImageIO.write(bufferImg, "jpg", byteArrayOut);
//				fis = new FileInputStream(imgPath);
//				byte[] bytes = IOUtils.toByteArray(fis);
//				// 创建一个新的excel
//				File file = new File(newFilePath);
//				// 读取excel
//				fos = new FileOutputStream(file);
//				HSSFPatriarch patriarch = (HSSFPatriarch) sheetAt.createDrawingPatriarch();
//				// 设置图片位置
//				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 100, (short) 3, 7, (short) 6, 8);
//				patriarch.createPicture(anchor, workBook.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG));
//				// 写入
//				workBook.write(fos);
//				System.out.println("success!");
//			}
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
