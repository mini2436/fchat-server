package xyz.mini2436.fchat.api.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;
import xyz.mini2436.fchat.api.utils.JsonUtil;
import xyz.mini2436.fchat.enums.BusinessEnum;
import xyz.mini2436.fchat.model.vo.ResultVo;

/**
 * 全局异常拦截
 *
 * @author mini2436
 * @date 2021-07-16 17:31
 **/
@Slf4j
@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler  implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        ResultVo<String> resultVO = ResultVo.<String>builder().code(BusinessEnum.REQUEST_ERROR.getCode()).msg(BusinessEnum.REQUEST_ERROR.getContent()).data(throwable.getMessage()).build();
        DataBuffer buff = response.bufferFactory()
                .allocateBuffer().write(JsonUtil.objToJson(resultVO).getBytes());
        //基于流形式
        response.getHeaders().setContentType(MediaType.APPLICATION_NDJSON);
        return response.writeAndFlushWith(Mono.just(ByteBufMono.just(buff)));
    }
}
