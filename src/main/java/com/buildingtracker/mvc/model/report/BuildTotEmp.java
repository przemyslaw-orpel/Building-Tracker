package com.buildingtracker.mvc.model.report;

public class BuildTotEmp {
    private String buildName;
    private int totEmp;

    public BuildTotEmp(String buildName, int totEmp) {
        this.buildName = buildName;
        this.totEmp = totEmp;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public int getTotEmp() {
        return totEmp;
    }

    public void setTotEmp(int totEmp) {
        this.totEmp = totEmp;
    }
}
