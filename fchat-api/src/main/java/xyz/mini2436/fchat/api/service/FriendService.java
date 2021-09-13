package xyz.mini2436.fchat.api.service;

import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.mapper.FriendMapper;
import xyz.mini2436.fchat.api.model.po.mysql.FchatFriend;
import xyz.mini2436.fchat.api.model.po.mysql.FchatFriendApply;
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;
import xyz.mini2436.fchat.api.repository.FchatFriendApplyRepository;
import xyz.mini2436.fchat.api.repository.FchatFriendRepository;
import xyz.mini2436.fchat.api.repository.FchatUserRepository;
import xyz.mini2436.fchat.constant.SystemConstant;
import xyz.mini2436.fchat.exceptions.DatabaseException;
import xyz.mini2436.fchat.exceptions.MessageException;
import xyz.mini2436.fchat.model.dto.ApplyFriendDto;
import xyz.mini2436.fchat.model.vo.FriendApplyVo;
import xyz.mini2436.fchat.model.vo.FriendDetailedVo;
import xyz.mini2436.fchat.model.vo.FriendVo;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private final FchatFriendRepository fchatFriendRepository;
    private final FchatUserRepository fchatUserRepository;
    private final FriendMapper friendMapper;
    private final FchatFriendApplyRepository fchatFriendApplyRepository;

    /**
     * 查询当前用户的所有好友列表数据
     *
     * @param userId 查询的用户ID
     * @return 返回查询到的列表数据
     */
    public Mono<List<FriendVo>> getAll(Long userId) {
        return fchatFriendRepository
                // 查询指定用户的所有好友记录
                .getAll(userId).defaultIfEmpty(new FchatFriend()).map(fchatFriend -> {
                    if (Objects.isNull(fchatFriend.getUserId())) {
                        throw new MessageException("系统中没有任何好友记录");
                    }
                    return fchatFriend;
                })
                // 转化好友记录为好友列表数据
                .collectList().flatMap(friendVoList -> {
                    // 排除自身数据UserId数据
                    Set<Long> userIds = Stream.concat(
                            friendVoList.stream().map(FchatFriend::getFriend),
                            friendVoList.stream().map(FchatFriend::getUserId)
                    ).collect(Collectors.toSet());
                    userIds.remove(userId);
                    // 将好友列表的UserID转化为好友列表的简介信息
                    return fchatUserRepository.findUserIdInList(userIds).collectList();
                });
    }

    /**
     * 查询指定好友的详细信息
     *
     * @param userId       发起查询的用户ID
     * @param friendUserId 被查询的好友ID
     * @return 返回好友的详细信息
     */
    public Mono<FriendDetailedVo> getByUserId(Long userId, Long friendUserId) {
        // 检验是否拥有查看该好友详细信息的合法性
        return fchatFriendRepository
                // 查看好友是否存在于好友关系表中
                .checkFriend(userId, friendUserId)
                // 没有数据给一个没有数据的空对象,防止因为空对象导致整个Mono流终止处理
                .defaultIfEmpty(FchatFriend.builder().build())
                .map(fchatFriend -> {
                    // 没有权限查看该用户数据返回异常信息
                    if (Objects.isNull(fchatFriend.getUserId())) {
                        throw new DatabaseException("您无权限查看该用户的数据信息");
                    }
                    // 有权限查看则返回当前好友的添加时间继续下一步操作
                    return new Date(fchatFriend.getCreatedTime());
                })
                .flatMap(friendCreatedTime -> {
                    // 转化为好友详细数据
                    Mono<FchatUser> userById = fchatUserRepository.findById(friendUserId);
                    return userById.map(user -> friendMapper.userToFriendDetailedVo(user, friendCreatedTime));
                });
    }

    /**
     * 删除指定好友
     *
     * @param userId       发起删除的用户ID
     * @param friendUserId 被删除的好友ID
     * @return 返回被删除的用户数据
     */
    public Mono<Boolean> deleteByUserId(Long userId, Long friendUserId) {
        return fchatFriendRepository.checkFriend(userId, friendUserId)
                // 没有数据给一个没有数据的空对象,防止因为空对象导致整个Mono流终止处理
                .defaultIfEmpty(FchatFriend.builder().build())
                .map(fchatFriend -> {
                    // 没有权限查看该用户数据返回异常信息
                    if (Objects.isNull(fchatFriend.getUserId())) {
                        throw new MessageException("该好友目前与你不是好友关系无需删除");
                    }
                    fchatFriendRepository.deleteById(fchatFriend.getId());
                    return Boolean.TRUE;
                });
    }

    /**
     * 查询好友申请列表
     *
     * @param userId    申请的用户ID
     * @param pageIndex 分页页码
     * @param pageSize  分页页面条数
     * @return 返回申请列表数据
     */
    public Mono<List<FriendApplyVo>> getFriendApplyList(Long userId, Integer pageIndex, Integer pageSize) {
        return fchatFriendRepository
                .getApplyList(userId, pageIndex - 1, (pageIndex - 1) * pageSize)
                .collectList();

    }

    /**
     * 通过好友申请
     *
     * @param userId 审核人
     * @param id     通过的数据ID
     * @return 返回通过状态
     */
    @Transactional(rollbackFor = Exception.class)
    public Mono<Boolean> passFriendApply(Long userId, Long id) {
        return fchatFriendApplyRepository
                // 校验申请信息的合法性
                .findById(id)
                .defaultIfEmpty(FchatFriendApply.builder().build())
                .flatMap(fchatFriendApply -> {
                    if (Objects.isNull(fchatFriendApply.getFriendUserId())
                            || fchatFriendApply.getFriendUserId().equals(userId)) {
                        return Mono.error(new MessageException("您当前无权限操作该申请信息"));
                    }
                    return Mono.just(fchatFriendApply);
                }).flatMap(fchatFriendApply -> {
                    // 写入好友数据
                    FchatFriend fchatFriend = FchatFriend.builder()
                            .id(IdUtil.getSnowflake().nextId())
                            .friend(fchatFriendApply.getFriendUserId())
                            .userId(fchatFriendApply.getApplyUserId())
                            .createdTime(System.currentTimeMillis())
                            .build();
                    fchatFriendRepository.insertOne(fchatFriend).subscribe(rows -> {
                        if (rows < 1) {
                            throw new DatabaseException("好友数据添加失败");
                        }
                    });
                    return Mono.just(fchatFriendApply);
                }).flatMap(fchatFriendApply -> {
                    // 更新审核状态
                    fchatFriendApply.setApplyStatus(SystemConstant.FriendApplyStatus.PASS);
                    return fchatFriendApplyRepository
                            .save(fchatFriendApply)
                            .map(fchatFriendApplySave -> Objects.isNull(fchatFriendApplySave) ? Boolean.FALSE : Boolean.TRUE);
                });
    }

    /**
     * 添加好友申请信息
     *
     * @param dto 请求参数封装
     * @return 返回申请状态
     */
    public Mono<Boolean> apply(ApplyFriendDto dto) {
        // 校验申请用户是否已经是好友了
        return fchatFriendRepository.checkFriend(dto.getApplyUserId(), dto.getFriendUserId()).defaultIfEmpty(FchatFriend.builder().build()).map(fchatFriend -> {
                    if (Objects.isNull(fchatFriend.getId())) {
                        Mono.error(new MessageException("申请用户已是你的好友无需再次申请"));
                    }
                    return Boolean.TRUE;
                }
                // 校验是否已经存在好友申请信息
        ).flatMap(nextBool -> fchatFriendApplyRepository.findByApplyUserIdAndFriendUserIdAndApplyStatus(dto.getApplyUserId(), dto.getFriendUserId(), SystemConstant.FriendApplyStatus.WAIT)
                .defaultIfEmpty(FchatFriendApply.builder().build())
                .flatMap(fchatFriendApply -> {
                            if (!Objects.isNull(fchatFriendApply.getId())) {
                                Mono.error(new MessageException("系统中已存在当前信息请不要重复申请"));
                            }
                            return Mono.just(true);
                        }
                ).flatMap(bool -> fchatFriendApplyRepository.findByApplyUserIdAndFriendUserIdAndApplyStatus(dto.getFriendUserId(), dto.getApplyUserId(), SystemConstant.FriendApplyStatus.WAIT)
                                .defaultIfEmpty(FchatFriendApply.builder().build())
                                .flatMap(fchatFriendApply -> {
                                    if (!Objects.isNull(fchatFriendApply.getId())) {
                                        Mono.error(new MessageException("系统中已存在当前信息请不要重复申请"));
                                    }
                                    return Mono.just(true);
                                })
                        // 保存好友申请信息
                ).flatMap(bool -> {
                    FchatFriendApply fchatFriendApply = friendMapper.applyFriendDtoToFchatFriendApply(dto);
                    fchatFriendApply.setApplyContent(dto.getApplyContent());
                    fchatFriendApply.setApplyTime(System.currentTimeMillis());
                    fchatFriendApply.setApplyStatus(SystemConstant.FriendApplyStatus.WAIT);
                    fchatFriendApply.setDelStatus(SystemConstant.DataBaseDelStatus.NOT_DELETE);
                    return fchatFriendApplyRepository.save(fchatFriendApply).defaultIfEmpty(FchatFriendApply.builder().build());})
                // 返回处理状态
                .map(fchatFriendApply -> Objects.isNull(fchatFriendApply.getId()))
        );
    }
}
