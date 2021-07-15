package xyz.mini2436.fchat.api.utils;

import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 密码工具类
 *
 * @author mini2436
 * @date 2021-07-09 10:20
 **/
@Component
public class PasswordUtil {

    /**
     * 注入配置文件中的加密密钥
     */
    @Value("${fchat.system.encryptionKey}")
    private String encryptionKey;

    /**
     * 通过摘要算法获取新密码
     * @param password 需要被加密的明文密码
     * @return 加密后的密文密码
     */
    public String strEncryption(String password){
        return String.valueOf(SecureUtil.hmacSha1(generateKey()).digestHex(password));
    }

    /**
     * 通过明文的KEY生成一个加密的KEY
     * @return 返回加密的KEY
     */
    public String generateKey() {
        return SecureUtil.hmacSha1(encryptionKey).digestHex(encryptionKey);
    }
}
