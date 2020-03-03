package com.bdkj.wirecrimping.bean;

public class JSValueBean {
    private String maxOrminDes; //传值描述
    private String gqwmax; //用来区分压接管型号点击的是钢管或是铝管
    private String gqwnum1; //js用来区分点击哪一个
    //gqwmax  gqwmin  钢圈外径的最大最小   gqnmax gqnmin  钢圈内径的最大最小  gqduibianmax  gqduibianmin   钢圈的对边距最大最小
    //lqwmax  lqwmin  铝圈的外径最大最小    lnmax  lnmin  铝圈的内边距最大最小   lduibianmax  lduibianmin  铝圈的对边距最大最小
//    ["gqwmax","gqwnum1"]


    public String getMaxOrminDes() {
        return maxOrminDes;
    }

    public void setMaxOrminDes(String maxOrminDes) {
        this.maxOrminDes = maxOrminDes;
    }


    public String getGqwnum1() {
        return gqwnum1;
    }

    public void setGqwnum1(String gqwnum1) {
        this.gqwnum1 = gqwnum1;
    }

    public String getGqwmax() {
        return gqwmax;
    }

    public void setGqwmax(String gqwmax) {
        this.gqwmax = gqwmax;
    }
}
