package com.qihaosou.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/26
 * Description:
 */
public class ToastUtil {
    private static Toast toast = null;
    /**
     * 底部普通文本消息提示
     * @param context
     * @param text
     */
    public static void TextToast(Context context,CharSequence text){
        //创建一个Toast提示消息
        if(toast!=null){
            toast.cancel();
        }
        toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        //显示消息
        toast.show();
    }
}
