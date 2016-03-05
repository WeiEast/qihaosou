package com.qihaosou.app;

import android.os.Build;

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
    //工商变更
    public static final String GET_ICIONFO_CHANGE_URL= BASE_URL+"&cmd=getIcinfoChange";
    //公司信息主页
    public static final String GET_HOME_PAGE_URL=BASE_URL+"&cmd=getHomepage";
    //年报信息List
    public static final String GET_ICIONFO_ANNUAL_URL=BASE_URL+"&cmd=getIcinfoAnnual";
    //年报详情
    public static final String GET_ICIONFO_ANNUAL_BASIC_URL=BASE_URL+"&cmd=getIcinfoAnnualBasic";
    //投资人信息
    public static final String GET_ICINFO_PARTNER_URL=BASE_URL+"&cmd=getIcinfoPartner";
    //网站备案信息
    public static final String GET_RECORD_URL=BASE_URL+"&cmd=getRecordCmd";
    //商标信息
    public static final String GET_COMPANY_LOGO_URL=BASE_URL+"&cmd=getCompanyLogo";
}
