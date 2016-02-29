package com.qihaosou.bean;

import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/26
 * Description:
 */
public class UMShareVideoBean {
    private String shareTitle;
    private String shareText;
    private UMVideo umVideo;
    public UMShareVideoBean(String shareTitle,String shareText,UMVideo umVideo){
        this.shareText=shareText;
        this.shareTitle=shareTitle;
        this.umVideo=umVideo;
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
    public UMVideo getUmVideo() {
        return umVideo;
    }

    public void setUmVideo(UMVideo umVideo) {
        this.umVideo = umVideo;
    }

}
