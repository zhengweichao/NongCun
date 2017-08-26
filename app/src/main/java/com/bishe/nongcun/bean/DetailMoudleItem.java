package com.bishe.nongcun.bean;

import android.support.annotation.DrawableRes;

import com.bishe.nongcun.R;

import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.IMultiItem;

/**
 * @ 创建时间: 2017/5/14 on 9:10.
 * @ 描述：分类条目
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class DetailMoudleItem implements IMultiItem {
    public String mText;

    public DetailMoudleItem(String text) {
        mText = text;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_mou_detail;
    }

    @Override
    public void convert(BaseViewHolder holder) {
        holder.setText(R.id.tv_detail_item_name,mText);
    }

    @Override
    public int getSpanSize() {
        return 0;
    }


}
