package com.qihaosou.bean;

import com.umeng.socialize.media.UMImage;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/26
 * Description:
 */
public class UMShareImageBean {
    private String shareTitle;
    private String shareText;
    private UMImage umImage;
    public UMShareImageBean(){

    }
    public UMShareImageBean(String shareTitle,String shareText,UMImage umImage){
        this.shareText=shareText;
        this.shareTitle=shareTitle;
        this.umImage=umImage;
    }
    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareText() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    public UMImage getUmImage() {
        return umImage;
    }

    public void setUmImage(UMImage umImage) {
        this.umImage = umImage;
    }

}
