package com.bishe.nongcun.bean;

/**
 * @ 创建时间: 2017/5/22 on 12:13.
 * @ 描述：
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MainNews {
    private String name;
    private String price;
    private String date;
    private String address;

    public MainNews( String name, String price, String date, String address) {
        this.name=name;
        this.price=price;
        this.date=date;
        this.address=address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
