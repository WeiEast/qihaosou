package com.qihaosou.bean;

import java.util.List;
import java.util.Map;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/27
 * Description:
 */
public class BaseBean<T>{
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body =body ;
    }

    private String message;
    private T body;
}
