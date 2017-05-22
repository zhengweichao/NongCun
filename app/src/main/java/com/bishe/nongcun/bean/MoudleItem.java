package com.bishe.nongcun.bean;

import android.support.annotation.DrawableRes;

import com.bishe.nongcun.R;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.IMultiItem;

/**
 * @ 创建时间: 2017/5/14 on 9:10.
 * @ 描述：模块条目
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MoudleItem implements IMultiItem {
    @DrawableRes
    private int mImageResId;
    private String mImageTitle;
    private Class clazz;

    public MoudleItem(int imageResId, String imageTitle, Class clazz) {
        this.mImageResId = imageResId;
        this.mImageTitle = imageTitle;
        this.clazz=clazz;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_list;
    }

    @Override
    public void convert(BaseViewHolder holder) {
        holder.setText(R.id.tv_item_name,mImageTitle);
        holder.setImage(R.id.iv_item_logo,mImageResId);
    }

    @Override
    public int getSpanSize() {
        return 0;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public int getmImageResId() {
        return mImageResId;
    }

    public void setmImageResId(int mImageResId) {
        this.mImageResId = mImageResId;
    }

    public String getmImageTitle() {
        return mImageTitle;
    }

    public void setmImageTitle(String mImageTitle) {
        this.mImageTitle = mImageTitle;
    }
}
