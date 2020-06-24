package com.bdkj.wirecrimping.bean;

import java.io.Serializable;

public class MainBean implements Serializable {

    private String project;
    private String model;
    private String superVision;
    private String construction;

    public MainBean(){}

    public MainBean(String project, String model, String superVision, String construction) {
        this.project = project;
        this.model = model;
        this.superVision = superVision;
        this.construction = construction;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSuperVision() {
        return superVision;
    }

    public void setSuperVision(String superVision) {
        this.superVision = superVision;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }
}
