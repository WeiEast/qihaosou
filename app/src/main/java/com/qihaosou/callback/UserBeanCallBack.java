package com.qihaosou.callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.bean.EmployeeBean;
import com.qihaosou.bean.UserBean;
import com.qihaosou.util.L;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/3
 * Description:
 */
public abstract class UserBeanCallBack extends AbsCallback<UserBean> {
    @Override
    public UserBean parseNetworkResponse(Response response) throws Exception {

        JSONObject jsonObject=new JSONObject(response.body().string());
        if("0000".equals(jsonObject.optString("code"))){

            return new Gson().fromJson(jsonObject.optJSONObject("body").optString("user"),UserBean.class);
        }else{
            throw new TaskException(String.valueOf(jsonObject.optString("code")), jsonObject.optString("message"));
        }

    }


}
