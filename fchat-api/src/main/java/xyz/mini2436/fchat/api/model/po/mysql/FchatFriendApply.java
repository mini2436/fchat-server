package xyz.mini2436.fchat.api.model.po.mysql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serial;
import java.io.Serializable;

/**
 * 好友申请记录表
 *
 * @author mini2436
 * @date 2021-07-28 16:48
 **/
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FchatFriendApply implements Serializable {
    @Serial
    private static final long serialVersionUID = 8456935867709787529L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 申请的用户ID
     */
    private Long applyUserId;

    /**
     * 被添加为好友的用户ID
     */
    private Long friendUserId;

    /**
     * 申请添加信息
     */
    private String applyContent;

    /**
     * 申请时间
     */
    private Long applyTime;

    /**
     * 申请状态
     */
    private Integer applyStatus;

    /**
     * 申请信息删除状态
     */
    private Integer delStatus;
}
