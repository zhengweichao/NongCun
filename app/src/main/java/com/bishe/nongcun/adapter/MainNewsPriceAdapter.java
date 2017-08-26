package com.bishe.nongcun.adapter;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MainNewsPrice;
import com.bishe.nongcun.bean.PriceItem;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;

/**
 * @ 创建时间: 2017/5/22 on 12:16.
 * @ 描述：
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MainNewsPriceAdapter extends BaseAdapter<PriceItem> {
    @Override
    public int getLayoutRes(int index) {
        return R.layout.item_new_price;
    }

    @Override
    public void convert(BaseViewHolder holder, PriceItem data, int index) {
        holder.setText(R.id.new_price_address, data.getAuthor().getAddress());
        holder.setText(R.id.new_price_date, data.getCreatedAt().substring(5, 7) + "月"
                + data.getCreatedAt().substring(8, 10) + "日");
        holder.setText(R.id.new_price_name, data.getKind2());
        holder.setText(R.id.new_price_price, data.getPrice());
    }


    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {

    }

}
