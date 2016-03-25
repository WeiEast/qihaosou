package com.qihaosou.callback;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.bean.UploadBean;
import com.qihaosou.bean.UserBean;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/25
 * Description:
 */
public abstract class UploadCallBack extends AbsCallback<UploadBean> {

    @Override
    public UploadBean parseNetworkResponse(Response response) throws Exception {
        JSONObject jsonObject=new JSONObject(response.body().string());
        if("0000".equals(jsonObject.optString("code"))){

            return new Gson().fromJson(jsonObject.optString("body"),UploadBean.class);
        }else{
            throw new TaskException(String.valueOf(jsonObject.optString("code")), jsonObject.optString("message"));
        }
    }


}
