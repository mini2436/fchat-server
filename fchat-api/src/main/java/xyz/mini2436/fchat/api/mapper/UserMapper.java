package xyz.mini2436.fchat.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import xyz.mini2436.fchat.api.model.po.mysql.FchatUser;
import xyz.mini2436.fchat.model.dto.UpdateUserDto;
import xyz.mini2436.fchat.model.vo.LoginVo;

/**
 * 与用户相关的领域对象转换
 *
 * @author mini2436
 * @date 2021-07-20 11:08
 **/
@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {
    /**
     * 数据库用户对象转换为登录成功对象像返回
     *
     * @param fchatUser 被转换的数据库用户对象
     * @return 返回转换成功的对象
     */
    LoginVo fchatUserToLoginVo(FchatUser fchatUser);

    /**
     * 数据库用户对象转换为登录成功对象像返回,并添加当前请求的Token
     *
     * @param fchatUser 被转换的数据库用户对象
     * @param token     需要被返回的token
     * @return 返回转换成功的对象
     */
    @Mapping(source = "token", target = "token")
    LoginVo fchatUserToLoginVoAndToken(FchatUser fchatUser, String token);

    /**
     * 将更新的用户数据封装到更新的pojo实体类中
     * @param updateUserDto 更新的用户数据封装
     * @return 返回转换的用户数据
     */
    FchatUser updateUserDtoToUser(UpdateUserDto updateUserDto);
}
