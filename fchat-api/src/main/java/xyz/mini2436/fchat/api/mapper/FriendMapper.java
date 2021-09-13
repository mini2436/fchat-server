package xyz.mini2436.fchat.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import xyz.mini2436.fchat.api.model.po.mysql.FchatFriendApply;
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;
import xyz.mini2436.fchat.model.dto.ApplyFriendDto;
import xyz.mini2436.fchat.model.vo.FriendDetailedVo;

import java.util.Date;

/**
 * 好友相关数据转换mapper
 *
 * @author mini2436
 * @date 2021-07-29 09:39
 **/
@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FriendMapper {

    /**
     * 将用户信息转化为好友详细信息
     * @param user 用户数据
     * @param createFriendTime 添加好友的时间
     * @return 返回转化后的数据
     */
    @Mapping(source = "createFriendTime", target = "createFriendTime")
    FriendDetailedVo userToFriendDetailedVo(FchatUser user , Date createFriendTime);

    /**
     * 好友添加DTO转化为数据库好友申请记录POJO
     * @param dto 好友申请请求数据封装
     * @return 返回转化后的数据
     */
    FchatFriendApply applyFriendDtoToFchatFriendApply(ApplyFriendDto dto);
}
