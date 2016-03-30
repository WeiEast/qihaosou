package com.qihaosou.app;

import android.os.Build;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/25
 * Description:常量信息
 */
public class Constants {

    private static final String BASE_URL="http://service.qihaosou.com/qihaosou/main?version=1";

    public static final String APK_DOWNLOAD_URL = "downloagUrl";
    public static final String APK_UPDATE_CONTENT = "forceUpdate";
    public static final String APK_VERSION_CODE = "newVersion";



    //图片URL
    public static final String BASE_IMAGE_URL="http://img.qihaosou.com/avatar/";
    //登录url
    public static final String LOGIN_URL=BASE_URL+"&cmd=login";
    //检测绑定
    public static final String CHECK_UID_URL=BASE_URL+"&cmd=checkUid";
    //绑定
    public static final String BIND_UID_URL=BASE_URL+"&cmd=bindingUid";
    //注册url
    public static final String REGISTER_URL=BASE_URL+"&cmd=register";
    //注销url
    public static final String LOGINOUT_URL=BASE_URL+"&cmd=loginOut";
    //重设密码
    public static final String REPASSWORD_URL=BASE_URL+"&cmd=rePassword";
    //热门企业
    public static final String GET_HOT_COMPANY_URL=BASE_URL+"&cmd=getHotCompany";
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
    //专利
    public static final String GET_PATENT_OUTLINE_URL=BASE_URL+"&cmd=getPatentOutline";
    //作品著作权
    public static final String GET_WORK_COPYRIGHT_URL=BASE_URL+"&cmd=getWorkCopyright";
    //软件著作权
    public static final String GET_COMPUTER_COPYRIGHT_URL=BASE_URL+"&cmd=getComputerCopyright";
    //招聘信息
    public static final String GET_RECRUITINFO_URL=BASE_URL+"&cmd=getRecruitInfoList";
    //招投标
    public static final String GET_TENDERS_URL=BASE_URL+"&cmd=getTenders";
    //诉讼列表
    public static final String GET_COURTLIST_URL=BASE_URL+"&cmd=getCourtList";
    //法律文书
    public static final String GET_COURT_URL=BASE_URL+"&cmd=getCourt";
    //评论列表
    public static final String GET_COMMENT_URL=BASE_URL+"&cmd=getComment";
    //模糊搜索
    public static final String VAGUE_QUERY_URL=BASE_URL+"&cmd=vagueQuery";
    //失信列表
    public static final String GET_DISHONESTYLIST_URL=BASE_URL+"&cmd=getDishonestyList";
    //失信详情
    public static final String GET_DISHONESTY_URL=BASE_URL+"&cmd=getDishonesty";
    //版本更新
    public static final String GET_NEW_VERSION_URL=BASE_URL+"&cmd=getNewVersion";
    //上传图片
    public static final String UPLOAD_AVATAR_URL=BASE_URL+ "&cmd=uploadAvatar";
}
