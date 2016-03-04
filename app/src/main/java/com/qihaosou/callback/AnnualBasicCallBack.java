package com.qihaosou.callback;

import com.google.gson.Gson;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.bean.AnnualBasicBean;
import com.qihaosou.bean.HomepageBean;

import org.json.JSONObject;

import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/4
 * Description:
 */
public abstract class AnnualBasicCallBack extends AbsCallback<AnnualBasicBean>{
    @Override
    public AnnualBasicBean parseNetworkResponse(Response response) throws Exception {
        JSONObject jsonObject=new JSONObject(response.body().string());
        if("0000".equals(jsonObject.optString("code"))){

            return new Gson().fromJson(jsonObject.optJSONObject("body").optString("annualBasic"),AnnualBasicBean.class);
        }else{
            throw new TaskException(String.valueOf(jsonObject.optString("code")), jsonObject.optString("message"));
        }
    }
}
