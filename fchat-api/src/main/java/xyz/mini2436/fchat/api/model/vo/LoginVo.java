package xyz.mini2436.fchat.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
public class LoginVo implements Serializable {
    private static final long serialVersionUID = 2037284732715392387L;
    /**
     * 登录成功的认证
     */
    private String token;
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
     * 出生日期
     */
    private Date birthday;
}
