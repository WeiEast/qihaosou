package com.qihaosou.callback;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.bean.QihaosouBean;
import com.qihaosou.bean.VcodeBean;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/3
 * Description:
 */
public abstract class QihaosouBeanCallBack extends AbsCallback<QihaosouBean>{
    @Override
    public QihaosouBean parseNetworkResponse(Response response) throws Exception {
        JSONObject jsonObject=new JSONObject(response.body().string());
        if("0000".equals(jsonObject.optString("code"))){
            return new Gson().fromJson(jsonObject.toString(),QihaosouBean.class);
        }else{
            throw new TaskException(String.valueOf(jsonObject.optString("code")), jsonObject.optString("message"));
        }
    }
}
