package com.qihaosou.util;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.qihaosou.bean.SearchHistoryBean;
import java.util.Collections;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/11
 * Description:
 */
public final class DBUtils {
    private DBUtils() {
    }
    /**
     * 清空历史
     */
    public static void clearSearchHistory() {
        new Delete().from(SearchHistoryBean.class).execute();
    }

    public static List<SearchHistoryBean> getSearchHistory() {
        List<SearchHistoryBean> list=new Select().from(SearchHistoryBean.class).execute();
        Collections.reverse(list);
        return list;
    }
    public static void saveSearch(SearchHistoryBean searchHistoryBean){
        new Delete().from(SearchHistoryBean.class).where("key=?",searchHistoryBean.getKey()).execute();
        if(getSaveSearchNum()>=50){
            SearchHistoryBean.delete(SearchHistoryBean.class,1);
        }
        searchHistoryBean.save();
    }

    public static int getSaveSearchNum() {
        List<SearchHistoryBean> execute = new Select().from(SearchHistoryBean.class).execute();
        return execute.size();
    }
}
