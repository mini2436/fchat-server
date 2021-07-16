package xyz.mini2436.fchat.api.system;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.enums.SystemEnum;
import xyz.mini2436.fchat.exceptions.ParameterException;

import java.util.ArrayList;
import java.util.List;

/**
 * 校验用户是否登录的过滤器
 *
 * @author mini2436
 * @date 2021-07-07 13:19
 **/
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginFilter implements WebFilter {
    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    private final FchatYmlConfig fchatYmlConfig;

    /**
     * 认证拦截
     * @param serverWebExchange
     * @param webFilterChain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        log.info("当前的请求方式是:{}",request.getMethod().name());
        log.info("当前的请求path是:{}",request.getPath().pathWithinApplication());
        // 请求方式与请求路由的分割符号
        String separator = ":";
        if (fchatYmlConfig.getNoneCertificationUrl().contains(request.getMethod().name() + separator + request.getPath().pathWithinApplication())){
            log.info("当前请求无需进行用户认证的相关操作");
            return webFilterChain.filter(serverWebExchange);
        }else {
            List<String> tokens = request.getHeaders().get(fchatYmlConfig.getSystem().getTokenName());
            if (tokens == null){
                throw new ParameterException("当前请求未携带Token参数");
            }
            String token = new ArrayList<>(tokens).get(0);
            reactiveStringRedisTemplate.opsForValue()
                    .get(SystemEnum.REDIS_TOKEN_PATH.getContent()+"TOKEN:"+token)
                    .defaultIfEmpty("")
                    .subscribe(cacheUserInfo -> {
                        if (StrUtil.hasBlank(cacheUserInfo)){
                            throw new ParameterException("当前登录Token已失效");
                        }
                    });
            Object userId = JWT.of(token).getPayload("userId");
            return webFilterChain.filter(serverWebExchange).contextWrite(ctx -> ctx.put("userId", userId));
        }
    }
}
