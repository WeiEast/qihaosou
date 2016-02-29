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
    private String message;
    private T data;
    private List<T> datalist;
    public List<T> getDatalist() {
        return datalist;
    }
    public void setDatalist(List<T> datalist) {
        this.datalist = datalist;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

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


}
