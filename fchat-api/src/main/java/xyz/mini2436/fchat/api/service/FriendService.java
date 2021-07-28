package xyz.mini2436.fchat.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.model.vo.FriendApplyVo;
import xyz.mini2436.fchat.model.vo.FriendDetailedVo;
import xyz.mini2436.fchat.model.vo.FriendVo;

import java.util.List;

/**
 * FriendService
 *
 * @author mini2436
 * @date 2021-07-28 11:13
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class FriendService {
    /**
     * 查询当前用户的所有好友列表数据
     * @param userId 查询的用户ID
     * @return 返回查询到的列表数据
     */
    public Mono<List<FriendVo>> getAll(Long userId) {
        return null;
    }

    /**
     * 查询指定好友的详细信息
     * @param userId 发起查询的用户ID
     * @param friendUserId 被查询的好友ID
     * @return 返回好友的详细信息
     */
    public Mono<FriendDetailedVo> getByUserId(Long userId, Long friendUserId) {
        return null;
    }

    /**
     * 删除指定好友
     * @param userId 发起删除的用户ID
     * @param friendUserId 被删除的好友ID
     * @return 返回被删除的用户数据
     */
    public Mono<FriendDetailedVo> deleteByUserId(Long userId, Long friendUserId) {
        return null;
    }

    /**
     * 查询好友申请列表
     * @param userId 申请的用户ID
     * @param pageIndex 分页页码
     * @param pageSize 分页页面条数
     * @return 返回申请列表数据
     */
    public Mono<List<FriendApplyVo>> getFriendApplyList(Long userId, Integer pageIndex, Integer pageSize) {
        return null;
    }

    /**
     * 通过好友申请
     * @param userId 审核人
     * @param id 通过的数据ID
     * @return 返回通过状态
     */
    public Mono<Boolean> passFriendApply(Long userId, Long id) {
        return null;
    }
}
