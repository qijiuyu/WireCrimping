package com.bdkj.wirecrimping.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bdkj.wirecrimping.R;


/**
 * Created by bdkj on 2018/11/17.
 */

public class DialogUtils {

    private static Dialog buttomDialog;

    public static Dialog getBottomDialog(Context context, View inflate) {

        buttomDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
//        View inflate = LayoutInflater.from(context).inflate(dialog_layout, null);
        //初始化控件
       /* inflate.findViewById(R.id.choosePhoto).setOnClickListener(this);
        inflate.findViewById(R.id.takePhoto).setOnClickListener(this);
        inflate.findViewById(R.id.btn_cancel).setOnClickListener(this);*/
        //将布局设置给Dialog
        buttomDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = buttomDialog.getWindow();
        if (dialogWindow != null) {
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.y = 20;//设置Dialog距离底部的距离
            //将属性设置给窗体
            dialogWindow.setAttributes(lp);
        }
        return buttomDialog;
    }

    public static void show(Dialog dialog){
        if(dialog != null){
            dialog.show();
        }
    }

    public static void dismiss(Dialog dialog) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        } catch (Exception e) {

        } finally {
            dialog = null;
        }
    }


}
