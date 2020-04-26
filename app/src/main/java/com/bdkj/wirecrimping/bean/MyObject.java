package com.bdkj.wirecrimping.bean;

import android.content.Context;
import android.content.DialogInterface;
import android.webkit.JavascriptInterface;

import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bdkj.wirecrimping.Constant;
import com.bdkj.wirecrimping.util.JsonUtil;
import com.bdkj.wirecrimping.util.SpUtils;
import com.bdkj.wirecrimping.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MyObject {
    private Context mcontext;
    private int index = 0;
    private List<String> data = new ArrayList<>();//型号
    private List<String> wire = new ArrayList<>();//使用导线
    private String sign;


    //构造函数传入上下文
    public MyObject(Context c, List<String> data, List<String> wire, String sign) {
        mcontext = c;
        this.data = data;
        this.sign = sign;
        this.wire = wire;
    }

    @JavascriptInterface
    public void showList() {
        //创建对话框
        if (data.size() > 0 && data != null) {
            String[] items = data.toArray(new String[data.size()]);
            AlertDialog alertDialog = new AlertDialog.Builder(mcontext)
                    .setTitle("型号列表")
                    .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            index = i;
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ModelBean modelBean = new ModelBean();
                            modelBean.setModel("型号");
                            modelBean.setResultModel(items[index]);
                            SpUtils.getInstance(mcontext).savaString(Constant.MODEL, items[index]);
                            EventBus.getDefault().post(modelBean);

                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
            alertDialog.show();
        } else {
            ToastUtils.showShort("暂无压接管型号");
        }
    }

    @JavascriptInterface
    public void showWireList() {
        //创建对话框
        if (wire.size() > 0 && wire != null) {
            String[] items = wire.toArray(new String[wire.size()]);
            AlertDialog alertDialog = new AlertDialog.Builder(mcontext)
                    .setTitle("导线列表")
                    .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            index = i;
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ModelBean modelBean = new ModelBean();
                            modelBean.setModel("导线");
                            modelBean.setResultModel(items[index]);
                            SpUtils.getInstance(mcontext).savaString(Constant.WIRE, items[index]);
                            EventBus.getDefault().post(modelBean);

                        }
                    })
                    .setNegativeButton("取消", null)
                    .create();
            alertDialog.show();
        } else {
            ToastUtils.showShort("暂无压接管型号");
        }
    }

    @JavascriptInterface
    public void showValue(String message) {
        JSValueBean jsValueBean = new JSValueBean();
        JSONArray array = JSON.parseArray(message);
        for (int i = 0; i < array.size(); i++) {
            if ("1".equals(sign) || "2".equals(sign)) {
                if (i == 0) {
                    jsValueBean.setGqwmax(array.getString(0));
                    SpUtils.getInstance(mcontext).savaString(Constant.TYPE, array.getString(0));
                } else {
                    jsValueBean.setGqwnum1(array.getString(1));
                }
            } else {
                if (i == 1) {
                    jsValueBean.setGqwnum1(array.getString(1));
                } else if (i == 2) {
                    jsValueBean.setGqwmax(array.getString(2));
                    SpUtils.getInstance(mcontext).savaString(Constant.TYPETWO, array.getString(2));
                }

            }
        }
        jsValueBean.setMaxOrminDes("value");
        EventBus.getDefault().post(jsValueBean);
    }

    @JavascriptInterface
    public void showPhoto() {
        EventBus.getDefault().post("选择图片");
    }

    @JavascriptInterface
    public void amplificationPhoto(String photoStr) {
        PhotoAddressBean photoAddressBean = new PhotoAddressBean();
        photoAddressBean.setPhotoDes("放大图片");
        photoAddressBean.setPhotoSelect(photoStr);
        EventBus.getDefault().post(photoAddressBean);
    }

    @JavascriptInterface
    public void narrowPhoto() {
        EventBus.getDefault().post("缩小图片");
    }

    @JavascriptInterface
    public void save(String message) {
        if ("1".equals(sign)) {
            SpUtils.getInstance(mcontext).savaString(Constant.CURVATURESTRAIGHTSAVE, message);
        } else if ("2".equals(sign)) {
            SpUtils.getInstance(mcontext).savaString(Constant.CURVATURETENSIONSAVE, message);
        } else if ("3".equals(sign)) {
            SpUtils.getInstance(mcontext).savaString(Constant.CURVATURETHREESAVE, message);
        } else {
            SpUtils.getInstance(mcontext).savaString(Constant.CURVATURETWOSAVE, message);
        }
        AttributeValuesBean attributeValuesBean = (AttributeValuesBean) JsonUtil.stringToObject(message, AttributeValuesBean.class);
        attributeValuesBean.setMessage("保存");
        EventBus.getDefault().post(attributeValuesBean);
    }

    @JavascriptInterface
    public void hardwareSave(String message) {
        if ("1".equals(sign)) {
            SpUtils.getInstance(mcontext).savaString(Constant.HARDWARESTRAIGHTSAVE, message);
        } else if ("2".equals(sign)) {
            SpUtils.getInstance(mcontext).savaString(Constant.HARDWARETENSIONSAVE, message);
        } else if ("3".equals(sign)) {
            SpUtils.getInstance(mcontext).savaString(Constant.HARDWARETHREESAVE, message);
        } else {
            SpUtils.getInstance(mcontext).savaString(Constant.HARDWARETWOSAVE, message);
        }
        if ("1".equals(sign) || "2".equals(sign)) {
            HardwareBean hardwareBean = (HardwareBean) JsonUtil.stringToObject(message, HardwareBean.class);
            hardwareBean.setSaveInformation("保存");
            EventBus.getDefault().post(hardwareBean);
        } else {
            HardwareTwoBean hardwareTwoBean = (HardwareTwoBean) JsonUtil.stringToObject(message, HardwareTwoBean.class);
            hardwareTwoBean.setSaveInformation("保存数据");
            EventBus.getDefault().post(hardwareTwoBean);
        }

    }

    @JavascriptInterface
    public void getValues(String tex) {
        ByValueBean byValueBean = new ByValueBean();
        byValueBean.setTitle("传值");
        byValueBean.setValue(tex);
        EventBus.getDefault().post(byValueBean);
    }

}
