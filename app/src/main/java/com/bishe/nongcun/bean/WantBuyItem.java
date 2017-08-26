package com.bishe.nongcun.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * @ 创建时间: 2017/8/23 on 15:39.
 * @ 描述：求购条目
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class WantBuyItem extends BmobObject implements Serializable{
    private String title;
    private String content;
    private String count;
    private String kind1;
    private String kind2;
    private String kind3;
    private MyUser author;


    public WantBuyItem() {
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getKind3() {
        return kind3;
    }

    public void setKind3(String kind3) {
        this.kind3 = kind3;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }
}
