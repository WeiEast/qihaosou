package com.qihaosou.app;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/25
 * Description:常量信息
 */
public class Constants {
    private static final String BASE_URL="http://192.168.1.40:8080/main?version=1";
    //登录url
    public static final String LOGIN_URL=BASE_URL+"&cmd=login";
    //注册url
    public static final String REGISTER_URL=BASE_URL+"&cmd=register";
    //注销url
    public static final String LOGINOUT_URL=BASE_URL+"&cmd=loginOut";
    //工商信息主页接口
    public static final String GET_ICINFO=BASE_URL+"&cmd=getIcinfo";
    //主要人员接口
    public static final String GET_ICINFO_EMP=BASE_URL+"&cmd=getIcinfoEmp";
    //获取验证码
    public static final String GET_V_CODE_URL=BASE_URL+"&cmd=sendVcode";
    //关注
    public static final String  ATTENTION_URL=BASE_URL+"&cmd=insertAttention";
    //关注列表
    public static final String GET_ATTENTION_LIST_URL=BASE_URL+"&cmd=getAttention";
    //取消关注
    public static final String CANCEL_ATTENTION_URL=BASE_URL+"&cmd=cancelAttention";
}
