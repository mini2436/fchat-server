package xyz.mini2436.fchat.api.model.po.mysql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 测试表
 *
 * @author mini2436
 * @date 2021-07-08 15:52
 **/
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestTable implements Serializable {
    private static final long serialVersionUID = -4819361865776646944L;
    /**
     * 主键
     */
    @Id
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 年龄
     */
    private Integer age;
}
