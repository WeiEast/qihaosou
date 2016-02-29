package com.qihaosou.list;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/18
 * Description:
 */
public interface ViewHolderCreator<ItemDataType> {
    public ViewHolderBase<ItemDataType> createViewHolder(int position);
}
