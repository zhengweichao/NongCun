package com.bishe.nongcun.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

/**
 * @ 创建时间: 2017/8/24 on 11:52.
 * @ 描述：用户类型
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MyUser extends BmobUser implements Serializable {
    private String address;
    private String avatar;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
