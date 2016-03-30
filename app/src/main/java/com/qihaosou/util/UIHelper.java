package com.qihaosou.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.qihaosou.ui.activity.AgreementActivity;
import com.qihaosou.ui.activity.CompanyPatentActivity;
import com.qihaosou.ui.activity.CompanySearchActivity;
import com.qihaosou.ui.activity.CopyRightActivity;
import com.qihaosou.ui.activity.CourtListActivity;
import com.qihaosou.ui.activity.DemoActivity;
import com.qihaosou.ui.activity.DishonestyActivity;
import com.qihaosou.ui.activity.EnterpriseDetailInfoActivity;
import com.qihaosou.ui.activity.EnterpriseInfoDetailsActivity;
import com.qihaosou.ui.activity.LoginActivity;
import com.qihaosou.ui.activity.LoginBindActivity;
import com.qihaosou.ui.activity.MainActivity;
import com.qihaosou.ui.activity.MarkListActivity;
import com.qihaosou.ui.activity.RecruitInfoActivity;
import com.qihaosou.ui.activity.RegisterActivity;
import com.qihaosou.ui.activity.ResetPasswordActivity;
import com.qihaosou.ui.activity.TenderActivity;
import com.qihaosou.ui.activity.UniteLoginActivity;
import com.qihaosou.ui.activity.UserActivity;
import com.qihaosou.ui.activity.WebInfoActivity;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/22
 * Description:
 */
public class UIHelper {

    /**
     * 显示登录界面
     *
     * @param context
     */
    public static void showLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    /**
     * 显示注册界面
     */
    public static void showRegisterActivity(Context context,String action){
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.setAction(action);
        context.startActivity(intent);
    }
    /**
     * 显示注册绑定界面
     */
    public static void showBindRegisterActivity(Context context,String action,Bundle bundle){
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.setAction(action);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 显示忘记密码界面
     */
    public static void showResetPasswordActivity(Context context){
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }
    /**
     * 显示搜索界面
     */
    public static void showSearchActivity(Context context){
        Intent intent=new Intent(context, CompanySearchActivity.class);
        context.startActivity(intent);
    }
    /**
     * 显示服务协议界面
     */
    public static void showAgreementActivity(Context context){
        Intent intent=new Intent(context, AgreementActivity.class);
        context.startActivity(intent);
    }
    /**
     * 显示个人中心页面
     */
    public static void showUserActivity(Context context){
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
    }
    /**
     * 显示企业详细信息
     */
    public static void showEnterpriseDetailInfoActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, EnterpriseDetailInfoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 显示工商信息页
     *
     */
    public static void showEnterpriseInfoDetailsActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, EnterpriseInfoDetailsActivity.class);
       intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 显示WebInfoActivity
     */
    public static void showWebInfoActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, WebInfoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 显示MarkListActivity
     */
    public static void showMarkListActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, MarkListActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * CompanyPatentActivity
     */
    public static void showCompanyPatentActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, CompanyPatentActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    };
    /**
     * CopyRightActivity
     */
    public static void showCopyRightActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, CopyRightActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * CourtListActivity(法院诉讼)
     */
    public static void showCourtListActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, CourtListActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 显示DishonestyActivity（失信)
     */
    public static void showDishonestyActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, DishonestyActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 显示RecruitInfoActivity（招聘）
     */
    public static void showRecruitInfoActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, RecruitInfoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 显示TenderActivity（招投标信息）
     *
     */
    public static void showTenderActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, TenderActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 显示联合登录界面
     */
    public static void showUniteLoginActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, UniteLoginActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 显示登录绑定界面
     */
    public static void showLoginBindActivity(Context context,Bundle bundle){
        Intent intent=new Intent(context, LoginBindActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 显示首页
     */
    public static void showMainActivity(Context context){
        Intent intent=new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
