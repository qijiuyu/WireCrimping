package com.bdkj.wirecrimping.bean;

import java.util.ArrayList;
import java.util.List;

public class AttributeValuesBean {
    private String message; //保存
    private String proname; //工程名称
    private String projianliname; //项目部门
    private List<String> naizhangnum = new ArrayList<>(); //耐张段桩号
    private String yejieguannum; //压接管型号
    private String proprson; // 项目部专职质检员
    private String workprson; //施工队质检员
    private String date; //年月日
    private String yajieguanweizhi; //压接管位置
    private List<String> xbnum = new ArrayList<>(); //相别
    private List<String> wanqudu = new ArrayList<>(); //弯曲度测量值
    private List<String> imgUrl = new ArrayList<>(); //图片路径
    private String checked; //选中结果
    private String naizhangNum; //耐张段施工桩号 线线3和线线2
    private List<String> naizhangtahao = new ArrayList<>(); //耐张段运行塔号
    private String daoxiannum; //导线
    private String dixiannum; //地线

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProjianliname() {
        return projianliname;
    }

    public void setProjianliname(String projianliname) {
        this.projianliname = projianliname;
    }

    public List<String> getNaizhangnum() {
        return naizhangnum;
    }

    public void setNaizhangnum(List<String> naizhangnum) {
        this.naizhangnum = naizhangnum;
    }

    public String getYejieguannum() {
        return yejieguannum;
    }

    public void setYejieguannum(String yejieguannum) {
        this.yejieguannum = yejieguannum;
    }

    public String getProprson() {
        return proprson;
    }

    public void setProprson(String proprson) {
        this.proprson = proprson;
    }

    public String getWorkprson() {
        return workprson;
    }

    public void setWorkprson(String workprson) {
        this.workprson = workprson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYajieguanweizhi() {
        return yajieguanweizhi;
    }

    public void setYajieguanweizhi(String yajieguanweizhi) {
        this.yajieguanweizhi = yajieguanweizhi;
    }

    public List<String> getXbnum() {
        return xbnum;
    }

    public void setXbnum(List<String> xbnum) {
        this.xbnum = xbnum;
    }

    public List<String> getWanqudu() {
        return wanqudu;
    }

    public void setWanqudu(List<String> wanqudu) {
        this.wanqudu = wanqudu;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getNaizhangNum() {
        return naizhangNum;
    }

    public void setNaizhangNum(String naizhangNum) {
        this.naizhangNum = naizhangNum;
    }

    public List<String> getNaizhangtahao() {
        return naizhangtahao;
    }

    public void setNaizhangtahao(List<String> naizhangtahao) {
        this.naizhangtahao = naizhangtahao;
    }

    public String getDaoxiannum() {
        return daoxiannum;
    }

    public void setDaoxiannum(String daoxiannum) {
        this.daoxiannum = daoxiannum;
    }

    public String getDixiannum() {
        return dixiannum;
    }

    public void setDixiannum(String dixiannum) {
        this.dixiannum = dixiannum;
    }
}
