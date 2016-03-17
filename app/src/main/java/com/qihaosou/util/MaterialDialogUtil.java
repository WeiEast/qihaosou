package com.qihaosou.util;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/16
 * Description:
 */
public class MaterialDialogUtil {
    public static MaterialDialog getNormalProgressDialog(Context context,String content) {
      return  new MaterialDialog.Builder(context)
              .content(content)
              .progress(true, 0).cancelable(false).build();
    }
}
