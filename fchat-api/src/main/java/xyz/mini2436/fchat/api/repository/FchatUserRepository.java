package xyz.mini2436.fchat.api.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;

/**
 * Fchat用户Dao封装操作
 *
 * @author mini2436
 * @date 2021-07-14 16:35
 **/
public interface FchatUserRepository extends ReactiveSortingRepository<FchatUser,Long> {
    /**
     * 添加一个用户
     * @return 返回插入的状态
     */
    @Modifying
    @Query("""
            insert into fchat_user 
                (
                    user_id,username,password,nick_name,mobile_phone,email,avatar,birthday,revision
                    ,created_by,created_time,updated_by,updated_time,del_status
                ) 
            values 
                (
                    :#{#user.userId},:#{#user.username},:#{#user.password},:#{#user.nickName},:#{#user.mobilePhone},:#{#user.email}
                    ,:#{#user.avatar},:#{#user.birthday},:#{#user.revision},:#{#user.createdBy},:#{#user.createdTime},:#{#user.updatedBy}
                    ,:#{#user.updatedTime},:#{#user.delStatus}
                )
            """)
    Mono<Boolean> addOneUser(@Param("user") FchatUser user);

    /**
     * 根据手机号查询用户是否存在
     * @param mobilePhone 手机号
     * @param delStatus 删除状态
     * @return 返回查询的用户数据
     */
    Flux<FchatUser> findByMobilePhoneAndDelStatus(String mobilePhone, Integer delStatus);

    /**
     * 根据邮箱查询用户是否存在
     * @param email 邮件地址
     * @param delStatus 删除状态
     * @return 返回查询的用户数量
     */
    Flux<FchatUser> findByEmailAndDelStatus(String email,Integer delStatus);
}
