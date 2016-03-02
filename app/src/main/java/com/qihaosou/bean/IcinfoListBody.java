package com.qihaosou.bean;

import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/2
 * Description:
 */
public class IcinfoListBody {
    public List<IcinfoBean> getIcinfoAll() {
        return icinfoAll;
    }

    public void setIcinfoAll(List<IcinfoBean> icinfoAll) {
        this.icinfoAll = icinfoAll;
    }

    private List<IcinfoBean> icinfoAll;
}
