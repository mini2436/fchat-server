package xyz.mini2436.fchat.api.api;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;
import xyz.mini2436.fchat.api.model.vo.ApiVo;
import xyz.mini2436.fchat.api.service.UserService;
import xyz.mini2436.fchat.api.utils.PasswordUtil;
import xyz.mini2436.fchat.model.vo.ResultVO;

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

    /**
     * 注册用户接口
     *
     * @param user 用户的注册信息封装
     * @return 返回注册成功的用户
     */
    @PostMapping
    Mono<ResultVO<FchatUser>> adduser(@Validated @RequestBody Mono<FchatUser> user) {
        // 转换请求参数
        return user.map(requestUser -> {
                    requestUser.setPassword(passwordUtil.strEncryption(requestUser.getPassword()));
                    requestUser.setUserId(IdUtil.getSnowflake().nextId());
                    requestUser.setCreatedBy(-1L);
                    requestUser.setCreatedTime(System.currentTimeMillis());
                    requestUser.setRevision(0);
                    requestUser.setDelStatus(0);
                    return requestUser;
                })
                // 业务层处理逻辑
                .flatMap(userService::addUser)
                // 返回前端数据
                .flatMap(this::success);
    }
}
