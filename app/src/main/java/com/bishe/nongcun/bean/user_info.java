package com.bishe.nongcun.bean;


import cn.bmob.v3.BmobObject;

/**
 * @ 创建时间: 2017/8/23 on 10:14.
 * @ 描述：用户信息bean类型
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class user_info extends BmobObject{
    private String username;
    private String tel;
    private String password;
    private Boolean isreal;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsreal() {
        return isreal;
    }

    public void setIsreal(Boolean isreal) {
        this.isreal = isreal;
    }
}
