package xyz.mini2436.fchat.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 好友详细VO数据封装
 *
 * @author mini2436
 * @date 2021-07-28 11:40
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendDetailedVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -7506261044999047958L;
    /**
     * 主键
     */
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 好友添加时间
     */
    private Date createFriendTime;
}
