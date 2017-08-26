package com.bishe.nongcun.adapter;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.IMultiItem;

/**
 * @ 创建时间: 2017/5/13 on 13:09.
 * @ 描述：
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class DetailMoudleAdapter extends BaseAdapter<IMultiItem> {

    @Override
    public int getLayoutRes(int index) {
        final IMultiItem data = mData.get(index);
        return data.getLayoutRes();
    }

    @Override
    public void convert(BaseViewHolder holder, IMultiItem data, int index) {
        data.convert(holder);
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {

    }

}
