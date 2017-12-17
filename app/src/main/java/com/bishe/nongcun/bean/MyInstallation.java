package com.bishe.nongcun.bean;

import cn.bmob.v3.BmobInstallation;

/**
 * @ 创建时间: 2017/11/6 on 10:47.
 * @ 描述：
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class MyInstallation extends BmobInstallation {
    private MyUser myuser;

    public MyUser getMyuser() {
        return myuser;
    }

    public void setMyuser(MyUser myuser) {
        this.myuser = myuser;
    }
}
