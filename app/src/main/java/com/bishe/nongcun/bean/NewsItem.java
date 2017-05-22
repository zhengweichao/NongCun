package com.bishe.nongcun.bean;

import android.graphics.Point;
import android.support.annotation.DrawableRes;
import android.util.Log;

import com.bishe.nongcun.R;
import com.bishe.nongcun.utils.FrescoUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.IMultiItem;

/**
 * 文 件 名: NewsItem
 * 创 建 人: 郑卫超
 * 创建日期: 2016/12/23 19:42
 */

public class NewsItem implements IMultiItem {
    @DrawableRes
    private int mImageId;

    private int mSpanSize;

    private int mImageWidth;

    private Point mImageViewSize;

    public NewsItem(int imageId ,int width, int spanSize) {
        mImageId = imageId;
        mSpanSize = spanSize;
        mImageWidth = width * mSpanSize;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_foot;
    }

    @Override
    public void convert(BaseViewHolder holder) {
        holder.setImage(R.id.iv_home_newsitem,mImageId);
        holder.setText(R.id.tv_home_newsname,"标题");
        holder.setText(R.id.tv_home_newscon,"内容");

    }

    @Override
    public int getSpanSize() {
        return mSpanSize;
    }
}
