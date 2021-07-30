package xyz.mini2436.fchat.api.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.po.mysql.FchatFriend;
import xyz.mini2436.fchat.model.vo.FriendApplyVo;

/**
 * 好友数据表DAO
 *
 * @author mini2436
 * @date 2021-07-28 22:42
 **/
@Repository
public interface FchatFriendRepository extends ReactiveSortingRepository<FchatFriend,Long> {
    /**
     * 查询指定用户的所有好友
     * @param userId 被查询的用户ID
     * @return 返回好友列表数据
     */
    @Modifying
    @Query("""
            select user_id,friend from fchat_friend where user_id = #{userId} or friend = #{userId}
            """)
    Flux<FchatFriend> getAll(@Param("userId") Long userId);

    /**
     * 查询好友的列表数据
     * @param userId 用户ID
     * @param friendUserId　好友ID
     * @return 返回查询出来的数据
     */
    @Modifying
    @Query("""
            select id,user_id,friend,created_time
            from fchat_friend
            where (user_id = #{userId} and friend = #{friendUserId}) or (user_id = #{friendUserId} and friend = #{userId})
            """)
    Mono<FchatFriend> checkFriend(@Param("userId") Long userId, @Param("friendUserId") Long friendUserId);

    /**
     * 获取指定用户的好友申请列表
     * @param userId 需要查询的用户ID
     * @param pageIndex 分页页码
     * @param pageSize 页面条数
     * @return 返回好友列表申请条数
     */
    @Modifying
    @Query("""
            select id,apply_user_id,nick_name as apply_nick_name,apply_content,apply_time
            from fchat.fchat_friend_apply as fffa
            join fchat.fchat_user as ffu on ffu.user_id = fffa.apply_user_id and ffu.del_status = 0
            where fffa.friend_user_id = #{userId}
            order by fffa.apply_time desc
            limit #{pageIndex},#{pageSize}
            """)
    Flux<FriendApplyVo> getApplyList(@Param("userId") Long userId, @Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);

    /**
     * 插入好友数据
     * @param fchatFriend 好友对象数据封装
     * @return 返回插入的条数
     */
    @Modifying
    @Query("""
            insert into fchat_friend 
            (
                id,user_id,friend,created_time
            )
            values 
            (
                :#{#fchatFriend.id},:#{#fchatFriend.user_id}
                ,:#{#fchatFriend.friend},:#{#fchatFriend.created_time}
            )
            """)
    Mono<Integer> insertOne(@Param("fchatFriend") FchatFriend fchatFriend);
}
