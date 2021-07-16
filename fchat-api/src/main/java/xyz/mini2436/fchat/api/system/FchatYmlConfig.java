package xyz.mini2436.fchat.api.system;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义配置JavaBean转换
 *
 * @author mini2436
 * @date 2021-07-16 16:44
 **/
@Data
@Component
@ConfigurationProperties(prefix = "fchat")
public class FchatYmlConfig {
    /**
     * 不进行登录校验的URL
     */
    private List<String> noneCertificationUrl;
    /**
     * 文件服务器配置相关的参数定义
     */
    private SeaweedFsYmlConfig seaweedFs;
    /**
     * 系统配置参数的定义
     */
    private SystemYmlConfig system;

}
