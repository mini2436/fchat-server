package xyz.mini2436.fchat.api.model.po.mysql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

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
    private static final long serialVersionUID = -879197555055416437L;

    /**
     * 主键
     */
    @Id
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    @Length(min = 1,max = 10,message = "用户昵称长度不合法")
    private String nickName;
    /**
     * 手机号
     */
    @Pattern(regexp = "[1][3,4,5,7,8][0-9]{9}",message = "手机号不合法")
    private String mobilePhone;
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不合法")
    private String email;
    /**
     * 密码
     */
    @Length(min = 6,max = 16,message = "密码长度介于6-16之间")
    private String password;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 乐观锁
     */
    private Integer revision;
    /**
     * 创建人
     */
    private Long createdBy;
    /**
     * 创建时间
     */
    private Long createdTime;
    /**
     * 更新人
     */
    private Long updatedBy;
    /**
     * 更新时间
     */
    private Long updatedTime;
    /**
     * 逻辑删除
     */
    private Integer delStatus;
}
