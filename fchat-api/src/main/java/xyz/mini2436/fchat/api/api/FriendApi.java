package xyz.mini2436.fchat.api.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.vo.ApiVo;
import xyz.mini2436.fchat.api.service.FriendService;
import xyz.mini2436.fchat.enums.SystemEnum;
import xyz.mini2436.fchat.model.dto.ApplyFriendDto;
import xyz.mini2436.fchat.model.vo.FriendApplyVo;
import xyz.mini2436.fchat.model.vo.FriendDetailedVo;
import xyz.mini2436.fchat.model.vo.FriendVo;
import xyz.mini2436.fchat.model.vo.ResultVo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 好友相关API
 *
 * @author mini2436
 * @date 2021-07-28 11:12
 **/
@RequestMapping("friend")
@RestController
@RequiredArgsConstructor
public class FriendApi extends ApiVo {
    private final FriendService friendService;

    /**
     * 获取当前用户的所有好友列表数据
     *
     * @return 返回当前用户的列表数据
     */
    @GetMapping
    Mono<ResultVo<List<FriendVo>>> getAll() {
        return Mono.deferContextual(ctx -> friendService.getAll(ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_ID.getContent())))
                .flatMap(this::success);
    }

    /**
     * 获取指定好友Id的详细信息数据
     *
     * @param friendUserId 需要查询的好友ID
     * @return 返回好友详细数据
     */
    @GetMapping("/{friendUserId}")
    Mono<ResultVo<FriendDetailedVo>> getByUserId(@Validated @PathVariable @NotNull Long friendUserId) {
        return Mono.deferContextual(ctx -> friendService.getByUserId(ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_ID.getContent()), friendUserId))
                .flatMap(this::success);
    }

    /**
     * 删除一个自己的好友
     *
     * @param friendUserId 需要删除的用户ID
     * @return 返回删除后的对象
     */
    @DeleteMapping("/{friendUserId}")
    Mono<ResultVo<Boolean>> deleteByUserId(@Validated @PathVariable @NotNull Long friendUserId) {
        return Mono.deferContextual(ctx -> friendService.deleteByUserId(ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_ID.getContent()), friendUserId))
                .flatMap(this::success);
    }

    /**
     * 获取自己的好友申请列表
     *
     * @param pageIndex 页码
     * @param pageSize  页面条数
     * @return 返回查询的列表数据
     */
    @GetMapping("friendApplyList/{pageIndex}/{pageSize}")
    Mono<ResultVo<List<FriendApplyVo>>> getFriendApplyList(@Validated @PathVariable @Min(value = 0, message = "页码参数不正确,请不要测试该系统") Integer pageIndex,
                                                           @Validated @PathVariable @Min(value = 0, message = "分页条数不正确,请不要测试该系统") Integer pageSize) {
        return Mono.deferContextual(ctx -> friendService
                .getFriendApplyList(ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_ID
                        .getContent()), pageIndex, pageSize))
                .flatMap(this::success);
    }

    /**
     * 通过指定的好友申请
     *
     * @param id 好友申请数据的ID
     * @return 返回通过状态
     */
    @PutMapping("pass/{id}")
    Mono<ResultVo<Boolean>> passFriendApply(@Validated @PathVariable @NotNull Long id) {
        return Mono.deferContextual(ctx -> friendService.passFriendApply(ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_ID.getContent()), id))
                .flatMap(this::success);
    }

    /**
     * 向指定用户发送好友申请
     * @param dto 请求参数封装
     * @param friendId 好友ID
     * @return 返回发送成功的状态
     */
    @PostMapping("apply/{friendId}")
    Mono<ResultVo<Boolean>> apply(@Validated ApplyFriendDto dto , @PathVariable Long friendId) {
        return Mono.deferContextual(ctx -> {
            dto.setApplyUserId(ctx.get(SystemEnum.WEBFLUX_CONTEXT_DATA_USER_ID.getContent()));
            dto.setFriendUserId(friendId);
            return friendService.apply(dto);
        }).flatMap(this::success);
    }


}
