package xyz.mini2436.fchat.api.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.po.mysql.FchatFriendApply;
/**
 * 好友信息申请表
 *
 * @author mini2436
 * @date 2021-07-30 09:41
 **/
@Repository
public interface FchatFriendApplyRepository extends ReactiveCrudRepository<FchatFriendApply,Long> {

    /**
     * 查询时候存在当前用户的申请信息
     * @param applyUserId 申请用户
     * @param friendId 被申请用户
     * @param applyStatus 申请状态
     * @return 查找的数据
     */
    Mono<FchatFriendApply> findByApplyUserIdAndFriendUserIdAndApplyStatus(Long applyUserId, Long friendId,Integer applyStatus);
}
