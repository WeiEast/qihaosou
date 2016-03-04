package com.qihaosou.callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.bean.AnnualBean;
import com.qihaosou.bean.PartnerBean;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/4
 * Description:
 */
public abstract class PartnerAllCallBack extends AbsCallback<List<PartnerBean>>{
    @Override
    public List<PartnerBean> parseNetworkResponse(Response response) throws Exception {
        JSONObject jsonObject = new JSONObject(response.body().string());
        if ("0000".equals(jsonObject.optString("code"))) {
            return new Gson().fromJson(jsonObject.optJSONObject("body").optString("partnerAll"), new TypeToken<List<PartnerBean>>() {
            }.getType());
        }else{
            throw new TaskException(String.valueOf(jsonObject.optString("code")), jsonObject.optString("message"));
        }
    }
}
