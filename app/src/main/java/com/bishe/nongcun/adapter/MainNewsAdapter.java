package com.bishe.nongcun.adapter;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MainNews;

import xyz.zpayh.adapter.BaseAdapter;
import xyz.zpayh.adapter.BaseViewHolder;

/**
 * @ 创建时间: 2017/5/22 on 12:16.
 * @ 描述：
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MainNewsAdapter extends BaseAdapter<MainNews> {
    @Override
    public int getLayoutRes(int index) {
        return R.layout.item_new_price;
    }

    @Override
    public void convert(BaseViewHolder holder, MainNews data, int index) {
        holder.setText(R.id.new_price_address,data.getAddress());
        holder.setText(R.id.new_price_date,data.getDate());
        holder.setText(R.id.new_price_name,data.getName());
        holder.setText(R.id.new_price_price,data.getPrice());
    }


    @Override
    public void bind(BaseViewHolder holder, int layoutRes) {

    }

}
