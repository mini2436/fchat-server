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
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;
import xyz.mini2436.fchat.api.repository.FchatUserRepository;
import xyz.mini2436.fchat.enums.SystemEnum;
import xyz.mini2436.fchat.exceptions.ParameterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private final FchatUserRepository fchatUserRepository;

    /**
     * 认证拦截
     */
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        log.info("当前的请求方式是:{},当前的请求path是:{}", Objects.requireNonNull(request.getMethod()).name(),request.getPath().pathWithinApplication());
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
            Long userId = null;
            try {
                 userId = JWT.of(token).getPayloads().getLong("userId");
            }catch (Exception exception){
                throw new ParameterException("请不要随意使用Token值测试系统");
            }
            return Mono.just(userId).flatMap(id -> reactiveStringRedisTemplate.opsForValue()
                    .get(SystemEnum.REDIS_TOKEN_PATH.getContent()+"TOKEN:"+token)
                    .defaultIfEmpty("")
                    .flatMap(cacheUserInfo -> {
                        // 根据key没有在redis找到当前用户的登录缓存信息,及判定当前用户登录失效
                        if (StrUtil.hasBlank(cacheUserInfo)){
                            return Mono.error(new ParameterException("当前登录Token已失效"));
                        }
                        // 认证通过则通过当前的认证,并放入当前的用户请求信息
                        return fchatUserRepository
                                .findByIdAndDelStatus(id, 0)
                                .defaultIfEmpty(FchatUser.builder().build())
                                .flatMap(queryUser -> webFilterChain.filter(serverWebExchange)
                                        // 认证成功在上下文中放入用户的id
                                        .contextWrite(ctx -> ctx.put("userId", id))
                                        // 认证成功在上下文中放入用户的信息
                                        .contextWrite(ctx -> ctx.put("userInfo", queryUser)));
                    }));

        }
    }
}
