package com.qihaosou.bean;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/4
 * Description:
 */
public class HomePageGridViewBean {
    private int imgId;
    private String name;
    private int num;
    public HomePageGridViewBean(int imgId,String name,int num){
        this.imgId=imgId;
        this.name=name;
        this.num=num;
    }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
