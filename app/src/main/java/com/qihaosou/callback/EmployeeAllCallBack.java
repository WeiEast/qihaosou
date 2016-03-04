package com.qihaosou.callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.bean.EmployeeBean;
import com.qihaosou.util.L;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/3
 * Description:主要人员回调
 */
public abstract class EmployeeAllCallBack extends AbsCallback<List<EmployeeBean>> {
    @Override
    public List<EmployeeBean> parseNetworkResponse(Response response) throws Exception {
        JSONObject jsonObject = new JSONObject(response.body().string());
        if ("0000".equals(jsonObject.optString("code"))) {
            String employeeAllString = jsonObject.optJSONObject("body").optString("employeeAll");
            return new Gson().fromJson(employeeAllString, new TypeToken<List<EmployeeBean>>() {
            }.getType());
        }else{
            throw new TaskException(String.valueOf(jsonObject.optString("code")), jsonObject.optString("message"));
        }
    }
}
