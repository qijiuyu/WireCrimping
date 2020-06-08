package com.bdkj.wirecrimping.bean;

import java.io.Serializable;
import java.util.List;

public class StandardValuesBean implements Serializable {
    private List<DataBean> dataList;

    public List<DataBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataBean implements Serializable {
        private int serialNumber; //序号
        private String model; //型号
        private String applyWire;//适用导线
        private double steel_D_big;//钢管D最大值
        private double steel_D_min;//钢管D最小值
        private double steel_d_big;//钢管d最大值
        private double steel_d_min;//钢管d最小值
        private double steel_pressure_after; //钢管压后值
        private int steel_L; //钢管L值
        private double aluminum_D;//铝管外径D标准值
        private double aluminum_D_big; //铝管外径D最大值
        private double aluminum_D_min; //铝管外径D最小值
        private double aluminum_d; //铝管内径d标准值
        private double aluminum_pressure_after; //铝管压后值
        private int aluminum_L;//铝管L值
        private int aluminum_type;//铝管基本尺寸标注

        public int getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(int serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getApplyWire() {
            return applyWire;
        }

        public void setApplyWire(String applyWire) {
            this.applyWire = applyWire;
        }

        public double getSteel_D_big() {
            return steel_D_big;
        }

        public void setSteel_D_big(double steel_D_big) {
            this.steel_D_big = steel_D_big;
        }

        public double getSteel_D_min() {
            return steel_D_min;
        }

        public void setSteel_D_min(double steel_D_min) {
            this.steel_D_min = steel_D_min;
        }

        public double getSteel_d_big() {
            return steel_d_big;
        }

        public void setSteel_d_big(double steel_d_big) {
            this.steel_d_big = steel_d_big;
        }

        public double getSteel_d_min() {
            return steel_d_min;
        }

        public void setSteel_d_min(double steel_d_min) {
            this.steel_d_min = steel_d_min;
        }

        public double getSteel_pressure_after() {
            return steel_pressure_after;
        }

        public void setSteel_pressure_after(double steel_pressure_after) {
            this.steel_pressure_after = steel_pressure_after;
        }

        public int getSteel_L() {
            return steel_L;
        }

        public void setSteel_L(int steel_L) {
            this.steel_L = steel_L;
        }

        public double getAluminum_D_big() {
            return aluminum_D_big;
        }

        public void setAluminum_D_big(double aluminum_D_big) {
            this.aluminum_D_big = aluminum_D_big;
        }

        public double getAluminum_D_min() {
            return aluminum_D_min;
        }

        public void setAluminum_D_min(double aluminum_D_min) {
            this.aluminum_D_min = aluminum_D_min;
        }

        public double getAluminum_d() {
            return aluminum_d;
        }

        public void setAluminum_d(double aluminum_d) {
            this.aluminum_d = aluminum_d;
        }

        public double getAluminum_pressure_after() {
            return aluminum_pressure_after;
        }

        public void setAluminum_pressure_after(double aluminum_pressure_after) {
            this.aluminum_pressure_after = aluminum_pressure_after;
        }

        public int getAluminum_L() {
            return aluminum_L;
        }

        public void setAluminum_L(int aluminum_L) {
            this.aluminum_L = aluminum_L;
        }

        public double getAluminum_D() {
            return aluminum_D;
        }

        public void setAluminum_D(double aluminum_D) {
            this.aluminum_D = aluminum_D;
        }

        public int getAluminum_type() {
            return aluminum_type;
        }

        public void setAluminum_type(int aluminum_type) {
            this.aluminum_type = aluminum_type;
        }
    }


}
