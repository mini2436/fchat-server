package xyz.mini2436.fchat.api.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.mini2436.fchat.api.system.FchatYmlConfig;
import xyz.mini2436.fchat.exceptions.ParameterException;

/**
 * 密码工具类
 *
 * @author mini2436
 * @date 2021-07-09 10:20
 **/
@Component
@RequiredArgsConstructor
public class PasswordUtil {
    private final FchatYmlConfig fchatYmlConfig;


    /**
     * 通过摘要算法获取新密码
     * @param password 需要被加密的明文密码
     * @return 加密后的密文密码
     */
    public String strEncryption(String password){
        if (StrUtil.hasBlank(password)){
            throw new ParameterException("加密字符串为空");
        }
        return String.valueOf(SecureUtil.hmacSha1(generateKey()).digestHex(password));
    }

    /**
     * 通过明文的KEY生成一个加密的KEY
     * @return 返回加密的KEY
     */
    public String generateKey() {
        return SecureUtil.hmacSha1(fchatYmlConfig.getSystem()
                .getEncryptionKey()).digestHex(fchatYmlConfig.getSystem().getEncryptionKey());
    }
}
