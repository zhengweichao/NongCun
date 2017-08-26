package com.bishe.nongcun.utils;

import com.bishe.nongcun.bean.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @ 创建时间: 2017/7/14 on 18:16.
 * @ 描述：常用配置变量
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class CONFIG {

    //    登录网址
    public static final String URL_LOGIN = "http://123.207.155.175:8080/Examine1/LogLet";
    //    注册网址
    public static final String URL_SIGNUP = "http://123.207.155.175:8080/Examine1/RegLet";

    public static final String KEY_TEXT = "text";
    public static final String KEY_COLOR = "color";
    public static final String ERRMSG_401 = "401";//表示token过期
    public static List<Message> messageList = new ArrayList<Message>();

}
