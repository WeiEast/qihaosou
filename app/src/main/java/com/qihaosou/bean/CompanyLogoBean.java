package com.qihaosou.bean;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/5
 * Description:上班Bean
 */
public class CompanyLogoBean {
    private String regName;
    private String regNo;
    private String sbName;
    private String[] serviceName;
    private String type;
    private int used;
    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String[] getServiceName() {
        return serviceName;
    }

    public void setServiceName(String[] serviceName) {
        this.serviceName = serviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }


}
