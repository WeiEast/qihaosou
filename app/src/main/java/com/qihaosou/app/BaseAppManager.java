package com.qihaosou.app;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: wenjundu
 * Email:179451678@qq.com
 * Date:2016/1/12
 * Description:Activity管理
 */
public class BaseAppManager {
    private static final String TAG = BaseAppManager.class.getSimpleName();
    private static BaseAppManager instance = null;
    private static List<Activity> mActivities = new LinkedList<Activity>();

    private BaseAppManager() {

    }
    public synchronized static BaseAppManager getInstance() {
        if (instance == null) {
            instance = new BaseAppManager();
        }
        return instance;
    }
    //返回存储activity 的list的大小
    public int size() {
        return mActivities.size();
    }
    //返回栈顶的activity
    public synchronized Activity getForwardActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }
    //添加activity
    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }
    //移除activity
    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }
    //清空activity
    public synchronized void clearActivity() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }
    }


}
