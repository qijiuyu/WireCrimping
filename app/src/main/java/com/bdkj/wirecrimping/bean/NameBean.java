package com.bdkj.wirecrimping.bean;

import java.io.Serializable;

public class NameBean implements Serializable {

    private int serialNumber;
    private String name;

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
