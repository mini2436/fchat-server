package xyz.mini2436.fchat.api.api;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.mapper.UserMapper;
import xyz.mini2436.fchat.model.dto.LoginDto;
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;
import xyz.mini2436.fchat.api.model.vo.ApiVo;
import xyz.mini2436.fchat.model.vo.LoginVo;
import xyz.mini2436.fchat.api.service.UserService;
import xyz.mini2436.fchat.api.utils.PasswordUtil;
import xyz.mini2436.fchat.enums.SystemEnum;
import xyz.mini2436.fchat.model.vo.ResultVo;

/**
 * 用户API
 * 提供用户数据的相关操作
 *
 * @author mini2436
 * @date 2021-07-14 16:07
 **/
@RequestMapping("user")
@RestController
@RequiredArgsConstructor
public class UserApi extends ApiVo {
    private final UserService userService;
    private final PasswordUtil passwordUtil;
    private final UserMapper userMapper;

    /**
     * 注册用户接口
     *
     * @param user 用户的注册信息封装
     * @return 返回注册成功的用户
     */
    @PostMapping("register")
    Mono<ResultVo<LoginVo>> adduser(@Validated @RequestBody Mono<FchatUser> user) {
        // 转换请求参数
        return user.map(requestUser -> {
                    requestUser.setPassword(passwordUtil.strEncryption(requestUser.getPassword()));
                    requestUser.setUserId(IdUtil.getSnowflake().nextId());
                    requestUser.setCreatedBy(-1L);
                    requestUser.setCreatedTime(System.currentTimeMillis());
                    requestUser.setRevision(0);
                    requestUser.setDelStatus(0);
                    return requestUser;
                }
        )
                // 业务层处理逻辑
                .flatMap(userService::addUser)
                // 返回前端数据
                .flatMap(this::success);
    }

    /**
     * 系统账户登录
     *
     * @param dto 登录的参数封装
     * @return 返回登录成功的用户信息与凭证
     */
    @PostMapping
    Mono<ResultVo<LoginVo>> login(@Validated @RequestBody Mono<LoginDto> dto) {
        return userService.login(dto).flatMap(this::success);
    }

    /**
     * 更新自己的用户数据
     *
     * @return 返回
     */
    @PutMapping
    Mono<ResultVo<LoginVo>> updateInfo() {
        return null;
    }

    /**
     * 根据当前用户的登录Token获取当前用户的信息
     *
     * @return 从上下文中返回当前的用户数据
     */
    @GetMapping
    Mono<ResultVo<LoginVo>> getUserInfo() {
        return Mono.deferContextual(ctx -> this.success(userMapper.fchatUserToLoginVoAndToken(
                ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_INFO.getContent()),
                ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_TOKEN.getContent())
        )));
    }
}
