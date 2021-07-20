package xyz.mini2436.fchat.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用返回类属性定义
 *
 * @author mini2436
 * @date 2021-07-05 21:26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultVo<T> implements Serializable{
    private static final long serialVersionUID = 9014286245366093696L;

    /**
     * 返回状态自定义code
     */
    private Integer code;
    /**
     * 返回消息定义
     */
    private String msg;
    /**
     * 返回请求体定义
     */
    private T data;
}
