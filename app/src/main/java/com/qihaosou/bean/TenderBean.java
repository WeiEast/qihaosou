package com.qihaosou.bean;

import java.io.Serializable;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/8
 * Description:招投标
 */
public class TenderBean implements Serializable{
    private String agentOrg;
    private String category;
    private String content;
    private String createTime;
    private String description;
    private String docID;
    private String iD;
    private String name;
    private String openTime;
    private String procurement;
    private String region;
    private String used;
    public String getAgentOrg() {
        return agentOrg;
    }

    public void setAgentOrg(String agentOrg) {
        this.agentOrg = agentOrg;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getProcurement() {
        return procurement;
    }

    public void setProcurement(String procurement) {
        this.procurement = procurement;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }



}
