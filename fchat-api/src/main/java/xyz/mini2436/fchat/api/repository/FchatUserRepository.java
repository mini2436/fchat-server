package xyz.mini2436.fchat.api.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;

import java.util.Map;

/**
 * Fchat用户Dao封装操作
 *
 * @author mini2436
 * @date 2021-07-14 16:35
 **/
@Repository
public interface FchatUserRepository extends ReactiveSortingRepository<FchatUser,Long> {
    /**
     * 添加一个用户
     * @return 返回插入的状态
     */
    @Modifying
    @Query("""
            insert into fchat_user
            (
                user_id,password,nick_name,mobile_phone,email,avatar,description,created_time,updated_time,del_status
            )
            values
            (
                :#{#user.userId},:#{#user.password},:#{#user.nickName},:#{#user.mobilePhone},:#{#user.email},
                :#{#user.avatar},:#{#user.description},:#{#user.createdTime},:#{#user.updatedTime},:#{#user.delStatus}
            )
            """)
    Mono<Integer> addOneUser(@Param(value = "user") FchatUser user);

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

    /**
     * 根据Id查找用户信息
     * @param userId 用户ID
     * @param delStatus 账号状态
     * @return 返回查询的数据
     */
    Mono<FchatUser> findByUserIdAndDelStatus(Long userId , Integer delStatus);

    /**
     * 测试r2dbc的SQL参数注入查询
     * @param limitMap 分页数据map
     * @return 返回查询的数据
     */
    @Modifying
    @Query("""
        select * from fchat_user limit :#{#limitMap.limit}
    """)
    Flux<FchatUser> testQuery(@Param(value = "limitMap") Map<String,Integer> limitMap);
}
