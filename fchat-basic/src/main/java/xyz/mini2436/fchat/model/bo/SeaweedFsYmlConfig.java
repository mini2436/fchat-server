package xyz.mini2436.fchat.model.bo;

import lombok.Data;

/**
 * 文件服务器配置类
 *
 * @author mini2436
 * @date 2021-07-16 16:46
 **/
@Data
public class SeaweedFsYmlConfig {
    /**
     * 文件服务器的地址
     */
    private String url;
    /**
     * Windows的临时文件存储路径
     */
    private String windowsTempFilePath;
    /**
     * Linux的临时文件存储路径
     */
    private String linuxTempFilePath;
}
