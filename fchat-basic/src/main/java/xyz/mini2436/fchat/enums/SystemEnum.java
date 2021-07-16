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
     * 请求成功定义
     */
    REDIS_TOKEN_PATH("FCHAT:USER:TOKEN:"),
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
