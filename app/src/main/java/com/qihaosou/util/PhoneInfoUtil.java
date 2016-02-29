package com.qihaosou.util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/28
 * Description:手机信息
 */
public class PhoneInfoUtil {

    //获取IMEI
    public static String getPhoneIMEI(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
    //获取手机号码
    public static String getPhoneNumber(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }
}
