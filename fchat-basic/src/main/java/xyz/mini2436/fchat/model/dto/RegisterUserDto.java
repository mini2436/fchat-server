package xyz.mini2436.fchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求数据封装
 *
 * @author mini2436
 * @date 2021-07-22 15:36
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 7623418369759773425L;

    /**
     * 用户昵称
     */
    @Length(min = 1,max = 30,message = "用户昵称长度不合法")
    @NotBlank(message = "注册昵称不能为空")
    private String nickName;
    /**
     * 手机号
     */
    @Pattern(regexp = "[1][3,4,5,7,8][0-9]{9}",message = "手机号不合法")
    @NotBlank(message = "注册手机号不能为空")
    private String mobilePhone;
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不合法")
    @NotBlank(message = "注册邮箱不能为空")
    private String email;
    /**
     * 密码
     */
    @Length(min = 6,max = 16,message = "密码长度介于6-16之间")
    @NotBlank(message = "注册密码不能为空")
    private String password;
    /**
     * 头像
     */
    @URL(message = "头像地址不正确")
    @NotBlank(message = "注册头像地址不能为空")
    private String avatar;

    /**
     * 个人描述
     */
    @Length(min = 0 , max = 250,message = "个性签名长度不正确")
    private String description;
}
