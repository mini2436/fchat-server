package xyz.mini2436.fchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 更新用户信息实体类
 *
 * @author mini2436
 * @date 2021-07-20 14:17
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2829841900669340703L;

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
    @Length(min = 5,max = 10,message = "头像id长度不正确")
    private String avatar;
    /**
     * 出生日期
     */
    @Past(message = "出生日期时间不正确")
    private Date birthday;
}
