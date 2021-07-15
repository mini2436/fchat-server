package xyz.mini2436.fchat.api.model.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件服务器Fid实体封装
 *
 * @author mini2436
 * @date 2021-07-15 09:31
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeaweedFs implements Serializable {
    private String fid;
    private String url;
    private String publicUrl;
    private Integer count;
}
