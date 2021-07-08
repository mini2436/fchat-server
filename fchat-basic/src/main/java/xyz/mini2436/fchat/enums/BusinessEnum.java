package xyz.mini2436.fchat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务处理枚举
 *
 * @author mini2436
 * @date 2021-07-05 09:05
 **/
@Getter
@AllArgsConstructor
public enum  BusinessEnum {
    /**
     * 数据异常
     */
    DATABASE_ERROR(5010,"数据异常"),
    /**
     * 消息异常
     */
    MESSAGE_ERROR(5020,"消息异常"),
    /**
     * 参数异常
     */
    PARAMETER_ERROR(5030,"参数异常"),
    /**
     * 业务处理异常
     */
    SERVICE_ERROR(5040,"业务处理异常"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(5050,"系统异常"),
    ;
    /**
     * 请求状态定义
     */
    private final Integer code;
    /**
     * 状态消息定义
     */
    private final String content;
}
