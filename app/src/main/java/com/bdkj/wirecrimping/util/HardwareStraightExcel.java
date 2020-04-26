package com.bdkj.wirecrimping.util;

import com.bdkj.wirecrimping.bean.HardwareBean;

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

public class HardwareStraightExcel {
    public static void rwExcel(String filePath, String newFilePath, HardwareBean hardwareBean) {
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
            // 获取第3行 第3列的单位格
            Row row = sheetAt.getRow(2);
            Cell cell = row.getCell(2);
            // 设置值
            cell.setCellValue(hardwareBean.getProName());
            cell.setCellStyle(style);
            // 获取第4行 第4列的数据
            row = sheetAt.getRow(3);
            cell = row.getCell(3);
            cell.setCellValue(hardwareBean.getJianliname());
            cell.setCellStyle(style);
            for (int i = 0; i < hardwareBean.getZhuanghao().size(); i++) {
                if (i == 0) {
                    // 获取第5行 第5列的数据
                    row = sheetAt.getRow(4);
                    cell = row.getCell(4);
                    cell.setCellValue(hardwareBean.getZhuanghao().get(0));
                    cell.setCellStyle(style);
                } else if (i == 1) {
                    // 获取第5行 第6列的数据
                    row = sheetAt.getRow(4);
                    cell = row.getCell(5);
                    cell.setCellValue(hardwareBean.getZhuanghao().get(i));
                    cell.setCellStyle(style);
                }

            }
            // 获取第5行 第10列的数据
            row = sheetAt.getRow(4);
            cell = row.getCell(9);
            cell.setCellValue(hardwareBean.getYajieguan());
            cell.setCellStyle(style);
            // 获取第6行 第6列的数据
            row = sheetAt.getRow(5);
            cell = row.getCell(5);
            cell.setCellValue(hardwareBean.getProzhijianyuan());
            cell.setCellStyle(style);
            // 获取第6行 第16列的数据
            row = sheetAt.getRow(5);
            cell = row.getCell(15);
            cell.setCellValue(hardwareBean.getWorkzhijianyuan());
            cell.setCellStyle(style);

            //写入右上角时间
            String[] rightTime=hardwareBean.getDate().split("-");
            if(rightTime!=null && rightTime.length==3){
                // 获取第6行 第22列的数据
                row = sheetAt.getRow(5);
                cell = row.getCell(21);
                cell.setCellValue(rightTime[0]);
                cell.setCellStyle(style);
                // 获取第6行 第24列的数据
                row = sheetAt.getRow(5);
                cell = row.getCell(23);
                cell.setCellValue(rightTime[1]);
                cell.setCellStyle(style);
                // 获取第6行 第25列的数据
                row = sheetAt.getRow(5);
                cell = row.getCell(24);
                cell.setCellValue(rightTime[2]);
                cell.setCellStyle(style);
            }


            // 获取第7行 第9列的数据
            row = sheetAt.getRow(6);
            cell = row.getCell(8);
            cell.setCellValue(hardwareBean.getYajieguanweizhi());
            cell.setCellStyle(style);
            for (int i = 0; i < hardwareBean.getXbnum().size(); i++) {
                if (i == 0) {
                    // 获取第8行 第9列的数据
                    row = sheetAt.getRow(7);
                    cell = row.getCell(8);
                    cell.setCellValue(hardwareBean.getXbnum().get(0));
                    cell.setCellStyle(style);
                } else if (i == 1) {
                    // 获取第8行 第17列的数据
                    row = sheetAt.getRow(7);
                    cell = row.getCell(16);
                    cell.setCellValue(hardwareBean.getXbnum().get(1));
                    cell.setCellStyle(style);
                } else if (i == 2) {
                    // 获取第8行 第25列的数据
                    row = sheetAt.getRow(7);
                    cell = row.getCell(24);
                    cell.setCellValue(hardwareBean.getXbnum().get(2));
                    cell.setCellStyle(style);
                }
            }
            // 第11行，第9-32列
            int j = 8;
            for (int i = 0; i < hardwareBean.getGqwmax().size(); i++) {
                row = sheetAt.getRow(10);
                cell = row.getCell(j);
                cell.setCellValue(hardwareBean.getGqwmax().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                j = j + 1;
            }

            // 第12行，第9-32列
            int aa = 8;
            for (int i = 0; i < hardwareBean.getGqwmin().size(); i++) {
                row = sheetAt.getRow(11);
                cell = row.getCell(aa);
                cell.setCellValue(hardwareBean.getGqwmin().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                aa = aa + 1;
            }
            // 第13行，第9-32列
            int bb = 8;
            for (int i = 0; i < hardwareBean.getGqnmax().size(); i++) {
                row = sheetAt.getRow(12);
                cell = row.getCell(bb);
                cell.setCellValue(hardwareBean.getGqnmax().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                bb = bb + 1;
            }
            // 第14行，第9-32列
            int cc = 8;
            for (int i = 0; i < hardwareBean.getGqnmin().size(); i++) {
                row = sheetAt.getRow(13);
                cell = row.getCell(cc);
                cell.setCellValue(hardwareBean.getGqnmin().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                cc = cc + 1;
            }
            // 第15行，第9-32列
            int dd = 8;
            for (int i = 0; i < hardwareBean.getGqlength().size(); i++) {
                row = sheetAt.getRow(14);
                cell = row.getCell(dd);
                cell.setCellValue(hardwareBean.getGqlength().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                dd = dd + 1;
            }
            // 第16行，第9-32列
            int ee = 8;
            for (int i = 0; i < hardwareBean.getGqduibianmax().size(); i++) {
                row = sheetAt.getRow(15);
                cell = row.getCell(ee);
                cell.setCellValue(hardwareBean.getGqduibianmax().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                ee = ee + 1;
            }
            // 第17行，第9-32列
            int ff = 8;
            for (int i = 0; i < hardwareBean.getGqduibianmin().size(); i++) {
                row = sheetAt.getRow(16);
                cell = row.getCell(ff);
                cell.setCellValue(hardwareBean.getGqduibianmin().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                ff = ff + 1;
            }
            // 第18行，第9-32列
            int gg = 8;
            for (int i = 0; i < hardwareBean.getGyajie().size(); i++) {
                row = sheetAt.getRow(17);
                cell = row.getCell(gg);
                cell.setCellValue(hardwareBean.getGyajie().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                gg = gg + 1;
            }
            // 第19行，第9-32列
            int hh = 8;
            for (int i = 0; i < hardwareBean.getLqwmax().size(); i++) {
                row = sheetAt.getRow(18);
                cell = row.getCell(hh);
                cell.setCellValue(hardwareBean.getLqwmax().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                hh = hh + 1;
            }
            // 第20行，第9-32列
            int kk = 8;
            for (int i = 0; i < hardwareBean.getLqwmin().size(); i++) {
                row = sheetAt.getRow(19);
                cell = row.getCell(kk);
                cell.setCellValue(hardwareBean.getLqwmin().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                kk = kk + 1;
            }
            // 第21行，第9-32列
            int ll = 8;
            for (int i = 0; i < hardwareBean.getLnmax().size(); i++) {
                row = sheetAt.getRow(20);
                cell = row.getCell(ll);
                cell.setCellValue(hardwareBean.getLnmax().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                ll = ll + 1;
            }
            // 第22行，第9-32列
            int mm = 8;
            for (int i = 0; i < hardwareBean.getLnmin().size(); i++) {
                row = sheetAt.getRow(21);
                cell = row.getCell(mm);
                cell.setCellValue(hardwareBean.getLnmin().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                mm = mm + 1;
            }
            // 第23行，第9-32列
            int nn = 8;
            for (int i = 0; i < hardwareBean.getLlength().size(); i++) {
                row = sheetAt.getRow(22);
                cell = row.getCell(nn);
                cell.setCellValue(hardwareBean.getLlength().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                nn = nn + 1;
            }
            // 第24行，第9-32列
            int oo = 8;
            for (int i = 0; i < hardwareBean.getLduibianmax().size(); i++) {
                row = sheetAt.getRow(23);
                cell = row.getCell(oo);
                cell.setCellValue(hardwareBean.getLduibianmax().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                oo = oo + 1;
            }
            // 第25行，第9-32列
            int pp = 8;
            for (int i = 0; i < hardwareBean.getLduibianmin().size(); i++) {
                row = sheetAt.getRow(24);
                cell = row.getCell(pp);
                cell.setCellValue(hardwareBean.getLduibianmin().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                pp = pp + 1;
            }
            // 第26行，第9-32列
            int qq = 8;
            for (int i = 0; i < hardwareBean.getLyajiechang().size(); i++) {
                row = sheetAt.getRow(25);
                cell = row.getCell(qq);
                cell.setCellValue(hardwareBean.getLyajiechang().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                qq = qq + 1;
            }
            // 第27行，第9-32列
            int rr = 8;
            for (int i = 0; i < hardwareBean.getQingxi().size(); i++) {
                row = sheetAt.getRow(26);
                cell = row.getCell(rr);
                cell.setCellValue(hardwareBean.getQingxi().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                rr = rr + 1;
            }
            // 第28行，第9-32列
            int ss = 8;
            for (int i = 0; i < hardwareBean.getWaiguan().size(); i++) {
                row = sheetAt.getRow(27);
                cell = row.getCell(ss);
                cell.setCellValue(hardwareBean.getWaiguan().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                ss = ss + 1;
            }
            // 第29行，第9-32列
            int tt = 8;
            for (int i = 0; i < hardwareBean.getDaodianzhi().size(); i++) {
                row = sheetAt.getRow(28);
                cell = row.getCell(tt);
                cell.setCellValue(hardwareBean.getDaodianzhi().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                tt = tt + 1;
            }
            // 第30行，第9-32列
            int uu = 8;
            for (int i = 0; i < hardwareBean.getYanghuamo().size(); i++) {
                row = sheetAt.getRow(29);
                cell = row.getCell(uu);
                cell.setCellValue(hardwareBean.getYanghuamo().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                uu = uu + 1;
            }
            // 第31行，第9-32列
            int vv = 8;
            for (int i = 0; i < hardwareBean.getYajiezhi().size(); i++) {
                row = sheetAt.getRow(30);
                cell = row.getCell(vv);
                cell.setCellValue(hardwareBean.getYajiezhi().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                vv = vv + 1;
            }
            // 第32行，第9-32列
            int ww = 8;
            for (int i = 0; i < hardwareBean.getGangyinhao().size(); i++) {
                row = sheetAt.getRow(31);
                cell = row.getCell(ww);
                cell.setCellValue(hardwareBean.getGangyinhao().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                ww = ww + 1;
            }
            // 第33行，第9-32列
            int xx = 8;
            for (int i = 0; i < hardwareBean.getYajieren().size(); i++) {
                row = sheetAt.getRow(32);
                cell = row.getCell(xx);
                cell.setCellValue(hardwareBean.getYajieren().get(i));
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                cell.setCellStyle(style);
                xx = xx + 1;
            }
            // 获取第41行 第3列的数据
            row = sheetAt.getRow(40);
            cell = row.getCell(2);
            cell.setCellValue(hardwareBean.getMessage());
            cell.setCellStyle(style);
            // 获取第42行 第21列的数据
            row = sheetAt.getRow(41);
            cell = row.getCell(20);
            cell.setCellValue(hardwareBean.getJianliren());
            cell.setCellStyle(style);

            //写入监理时间
            String[] JianLiTime=hardwareBean.getJianlidate().split("-");
            if(JianLiTime!=null && JianLiTime.length==3){
                // 获取第42行 第26列的数据
                row = sheetAt.getRow(41);
                cell = row.getCell(25);
                cell.setCellValue(JianLiTime[0]);
                cell.setCellStyle(style);
                // 获取第42行 第28列的数据
                row = sheetAt.getRow(41);
                cell = row.getCell(27);
                cell.setCellValue(JianLiTime[1]);
                cell.setCellStyle(style);
                // 获取第42行 第30列的数据
                row = sheetAt.getRow(41);
                cell = row.getCell(29);
                cell.setCellValue(JianLiTime[2]);
                cell.setCellStyle(style);
            }


            // 获取第46行 第14列的数据
            row = sheetAt.getRow(45);
            if (null == row.getCell(13)) {
                cell = row.createCell(13);
            }
            cell = row.getCell(13);
            cell.setCellValue(hardwareBean.getChecked());

            // 获取第47行 第14列的数据
            row = sheetAt.getRow(46);
            if (null == row.getCell(13)) {
                cell = row.createCell(13);
            }
            cell = row.getCell(13);
            cell.setCellValue(hardwareBean.getChecked1());

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
            ToastUtils.showLong("Excel文档保存失败");
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
