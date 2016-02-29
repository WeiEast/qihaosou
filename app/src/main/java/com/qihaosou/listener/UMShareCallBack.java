package com.qihaosou.listener;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.qihaosou.util.ToastUtil;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/26
 * Description:
 */
public class UMShareCallBack implements UMShareListener {
    private Context context;
    public UMShareCallBack(Context context){
        this.context=context;
    }
    @Override
    public void onResult(SHARE_MEDIA share_media) {
        ToastUtil.TextToast(context,share_media+"分享成功啦");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        ToastUtil.TextToast(context,share_media+"分享失败啦");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        ToastUtil.TextToast(context,share_media+"分享取消啦");
    }
}
