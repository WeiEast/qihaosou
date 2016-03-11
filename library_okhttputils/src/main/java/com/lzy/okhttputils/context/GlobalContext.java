package com.lzy.okhttputils.context;

import android.app.Application;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/3
 * description:
 */
public class GlobalContext extends Application {
    private static GlobalContext _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = this;
    }

    public static GlobalContext getInstance() {
        return _context;
    }
}
