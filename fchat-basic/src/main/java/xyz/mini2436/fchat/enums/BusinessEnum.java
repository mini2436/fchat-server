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
public class BusinessEnum {
    /**
     * 请求状态定义
     */
    private final Integer code;
    /**
     * 状态消息定义
     */
    private final String content;
}
