package xyz.mini2436.fchat.api.system;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 校验用户是否登录的过滤器
 *
 * @author mini2436
 * @date 2021-07-07 13:19
 **/
@Component
@Slf4j
public class LoginFilter implements WebFilter {

    @Value("${fchat.system.tokenName}")
    private String tokenName;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        PathPattern pattern=new PathPatternParser().parse("/system/login");
        ServerHttpRequest request = serverWebExchange.getRequest();
        String userId = "";
        if (!pattern.matches(request.getPath().pathWithinApplication())) {
            List<String> tokens = request.getHeaders().get(tokenName);
            if (ArrayUtil.isEmpty(tokens)) {
                log.error("当前请求没有携带登录认证密钥");
            }else {
                userId = tokens.stream().limit(1).collect(Collectors.toList()).get(0);
            }
        }
        String finalUserId = userId;
        return webFilterChain.filter(serverWebExchange).contextWrite(ctx -> ctx.put("userId", finalUserId));
    }
}
