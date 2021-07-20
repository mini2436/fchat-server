package xyz.mini2436.fchat.api.model.vo;

import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.enums.ResultEnum;
import xyz.mini2436.fchat.model.vo.ResultVo;

/**
 * API接口的通用返回
 *
 * @author mini2436
 * @date 2021-07-07 10:29
 **/
public class ApiVo {
    public Mono<ResultVo<String>> success(){
        return Mono.just(ResultVo.<String>builder().code(ResultEnum.SUCCESS.getCode()).msg(ResultEnum.SUCCESS.getContent()).data(ResultEnum.SUCCESS.getContent()).build());
    }
    public Mono<ResultVo<String>> success(String msg){
        return Mono.just(ResultVo.<String>builder().code(ResultEnum.SUCCESS.getCode()).msg(msg).data(msg).build());
    }
    public <T> Mono<ResultVo<T>> success(String msg , T data){
        return Mono.just(ResultVo.<T>builder().code(ResultEnum.SUCCESS.getCode()).msg(msg).data(data).build());
    }
    public <T> Mono<ResultVo<T>> success(Integer code , String msg , T data){
        return Mono.just(ResultVo.<T>builder().code(code).msg(msg).data(data).build());
    }
    public <T> Mono<ResultVo<T>> success(T data){
        return Mono.just(ResultVo.<T>builder().code(ResultEnum.SUCCESS.getCode()).msg(ResultEnum.SUCCESS.getContent()).data(data).build());
    }
    public Mono<ResultVo<String>> fail(){
        return Mono.just(ResultVo.<String>builder().code(ResultEnum.FAIL.getCode()).msg(ResultEnum.FAIL.getContent()).data(ResultEnum.FAIL.getContent()).build());
    }
    public Mono<ResultVo<String>> fail(String msg){
        return Mono.just(ResultVo.<String>builder().code(ResultEnum.FAIL.getCode()).msg(msg).data(msg).build());
    }
    public <T> Mono<ResultVo<T>> fail(String msg , T data){
        return Mono.just(ResultVo.<T>builder().code(ResultEnum.FAIL.getCode()).msg(msg).data(data).build());
    }
    public <T> Mono<ResultVo<T>> fail(Integer code , String msg , T data){
        return Mono.just(ResultVo.<T>builder().code(code).msg(msg).data(data).build());
    }
}
