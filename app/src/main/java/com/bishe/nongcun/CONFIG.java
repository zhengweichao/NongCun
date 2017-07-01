package com.bishe.nongcun;

import com.bishe.nongcun.bean.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期:2016/12/5 on 21:19
 * 描述:
 * 作者:郭士超
 * QQ:1169380200
 */

public class CONFIG {

    //    登录网址
    public static final String URL_LOGIN = "http://123.207.155.175:8080/Examine1/LogLet";
    //    注册网址
    public static final String URL_SIGNUP = "http://123.207.155.175:8080/Examine1/RegLet";

    public static final String SERVER_URL = "http://nust.cc/pi/index.php/api/Client/";
    public static final String APP_ID = "com.cc.alarmclock";
    public static final String CHARSET = "utf-8";

    public static final String KEY_ID = "id";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ALARM_ID = "alarm_id";
    public static final String KEY_MEMORANDUM_ID = "memorandum_id";
    public static final String KEY_CONTROL_ID = "control_id";
    public static final String KEY_DISPLAY_ID = "display_id";
    public static final String KEY_ERRMSG = "errmsg";
    public static final String KEY_MSG = "msg";
    public static final String KEY_DATA = "data";
    public static final String KEY_STATE = "state";
    public static final String KEY_REPEAT = "repeat";
    public static final String KEY_TIME = "time";
    public static final String KEY_REMARKS = "remarks";
    public static final String KEY_REMIND = "remind";
    public static final String KEY_VOICE = "voice";
    public static final String KEY_SHOCK = "shock";
    public static final String KEY_TEXT = "text";
    public static final String KEY_COLOR = "color";
    public static final String KEY_ITEM_ID = "itemId";
    public static final String KEY_AGO = "ago";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADD = "add";
    public static final String KEY_CODE = "code";
    public static final String KEY_INDOOR = "indoor";
    public static final String KEY_OUTDOOR = "outdoor";
    public static final String KEY_LINE = "line";
    public static final String KEY_FIRST = "first_line";
    public static final String KEY_SECOND = "second_line";
    public static final String KEY_UPDATE_CONTENT = "update_content";

    public static final String KEY_ACTIVITY = "activity";
    public static final String ATY_ALARM = "alarm";
    public static final String ATY_MESSAGE = "message";
    public static final String ATY_MEMORANDUM = "memorandum";
    public static final String ATY_SETTING = "setting";

    public static final String ERRMSG_OK = "ok";
    public static final String ERRMSG_401 = "401";//表示token过期
    public static final String ERRMSG_ERR = "err";//表示请求超时
    public static final String ERRMSG_MSG = "请求超时，请检查网络连接";//表示请求超时

    public static final String DEVICE_ADD ="index/login";
    public static final String DEVICE_GET ="Screen/state";
    public static final String ALARM_GET ="Alarm/get";
    public static final String ALARM_ADD ="Alarm/add";
    public static final String ALARM_SET ="Alarm/set";
    public static final String ALARM_DELETE ="Alarm/delete";
    public static final String MESSAGE_SEND ="Message/add";
    public static final String GET_VOICE="Alarm/voice";
    public static final String KEY_ENVIRONMENT_GET="Environment/get";
    public static final String MEMORANDUM_GET ="Memorandum/get";
    public static final String MEMORANDUM_ADD ="Memorandum/add";
    public static final String MEMORANDUM_SET ="Memorandum/set";
    public static final String MEMORANDUM_DELETE ="Memorandum/delete";
    public static final String CONTROL_GET ="Control/get";
    public static final String CONTROL_SET ="Control/set";
    public static final String SCREEN_GET ="Screen/get";
    public static final String SCREEN_SET ="Screen/set";
    public static final String DISPLAY_GET ="Screen/display";
    public static final String UPDATE ="index/update";

    public static List<Message> messageList= new ArrayList<Message>();

}
