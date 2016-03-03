package com.qihaosou.callback;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.bean.IcinfoBean;
import com.qihaosou.bean.UserBean;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/3
 * Description:
 */
public abstract class IcinfoBeanCallBack extends AbsCallback<IcinfoBean>{
    @Override
    public IcinfoBean parseNetworkResponse(Response response) throws Exception {
        JSONObject jsonObject=new JSONObject(response.body().string());
        if("0000".equals(jsonObject.optString("code"))){

            return new Gson().fromJson(jsonObject.optJSONObject("body").optString("icinfo"),IcinfoBean.class);
        }else{
            throw new TaskException(String.valueOf(jsonObject.optString("code")), jsonObject.optString("message"));
        }
    }
}
