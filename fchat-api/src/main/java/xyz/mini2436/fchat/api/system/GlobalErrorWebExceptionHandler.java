package xyz.mini2436.fchat.api.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.enums.ResultEnum;

import java.util.Map;

/**
 * 全局异常拦截
 *
 * @author mini2436
 * @date 2021-07-16 17:31
 **/
@Slf4j
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {
    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, applicationContext);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), request -> this.renderErrorResponse(request, errorAttributes.getError(request)));
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request, Throwable e) {
        //输出异常堆栈信息
        e.printStackTrace();
        log.error("全局异常拦截:{}", e.getMessage());
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(Map.of("code", ResultEnum.ERROR.getCode(),"msg",e.getMessage(),"data",e.getMessage())));
    }
}
