package com.qihaosou.net;

import android.text.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.qihaosou.bean.BaseBean;
import com.qihaosou.util.L;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/27
 * Description:
 */
public class GsonRequest<T> extends Request<T> {
    private static String cookie = "";// 静态标示 cookie
    private static Map<String, String> mHeader = new HashMap<String, String>();
    private final Gson gson = new Gson();
    private static Boolean isTruncate = true;// 截取第一次获得的cookie
    private final Response.Listener<T> listener;
    private final Type type;
    public GsonRequest(int method, String url,Type type, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
        this.type=type;
    }
    /**
     * 设置访问自己服务器时必须传递的参数，密钥等
     */
    static
    {

    }
    public void setCookie(){
        if (!cookie.equals(""))
            mHeader.put("Cookie", cookie);
        mHeader.put("Content-Type","application/json");
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError
    {
        // 默认返回 return Collections.emptyMap();
        if(!TextUtils.isEmpty(cookie))
           setCookie();
        return mHeader;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try
        {
            /**
             * 得到返回的数据
             */
            String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            // 使用正则表达式从reponse的头中提取cookie内容的子串
            Pattern pattern = Pattern.compile("Set-Cookie.*?;");
            Matcher m = pattern.matcher(response.headers.toString());
            L.e(response.headers.toString());
            if (m.find()) {

                //cookie = m.group();
                // L.e("LOG","cookie from server "+ cookie);
                // 去掉cookie末尾的分号
                if (isTruncate) {
                    isTruncate = false;
                    cookie = m.group().substring(11, m.group().length() - 1);
                }
            }
            /**
             * 转化成对象
             */
            T t= gson.fromJson(jsonStr,type);
            return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e)
        {
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
