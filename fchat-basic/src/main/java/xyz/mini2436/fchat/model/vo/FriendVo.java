package xyz.mini2436.fchat.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 好友对象封装
 *
 * @author mini2436
 * @date 2021-07-28 11:26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -6840514707871738249L;
    /**
     * 好友的用户ID
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
}
