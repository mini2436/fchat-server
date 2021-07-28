package xyz.mini2436.fchat.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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
public class SeaweedFsVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -4185655947923000233L;
    private String url;
    private String fid;
}
