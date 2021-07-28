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
 * Fchat用户表实体
 *
 * @author mini2436
 * @date 2021-07-14 16:28
 **/
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FchatUser implements Serializable {

    @Serial
    private static final long serialVersionUID = -879197555055416437L;

    /**
     * 主键
     */
    @Id
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String mobilePhone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 个人描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Long createdTime;
    /**
     * 更新时间
     */
    private Long updatedTime;
    /**
     * 逻辑删除
     */
    private Integer delStatus;

}
