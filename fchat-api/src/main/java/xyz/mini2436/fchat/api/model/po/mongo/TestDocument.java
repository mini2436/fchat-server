package xyz.mini2436.fchat.api.model.po.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 测试Mongol的数据操作
 *
 * @author mini2436
 * @date 2021-07-09 09:46
 **/
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestDocument {
    @Id
    private Integer id;
    private String name;
    private String phone;
    private Integer age;
}
