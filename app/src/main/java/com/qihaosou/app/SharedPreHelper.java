package com.qihaosou.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.qihaosou.bean.UserBean;
import com.qihaosou.util.L;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/25
 * Description:
 */
public class SharedPreHelper {
    public static final String KEY = "com.qihaosou.shared_help_key";
    private static Context mContext;

    private SharedPreHelper() {
    }

    public static void config(Context context) {
        mContext = context;
    }
    /**
     * 获取string，默认值为""
     *
     * @param key
     * @return
     */
    public static String getShareData(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
    /**
     * 获取string
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getShareData(String key, String defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }


    /**
     * 获取int
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int getIntShareData(String key, int defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static int getIntShareData(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public static boolean getBooleanShareData(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    public static boolean getBooleanShareData(String key, boolean defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public static void putShareData(String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString(key, value);
        et.commit();
    }

    public static void putIntShareData(String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putInt(key, value);
        et.commit();
    }

    public static void putBooleanShareData(String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putBoolean(key, value);
        et.commit();
    }

    public static void putSetShareData(String key, Set<String> value) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putStringSet(key, value);
        et.commit();
    }

    public static Set<String> getSetShareData(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getStringSet(key, new HashSet<String>());
    }

    public static void putUserInfo(UserBean userBean) throws IllegalAccessException {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        Field[] fields = userBean.getClass().getDeclaredFields();//获得所有对象属性
        for(Field field:fields){
            field.setAccessible(true);
            if( field.getType() == String.class){
                et.putString(field.getName(),(String)field.get(userBean));
            }
            et.commit();
        }
    }
    public static UserBean getUserInfo() throws IllegalAccessException {
        SharedPreferences sp = mContext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        UserBean userBean=new UserBean();
        Field[] fields = userBean.getClass().getDeclaredFields();//获得所有对象属性
        for(Field field:fields){
            field.setAccessible(true);
            if( field.getType() == String.class){
               field.set(userBean, sp.getString(field.getName(),""));
            }
        }
        return userBean;
    }
}
