package com.bdkj.wirecrimping.bean;

import java.util.ArrayList;
import java.util.List;

public class MeasureBean {
    private String measureDes; //测量描述
    private double measureValue; //测量值
    private String measureValueJs; // js传过来的值
    private List<String> listValue = new ArrayList<>();
    private double max; //标准值最大
    private double min; //标准值最小


    public String getMeasureDes() {
        return measureDes;
    }

    public void setMeasureDes(String measureDes) {
        this.measureDes = measureDes;
    }

    public double getMeasureValue() {
        return measureValue;
    }

    public void setMeasureValue(double measureValue) {
        this.measureValue = measureValue;
    }


    public String getMeasureValueJs() {
        return measureValueJs;
    }

    public void setMeasureValueJs(String measureValueJs) {
        this.measureValueJs = measureValueJs;
    }

    public List<String> getListValue() {
        return listValue;
    }

    public void setListValue(List<String> listValue) {
        this.listValue = listValue;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
}
