package xyz.mini2436.fchat.api.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.po.TestTable;

/**
 * 测试jpa
 *
 * @author: mini2436
 * @date: 2021-07-08 15:57
 **/
public interface TestTableRepository extends ReactiveSortingRepository<TestTable,Integer> {
    /**
     * 添加一条测试数据
     * @param testTable 测试数据实体封装
     * @return 返回测试表
     */
    @Modifying
    @Query("insert into test_table (id,name,phone,age) values (:#{#testTable.id},:#{#testTable.name},:#{#testTable.phone},:#{#testTable.age})")
    Mono<Boolean> addOneData(@Param("testTable") TestTable testTable);
}
