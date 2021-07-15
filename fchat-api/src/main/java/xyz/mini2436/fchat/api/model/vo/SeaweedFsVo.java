package xyz.mini2436.fchat.api.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件服务器返回对象
 *
 * @author mini2436
 * @date 2021-07-15 09:39
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeaweedFsVo {
    private String url;
    private String fid;
}
