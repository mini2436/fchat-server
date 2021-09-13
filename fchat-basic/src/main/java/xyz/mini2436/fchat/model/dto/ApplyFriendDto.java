package xyz.mini2436.fchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * FriendApplyDto
 *
 * @author mini2436
 * @date 2021-08-06 09:45
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyFriendDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -6608244045294425559L;

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
}
