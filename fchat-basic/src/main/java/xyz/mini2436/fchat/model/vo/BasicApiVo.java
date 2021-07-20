package xyz.mini2436.fchat.model.vo;

import xyz.mini2436.fchat.enums.BusinessEnum;
import xyz.mini2436.fchat.enums.ResultEnum;

import java.io.Serializable;

/**
 * 通用返回状态定义
 *
 * @author mini2436
 * @date 2021-07-05 21:39
 **/
public class BasicApiVo implements Serializable {
    private static final long serialVersionUID = 308039172131284282L;
    public ResultVo<String> success(){
        return ResultVo.<String>builder().code(ResultEnum.SUCCESS.getCode()).msg(ResultEnum.SUCCESS.getContent()).data(ResultEnum.SUCCESS.getContent()).build();
    }
    public ResultVo<String> success(String msg){
        return ResultVo.<String>builder().code(ResultEnum.SUCCESS.getCode()).msg(msg).data(msg).build();
    }
    public <T> ResultVo<T> success(String msg , T data){
        return ResultVo.<T>builder().code(ResultEnum.SUCCESS.getCode()).msg(msg).data(data).build();
    }
    public <T> ResultVo<T> success(Integer code , String msg , T data){
        return ResultVo.<T>builder().code(code).msg(msg).data(data).build();
    }

    public ResultVo<String> fail(){
        return ResultVo.<String>builder().code(ResultEnum.FAIL.getCode()).msg(ResultEnum.FAIL.getContent()).data(ResultEnum.FAIL.getContent()).build();
    }
    public ResultVo<String> fail(String msg){
        return ResultVo.<String>builder().code(ResultEnum.FAIL.getCode()).msg(msg).data(msg).build();
    }
    public <T> ResultVo<T> fail(String msg , T data){
        return ResultVo.<T>builder().code(ResultEnum.FAIL.getCode()).msg(msg).data(data).build();
    }
    public <T> ResultVo<T> fail(Integer code , String msg , T data){
        return ResultVo.<T>builder().code(code).msg(msg).data(data).build();
    }

    public ResultVo<String> error(BusinessEnum businessEnum, Exception exception){
        return ResultVo.<String>builder().code(businessEnum.getCode()).msg(exception.getMessage()).data(businessEnum.getContent()).build();
    }
    public <T> ResultVo<T> error(BusinessEnum businessEnum, Exception exception , T data){
        return ResultVo.<T>builder().code(businessEnum.getCode()).msg(businessEnum.getContent()).data(data).build();
    }
}
