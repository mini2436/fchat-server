package xyz.mini2436.fchat.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 好友申请信息封装
 *
 * @author mini2436
 * @date 2021-07-28 17:01
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendApplyVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 6565925085654855125L;
    /**
     * 申请信息数据表主键
     */
    private Long id;
    /**
     * 申请的用户ID
     */
    private Long applyUserId;
    /**
     * 申请的用户昵称
     */
    private String applyNickName;
    /**
     * 添加的好友信息
     */
    private String applyContent;
    /**
     * 申请时间
     */
    private Date applyTime;


}
