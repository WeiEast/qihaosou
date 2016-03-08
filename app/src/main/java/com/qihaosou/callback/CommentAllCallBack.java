package com.qihaosou.callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.bean.AnnualBean;
import com.qihaosou.bean.CommentBean;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/8
 * Description:
 */
public abstract class CommentAllCallBack extends AbsCallback<List<CommentBean>> {
    @Override
    public List<CommentBean> parseNetworkResponse(Response response) throws Exception {
        JSONObject jsonObject = new JSONObject(response.body().string());
        if ("0000".equals(jsonObject.optString("code"))) {
            return new Gson().fromJson(jsonObject.optJSONObject("body").optString("commentAll"), new TypeToken<List<CommentBean>>() {
            }.getType());
        }else{
            throw new TaskException(String.valueOf(jsonObject.optString("code")), jsonObject.optString("message"));
        }
    }
}
