package xyz.mini2436.fchat.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;
import xyz.mini2436.fchat.api.repository.FchatUserRepository;
import xyz.mini2436.fchat.exceptions.DatabaseException;

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

    /**
     * 系统用户注册
     *
     * @param user 注册的用户数据
     * @return 返回注册成功的用户数据
     */
    public Mono<FchatUser> addUser(FchatUser user) {
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
                .flatMap(insertCount -> insertCount  ? Mono.just(user) : Mono.error(new DatabaseException("当前用户注册失败")));
    }
}