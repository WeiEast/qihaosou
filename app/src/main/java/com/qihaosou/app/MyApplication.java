package com.qihaosou.app;


import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.context.GlobalContext;
import com.qihaosou.R;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.net.VolleyHelper;
import com.qihaosou.util.L;
import com.umeng.socialize.PlatformConfig;

/**
 * Author: wenjundu
 * Email:179451678@qq.com
 * Date:2016/1/12
 * Description:Application
 */
public class MyApplication extends GlobalContext {

    @Override
    public void onCreate() {
        super.onCreate();
        L.isDebug=true;
       // VolleyHelper.getInstance().init(this);
        LoadingAndRetryManager.BASE_LOADING_LAYOUT_ID = R.layout.loading;
        LoadingAndRetryManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        LoadingAndRetryManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
        PlatformConfig.setWeixin("wx1596167db4f5cd13", "930dcfcb2007dd9f16e2eece4708a3b6");
        PlatformConfig.setQQZone("1105146713", "7ZT2Qy3jwgJimRdc");
        com.umeng.socialize.utils.Log.LOG = true;
        OkHttpUtils.debug(true, "MyOkHttp");    //是否打开调试
        ActiveAndroid.initialize(this);
        try {
            OkHttpUtils.getInstance()//
                    .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)//全局的连接超时时间
                    .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)//全局的读取超时时间
                    .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS);//全局的写入超时时间
                    //.setCertificates(getAssets().open("srca.cer"), getAssets().open("zhy_server.cer"))//
                   // .setCertificates(new Buffer().writeUtf8(CER_12306).inputStream());//设置自签名网站的证书

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onLowMemory() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();
    }

    public void exitApp() {
        BaseAppManager.getInstance().clearActivity();
        System.gc();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
