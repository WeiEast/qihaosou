package com.qihaosou.bean;

import java.io.Serializable;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/4
 * Description:
 */
public class AnnualBean implements Serializable{
    private String uuid;
    private String onId;
    private String submittedYear;
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOnId() {
        return onId;
    }

    public void setOnId(String onId) {
        this.onId = onId;
    }

    public String getSubmittedYear() {
        return submittedYear;
    }

    public void setSubmittedYear(String submittedYear) {
        this.submittedYear = submittedYear;
    }

}
