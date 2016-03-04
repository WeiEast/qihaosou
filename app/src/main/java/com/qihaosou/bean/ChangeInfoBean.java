package com.qihaosou.bean;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/4
 * Description:
 */
public class ChangeInfoBean {
    private String afterContent;
    private String beforeContent;
    private String changeDate;
    private String projectName;
    private int onId;
    private String uuid;
    public String getAfterContent() {
        return afterContent;
    }

    public void setAfterContent(String afterContent) {
        this.afterContent = afterContent;
    }

    public String getBeforeContent() {
        return beforeContent;
    }

    public void setBeforeContent(String beforeContent) {
        this.beforeContent = beforeContent;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getOnId() {
        return onId;
    }

    public void setOnId(int onId) {
        this.onId = onId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
