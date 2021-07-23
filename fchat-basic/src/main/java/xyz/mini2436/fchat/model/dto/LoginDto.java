package xyz.mini2436.fchat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;

/**
 * 系统用户VO
 *
 * @author mini2436
 * @date 2021-07-15 23:34
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 2037284732715392387L;
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
    @NotBlank(message = "登录密码不能为空")
    private String password;

    /**
     * 当前登录的设备类型
     */
    @NotBlank(message = "登录的设备类型不能为空")
    private String equipment;
}
