package com.qihaosou.util;

import android.content.Context;
import android.content.Intent;

import com.qihaosou.ui.activity.LoginActivity;
import com.qihaosou.ui.activity.UserActivity;

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
    public static void showRegisterActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    /**
     * 显示忘记密码界面
     */
    public static void showResetPasswordActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    /**
     * 显示个人中心页面
     */
    public static void showUserActivity(Context context){
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
    }
}
