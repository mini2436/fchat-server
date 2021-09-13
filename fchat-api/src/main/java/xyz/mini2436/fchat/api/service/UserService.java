package xyz.mini2436.fchat.api.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.mapper.UserMapper;
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;
import xyz.mini2436.fchat.api.repository.FchatUserRepository;
import xyz.mini2436.fchat.api.system.FchatYmlConfig;
import xyz.mini2436.fchat.api.utils.JsonUtil;
import xyz.mini2436.fchat.api.utils.PasswordUtil;
import xyz.mini2436.fchat.constant.SystemConstant;
import xyz.mini2436.fchat.enums.SystemEnum;
import xyz.mini2436.fchat.exceptions.DatabaseException;
import xyz.mini2436.fchat.exceptions.ParameterException;
import xyz.mini2436.fchat.model.dto.LoginDto;
import xyz.mini2436.fchat.model.vo.LoginVo;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

/**
 * 用户业务处理层
 *
 * @author mini2436
 * @date 2021-07-14 16:36
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final FchatUserRepository fchatUserRepository;
    private final PasswordUtil passwordUtil;
    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    private final FchatYmlConfig fchatYmlConfig;
    private final UserMapper usermapper;


    /**
     * 系统用户注册
     *
     * @param user 注册的用户数据
     * @return 返回注册成功的用户数据
     */
    public Mono<LoginVo> addUser(FchatUser user) {
        return fchatUserRepository
                // 校验当前手机号是否已经注册
                .findByMobilePhoneAndDelStatus(user.getMobilePhone(), SystemConstant.DataBaseDelStatus.NOT_DELETE)
                .count().map(count -> count == 0)
                .flatMap(queryStatus -> queryStatus ? Mono.just(fchatUserRepository) : Mono.error(new DatabaseException("当前手机号码已被注册")))
                // 校验当前邮箱是否已经被注册
                .flatMap(repository -> repository.findByEmailAndDelStatus(user.getEmail(), SystemConstant.DataBaseDelStatus.NOT_DELETE).count().map(count -> count == 0))
                .flatMap(queryStatus -> queryStatus ? Mono.just(fchatUserRepository) : Mono.error(new DatabaseException("当前邮箱已被注册")))
                // 保存当前注册数据
                .flatMap(repository -> repository.addOneUser(user))
                .flatMap(insertCount -> insertCount > 0 ? Mono.just(usermapper.fchatUserToLoginVo(user)) : Mono.error(new DatabaseException("当前用户注册失败")));
    }

    /**
     * 系统账户登录
     *
     * @param dto 登录参数封装
     * @return 返回登录后的信息
     */
    public Mono<LoginVo> login(Mono<LoginDto> dto) {
        // 校验当前的登录用户是否存在
        return dto.flatMap(loginDto -> {
            // 根据登录的手机号或者邮箱分别查询用户信息
            Flux<FchatUser> queryUser = StrUtil.hasBlank(loginDto.getMobilePhone())
                    ? fchatUserRepository.findByEmailAndDelStatus(loginDto.getEmail(), SystemConstant.DataBaseDelStatus.NOT_DELETE)
                    : fchatUserRepository.findByMobilePhoneAndDelStatus(loginDto.getMobilePhone(), SystemConstant.DataBaseDelStatus.NOT_DELETE);
            // 判定用户信息是否存在 不存在则返回失败异常 有则将当前数据库的用户信息传递到下一流程处理
            return queryUser.count().map(count -> count == 0).flatMap(existStatus -> existStatus ? Mono.error(new DatabaseException("当前用户信息未注册")) : queryUser.next());
        })


                // 对比当前输入的密码与系统加密的密码是否一致 密码错误则返回失败异常 有则将当前数据库的用户信息传递到下一处理流程
                .flatMap(dataUser -> dto.map(loginDto -> passwordUtil.strEncryption(loginDto.getPassword()).equals(dataUser.getPassword()))
                        .flatMap(checkStatus -> checkStatus ? Mono.just(dataUser) : Mono.error(new DatabaseException("登录密码错误"))))


                // 生成用户的Token相关数据,并打包当前所有数据到下一处理流程
                .flatMap(dataUser -> {
                    Duration duration = Duration.ofMillis(fchatYmlConfig.getSystem().getTokenExpiredTime());
                    Mono<String> signMono = dto.map(loginDto -> JWT.create()
                            .setPayload("userId", dataUser.getUserId())
                            .setPayload("equipment", loginDto.getEquipment())
                            .setPayload("createTime", System.currentTimeMillis())
                            .setKey(fchatYmlConfig.getSystem().getTokenKey().getBytes(StandardCharsets.UTF_8))
                            .sign());
                    return Mono.zip(dto, Mono.just(dataUser), signMono, Mono.just(duration));
                })


                // 处理本次登录缓存相关问题处理
                .flatMap(zipData -> {
                    Map<String, String> redisloginSaveMap;
                    // 根据用户的登录设备缓存当前的token
                    redisloginSaveMap = switch (zipData.getT1().getEquipment()) {
                        case "WEB" -> Map.of(SystemEnum.WEB_LOGIN_EQUIPMENT.getContent(), zipData.getT3());
                        case "DESKTOP" -> Map.of(SystemEnum.DESKTOP_LOGIN_EQUIPMENT.getContent(), zipData.getT3());
                        case "MOBILE" -> Map.of(SystemEnum.MOBILE_LOGIN_EQUIPMENT.getContent(), zipData.getT3());
                        default -> {
                            String errorTemplate = "当前登录设备不在支持范围内,用户id:{},用户昵称:{},登录设备:{}";
                            throw new ParameterException(StrUtil.format(errorTemplate, zipData.getT2().getUserId(), zipData.getT2().getNickName(), zipData.getT1().getEquipment()));
                        }
                    };
                    // 剔除原本在Redis中属于本设备类型在线的设备
                    Flux<Map.Entry<Object, Object>> entries = reactiveStringRedisTemplate.opsForHash().entries(SystemEnum.REDIS_LOGIN_USERINFO_PATH.getContent() + zipData.getT2().getUserId());
                    entries.subscribe(entry -> {
                        if (zipData.getT1().getEquipment().equals(entry.getKey().toString())) {
                            reactiveStringRedisTemplate
                                    .delete(SystemEnum.REDIS_LOGIN_TOKEN_PATH.getContent() + entry.getValue().toString())
                                    .subscribe(deleteCount -> log.warn("原设备已被下线,原有设备类型:{},原有设备在线Token:{}", zipData.getT1().getEquipment(), entry.getValue()));
                        }
                    });
                    // 更新用户的token数据
                    reactiveStringRedisTemplate.opsForHash()
                            .putAll(SystemEnum.REDIS_LOGIN_USERINFO_PATH.getContent() + zipData.getT2().getUserId(), redisloginSaveMap)
                            .subscribe(updateStatus ->{
                                if (!updateStatus){
                                    throw new DatabaseException("用户Token缓存更新失败");
                                }
                            });
                    // 设置新token的时效
                    reactiveStringRedisTemplate.opsForValue().set(SystemEnum.REDIS_LOGIN_TOKEN_PATH.getContent() + zipData.getT3(), JsonUtil.objToJson(zipData.getT2()), zipData.getT4())
                            .subscribe(updateStatus ->{
                                if (!updateStatus){
                                    throw new DatabaseException("本次登录Token缓存保存失败");
                                }
                            });
                    return Mono.zip(Mono.just(zipData.getT2()), Mono.just(zipData.getT3()));
                })


                // 构建方法的处理返回对象
                .map(zipData -> {
                    LoginVo loginVo = usermapper.fchatUserToLoginVo(zipData.getT1());
                    loginVo.setToken(zipData.getT2());
                    return loginVo;
                });
    }

    /**
     * 更新用户数据
     *
     * @param user 保存的用户数据
     * @return 返回更新了后的数据
     */
    public Mono<LoginVo> updateUserInfo(FchatUser user) {
        return Mono.deferContextual(ctx -> Mono.just((Long) ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_ID.getContent())))
                .flatMap(userId -> {
                    user.setUserId(userId);
                    // 将明文密码转换为密文密码
                    user.setPassword(passwordUtil.strEncryption(user.getPassword()));
                    // 数据库保存操作
                    return fchatUserRepository.save(user);
                }).flatMap(resultUser -> Mono.deferContextual(ctx ->
                        Mono.just(usermapper.fchatUserToLoginVoAndToken(resultUser, ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_TOKEN.getContent())))
                ));

    }

    /**
     * 注销当前用户登录状态
     *
     * @return 返回当前用户的注销信息
     */
    public Mono<String> logout() {
        return Mono.deferContextual(ctx -> {
            String token = ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_TOKEN.getContent());
            return reactiveStringRedisTemplate.delete(SystemEnum.REDIS_LOGIN_TOKEN_PATH + token).map(deleteCount -> "数据删除成功");
        });
    }
}