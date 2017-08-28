package com.bishe.nongcun.adapter;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.WantBuyItem;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.IMultiItem;

/**
 * @ 创建时间: 2017/8/27 on 17:35.
 * @ 描述：
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MyPushAdapter extends BaseAdapter<WantBuyItem> {
    @Override
    public int getLayoutRes(int index) {
        return R.layout.item_foods;
    }

    @Override
    public void convert(BaseViewHolder holder, WantBuyItem data, int index) {
        holder.setText(R.id.tv_item_foods_name,data.getTitle());
        holder.setText(R.id.tv_item_foods_time,data.getCreatedAt());
        holder.setText(R.id.tv_item_foods_address,data.getAuthor().getAddress());
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {

    }
}
