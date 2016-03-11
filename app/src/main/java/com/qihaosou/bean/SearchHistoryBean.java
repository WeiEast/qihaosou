package com.qihaosou.bean;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
/**
 * Author: Created by wenjundu
 * Date:on 2016/3/11
 * Description:
 */
@Table(name = "search_history")
public class SearchHistoryBean extends Model {
   public SearchHistoryBean(String key){
       super();
       this.key=key;
   }
    public SearchHistoryBean(){
        super();
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    @Column
    public String key;

    @Override
    public String toString() {
        return key;
    }
}
