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
 * 好友数据表封装
 *
 * @author mini2436
 * @date 2021-07-28 16:43
 **/
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FchatFriend implements Serializable {
    @Serial
    private static final long serialVersionUID = 2092355870225783756L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 好友用户ID
     */
    private Long friend;

    /**
     * 创建时间
     */
    private Long createdTime;


}
