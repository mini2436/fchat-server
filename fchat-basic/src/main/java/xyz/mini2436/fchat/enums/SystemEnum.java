package xyz.mini2436.fchat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统枚举
 *
 * @author mini2436
 * @date 2021-07-16 11:37
 **/
@Getter
@AllArgsConstructor
public enum SystemEnum {
    /**
     * webflux上下文关系数据存储userId
     */
    WEBFLUX_CONTEXT_DATA_USER_ID("userId"),
    /**
     * webflux上下文关系数据存储userInfo
     */
    WEBFLUX_CONTEXT_DATA_USER_INFO("userInfo"),
    /**
     * webflux上下文关系数据存储userInfo
     */
    WEBFLUX_CONTEXT_DATA_USER_TOKEN("userToken"),
    /**
     * 登录的用户redis数据存储
     */
    REDIS_LOGIN_USERINFO_PATH("FCHAT:USER:LOGIN:USERINFO"),
    /**
     * 登录的用户redis数据存储
     */
    REDIS_LOGIN_TOKEN_PATH("FCHAT:USER:LOGIN:TOKEN"),
    /**
     * Web登录设备
     */
    WEB_LOGIN_EQUIPMENT("WEB"),
    /**
     * 移动登录设备
     */
    MOBILE_LOGIN_EQUIPMENT("MOBILE"),
    /**
     * 桌面登录设备
     */
    DESKTOP_LOGIN_EQUIPMENT("DESKTOP"),
    ;
    /**
     * 状态消息定义
     */
    private final String content;
}
