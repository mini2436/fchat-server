package xyz.mini2436.fchat.api.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import xyz.mini2436.fchat.api.model.po.mysql.FchatFriendApply;

/**
 * 好友信息申请表
 *
 * @author mini2436
 * @date 2021-07-30 09:41
 **/
public interface FchatFriendApplyRepository extends ReactiveSortingRepository<FchatFriendApply,Long> {
}
