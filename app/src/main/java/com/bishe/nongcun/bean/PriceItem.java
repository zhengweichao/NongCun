package com.bishe.nongcun.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @ 创建时间: 2017/8/24 on 18:39.
 * @ 描述：最近报价条目
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class PriceItem extends BmobObject implements Serializable {
    private String title;
    private String content;
    private MyUser author;
    private String kind1;
    private String kind2;
    private String kind3;

    private String price;
    private BmobFile pic1;
    private BmobFile pic2;
    private BmobFile pic3;

    public BmobFile getPic1() {
        return pic1;
    }

    public void setPic1(BmobFile pic1) {
        this.pic1 = pic1;
    }

    public BmobFile getPic2() {
        return pic2;
    }

    public void setPic2(BmobFile pic2) {
        this.pic2 = pic2;
    }

    public BmobFile getPic3() {
        return pic3;
    }

    public void setPic3(BmobFile pic3) {
        this.pic3 = pic3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public String getKind1() {
        return kind1;
    }

    public void setKind1(String kind1) {
        this.kind1 = kind1;
    }

    public String getKind2() {
        return kind2;
    }

    public void setKind2(String kind2) {
        this.kind2 = kind2;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKind3() {
        return kind3;
    }

    public void setKind3(String kind3) {
        this.kind3 = kind3;
    }
}
