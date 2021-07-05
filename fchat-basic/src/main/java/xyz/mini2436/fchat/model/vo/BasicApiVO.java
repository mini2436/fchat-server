package xyz.mini2436.fchat.model.vo;

import xyz.mini2436.fchat.enums.ResultEnum;

import java.io.Serializable;

/**
 * 通用返回状态定义
 *
 * @author mini2436
 * @date 2021-07-05 21:39
 **/
public class BasicApiVO implements Serializable {
    private static final long serialVersionUID = 308039172131284282L;
    public ResultVO<String> success(){
        return ResultVO.<String>builder().code(ResultEnum.SUCCESS.getCode()).msg(ResultEnum.SUCCESS.getContent()).data(ResultEnum.SUCCESS.getContent()).build();
    }
    public ResultVO<String> success(String msg){
        return ResultVO.<String>builder().code(ResultEnum.SUCCESS.getCode()).msg(msg).data(msg).build();
    }
    public <T> ResultVO<T> success(String msg , T data){
        return ResultVO.<T>builder().code(ResultEnum.SUCCESS.getCode()).msg(msg).data(data).build();
    }
    public <T> ResultVO<T> success(Integer code , String msg , T data){
        return ResultVO.<T>builder().code(code).msg(msg).data(data).build();
    }

    public ResultVO<String> fail(){
        return ResultVO.<String>builder().code(ResultEnum.FAIL.getCode()).msg(ResultEnum.FAIL.getContent()).data(ResultEnum.FAIL.getContent()).build();
    }
    public ResultVO<String> fail(String msg){
        return ResultVO.<String>builder().code(ResultEnum.FAIL.getCode()).msg(msg).data(msg).build();
    }
    public <T> ResultVO<T> fail(String msg , T data){
        return ResultVO.<T>builder().code(ResultEnum.FAIL.getCode()).msg(msg).data(data).build();
    }
    public <T> ResultVO<T> fail(Integer code , String msg , T data){
        return ResultVO.<T>builder().code(code).msg(msg).data(data).build();
    }
}
