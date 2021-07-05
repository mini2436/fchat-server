package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回状态枚举定义
 *
 * @author mini2436
 * @date 2021-07-05 08:59
 **/
@Getter
@AllArgsConstructor
public enum  ResultEnum {
    /**
     * 请求成功定义
     */
    success(200,"request success"),
    /**
     * 请求失败
     */
    FAIL(404,"request fail"),
    /**
     * 服务端数据处理异常
     */
    ERROR(500,"server error")
    ;
    /**
     * 请求状态定义
     */
    private final Integer code;
    /**
     * 状态消息定义
     */
    private final String message;
}
