package xyz.mini2436.fchat.api.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件服务器上传成功处理
 *
 * @author mini2436
 * @date 2021-07-15 09:35
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeaweedFsUpload implements Serializable {
    private String name;
    private Integer size;
    private String eTag;
}
