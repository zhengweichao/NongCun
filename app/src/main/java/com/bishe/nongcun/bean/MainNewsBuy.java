package com.bishe.nongcun.bean;

/**
 * @ 创建时间: 2017/5/22 on 12:13.
 * @ 描述：主页面最新求购
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MainNewsBuy {
    private String name;
    private String count;
    private String phone_num;
    private String address;

    public MainNewsBuy(String name, String count, String phone_num, String address) {
        this.name=name;
        this.count = count;
        this.phone_num = phone_num;
        this.address=address;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
