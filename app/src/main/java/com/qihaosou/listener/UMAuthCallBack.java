package com.qihaosou.listener;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/27
 * Description:
 */
public class UMAuthCallBack implements UMAuthListener {
    private UMAuthType type;
    public static enum UMAuthType{
        Auth,GetInfo
    }
    public UMAuthCallBack(UMAuthType type){
        this.type=type;

    }
    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        if(type==UMAuthType.Auth){
            //获取用户信息
        }else {
            //登录，保存用户信息
        }
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {

    }
}
