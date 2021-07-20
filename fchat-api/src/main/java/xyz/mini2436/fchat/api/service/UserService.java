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
import xyz.mini2436.fchat.model.dto.LoginDto;
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;
import xyz.mini2436.fchat.model.vo.LoginVo;
import xyz.mini2436.fchat.api.repository.FchatUserRepository;
import xyz.mini2436.fchat.api.system.FchatYmlConfig;
import xyz.mini2436.fchat.api.utils.JsonUtil;
import xyz.mini2436.fchat.api.utils.PasswordUtil;
import xyz.mini2436.fchat.enums.SystemEnum;
import xyz.mini2436.fchat.exceptions.DatabaseException;
import xyz.mini2436.fchat.exceptions.ParameterException;

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
                .findByMobilePhoneAndDelStatus(user.getMobilePhone(),0)
                .count().map(count -> count == 0)
                .flatMap(queryStatus -> queryStatus ? Mono.just(fchatUserRepository) : Mono.error(new DatabaseException("当前手机号码已被注册")))
                // 校验当前邮箱是否已经被注册
                .flatMap(repository -> repository.findByEmailAndDelStatus(user.getEmail(),0).count().map(count -> count == 0))
                .flatMap(queryStatus ->queryStatus ? Mono.just(fchatUserRepository) : Mono.error(new DatabaseException("当前邮箱已被注册")))
                // 保存当前注册数据
                .flatMap(repository -> repository.addOneUser(user))
                .flatMap(insertCount -> insertCount  ? Mono.just(usermapper.fchatUserToLoginVo(user)) : Mono.error(new DatabaseException("当前用户注册失败")));
    }

    /**
     * 系统账户登录
     * @param dto 登录参数封装
     * @return 返回登录后的信息
     */
    public Mono<LoginVo> login(Mono<LoginDto> dto) {
        return dto.flatMap(loginDto -> {
            // 校验当前的登录用户是否处在
            Flux<FchatUser> queryUser;
            // 根据登录的手机号或者邮箱分别查询用户信息
            if (StrUtil.hasBlank(loginDto.getMobilePhone())){
                queryUser = fchatUserRepository.findByEmailAndDelStatus(loginDto.getEmail(), 0);
            }else {
                queryUser = fchatUserRepository.findByMobilePhoneAndDelStatus(loginDto.getMobilePhone(), 0);
            }
            // 判定用户信息是否存在
            Mono<Boolean> isExist = queryUser.count().map(count -> count == 0);
            Mono<FchatUser> user = isExist.flatMap(existStatus -> {
                if (existStatus) {
                    return Mono.error(new DatabaseException("当前用户信息未注册"));
                } else {
                    return queryUser.next();
                }
            });
            // 校验当前的登录用户用户密码是否正确
            return user.flatMap(dataUser ->{
                // 对比当前输入的密码与系统加密的密码是否一致
                Mono<Boolean> passwordCheck = Mono.just(passwordUtil.strEncryption(loginDto.getPassword()).equals(dataUser.getPassword()));
                return passwordCheck.flatMap(checkStatus ->{
                    if (checkStatus){
                        return user.map(userInfo ->{
                            Duration duration = Duration.ofMillis(fchatYmlConfig.getSystem().getTokenExpiredTime());
                            // 将当前的用户ID与登录设备放入token进行保存
                            String sign = JWT.create()
                                    .setPayload("userId",userInfo.getUserId())
                                    .setPayload("equipment",loginDto.getEquipment())
                                    .setPayload("createTime",System.currentTimeMillis())
                                    .setKey(fchatYmlConfig.getSystem().getTokenKey().getBytes(StandardCharsets.UTF_8))
                                    .sign();
                            // 构造方法返回对象
                            LoginVo responseVo = LoginVo.builder().avatar(userInfo.getAvatar()).email(userInfo.getEmail()).birthday(userInfo.getBirthday())
                                    .mobilePhone(userInfo.getMobilePhone()).nickName(userInfo.getNickName()).username(userInfo.getUsername())
                                    .userId(userInfo.getUserId()).token(sign).build();
                            // 处理保存的参数
                            Map<String, String> redisloginSaveMap;
                            // 根据用户的登录设备缓存当前的token
                            redisloginSaveMap = switch (loginDto.getEquipment()){
                                case "WEB" ->  Map.of(SystemEnum.WEB_LOGIN_EQUIPMENT.getContent(), sign);
                                case "DESKTOP" -> Map.of(SystemEnum.DESKTOP_LOGIN_EQUIPMENT.getContent(), sign);
                                case "MOBILE" -> Map.of(SystemEnum.MOBILE_LOGIN_EQUIPMENT.getContent(),sign);
                                default -> {throw new ParameterException("当前登录设备不在支持范围内,用户id:"+userInfo.getUserId()+",用户姓名:"+userInfo.getUsername()+",登录设备:"+loginDto.getEquipment());}
                                };
                            // 剔除原本在本设备类型在线的设备
                            Flux<Map.Entry<Object, Object>> entries = reactiveStringRedisTemplate.opsForHash().entries(SystemEnum.REDIS_LOGIN_USERINFO_PATH.getContent()+ userInfo.getUserId());
                            entries.subscribe(entry ->{
                                if (loginDto.getEquipment().equals(entry.getKey().toString())){
                                    log.warn("原设备已被下线,原有设备类型:{},原有设备在线Token:{}",loginDto.getEquipment(),entry.getValue());
                                    reactiveStringRedisTemplate.delete(SystemEnum.REDIS_LOGIN_TOKEN_PATH.getContent()+entry.getValue().toString()).subscribe();
                                }
                            });
                            // 更新用户的token数据
                            reactiveStringRedisTemplate.opsForHash().putAll(SystemEnum.REDIS_LOGIN_USERINFO_PATH.getContent()+userInfo.getUserId(),redisloginSaveMap).subscribe();
                            // 设置新token的时效
                            reactiveStringRedisTemplate.opsForValue().set(SystemEnum.REDIS_LOGIN_TOKEN_PATH.getContent()+sign, JsonUtil.objToJson(dataUser),duration).subscribe();
                            return responseVo;
                        });
                    }{
                       return Mono.error(new DatabaseException("登录密码错误"));
                    }
                });
            });
        });
    }
}