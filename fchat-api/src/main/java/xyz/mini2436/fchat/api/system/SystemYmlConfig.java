package xyz.mini2436.fchat.api.system;

import lombok.Data;

/**
 * SystemYmlConfig
 *
 * @author mini2436
 * @date 2021-07-16 17:01
 **/
@Data
public class SystemYmlConfig {
    /**
     * 进行请求的token名称定义
     */
    private String tokenName;
    /**
     * 用户密码加密的Key
     */
    private String encryptionKey;
    /**
     * token加密的Key
     */
    private String tokenKey;
    /**
     * token过期的超时时间
     */
    private Long tokenExpiredTime;
}
