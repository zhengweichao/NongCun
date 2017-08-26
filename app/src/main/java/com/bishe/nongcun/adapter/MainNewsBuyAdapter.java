package com.bishe.nongcun.adapter;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MainNewsBuy;
import com.bishe.nongcun.bean.MainNewsPrice;
import com.bishe.nongcun.bean.WantBuyItem;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;

/**
 * @ 创建时间: 2017/5/22 on 12:16.
 * @ 描述：
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MainNewsBuyAdapter extends BaseAdapter<WantBuyItem> {
    @Override
    public int getLayoutRes(int index) {
        return R.layout.item_new_buy;
    }

    @Override
    public void convert(BaseViewHolder holder, WantBuyItem data, int index) {
        holder.setText(R.id.new_buy_address,data.getAuthor().getAddress());
        holder.setText(R.id.new_buy_count,data.getCount());
        holder.setText(R.id.new_buy_name,data.getKind2());
//        holder.setText(R.id.new_buy_phone_num,data.getPhone_num());
    }

    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {

    }

}
