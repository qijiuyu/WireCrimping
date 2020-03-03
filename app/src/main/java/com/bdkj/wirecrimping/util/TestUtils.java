package com.bdkj.wirecrimping.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestUtils {

    public static void main() {
        try {
            // 图片路径
            String imgPath = "/storage/emulated/0/Android/data/com.bdkj.wirecrimping/cache/luban_disk_cache/1579077844574906.png";
            //create a new workbook
            Workbook wb = new HSSFWorkbook(); //or new HSSFWorkbook();

            //add picture data to this workbook.
            InputStream is = new FileInputStream(imgPath);
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            is.close();

            CreationHelper helper = wb.getCreationHelper();

            //create sheet
            Sheet sheet = wb.createSheet("Sheet1");

            // Create the drawing patriarch.  This is the top level container for all shapes.
            Drawing drawing = sheet.createDrawingPatriarch();
            //add a picture shape
            ClientAnchor anchor = helper.createClientAnchor();
            //set top-left corner of the picture,
            //subsequent call of Picture#resize() will operate relative to it
            anchor.setCol1(3);
            anchor.setRow1(2);
            Picture pict = drawing.createPicture(anchor, pictureIdx);

            //auto-size picture relative to its top-left corner
            pict.resize();


            //save workbook
            String file = "C:\\Users\\bdkj\\Desktop\\test.xls";
            if (wb instanceof HSSFWorkbook) file += "x";
            FileOutputStream fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
