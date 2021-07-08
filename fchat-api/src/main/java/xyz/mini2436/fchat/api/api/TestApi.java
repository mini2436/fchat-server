package xyz.mini2436.fchat.api.api;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.mini2436.fchat.api.model.po.TestTable;
import xyz.mini2436.fchat.api.model.vo.ApiVo;
import xyz.mini2436.fchat.api.repository.TestTableRepository;
import xyz.mini2436.fchat.api.utils.JsonUtils;
import xyz.mini2436.fchat.exceptions.ServiceException;
import xyz.mini2436.fchat.model.vo.ResultVO;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试API
 *
 * @author mini2436
 * @date 2021-07-05 21:24
 **/
@RequestMapping("test")
@RestController
@RequiredArgsConstructor
@Slf4j
public class TestApi extends ApiVo {
    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;
    private final TestTableRepository testTableRepository;

    /**
     * Redis测试接口
     *
     * @return Redis中的Key数据
     */
    @GetMapping
    Mono<ResultVO<Map<String, Object>>> getCache() {
        return reactiveStringRedisTemplate
                .opsForValue()
                .get("ZRA170000")
                .flatMap(s -> Mono.deferContextual(ctx -> {
                    String userId = ctx.get("userId");
                    if (StrUtil.hasBlank(userId)) {
                        throw new ServiceException("登录校验认证未通过");
                    } else {
                        return Mono.just(s);
                    }
                }))
                .map(v -> JsonUtils.jsonToObj(v, new HashMap<String, Object>(16)))
                .flatMap(this::success);
    }

    /**
     * Mysql异步测试接口
     *
     * @return 返回是否新增成功的状态
     */
    @PostMapping
    Mono<ResultVO<Boolean>> addData() {
        return testTableRepository
                .addOneData(TestTable.builder().id(123).age(24).name("chenlong").phone("15625702083").build())
                .flatMap(this::success);
    }

    /**
     * 根据id查询数据库中的测试数据
     *
     * @param id 查询的用户主键
     * @return 返回查询的数据库数据
     */
    @GetMapping("/{id}")
    Mono<ResultVO<TestTable>> getOneTableData(@PathVariable Integer id) {
        return testTableRepository.findById(id).flatMap(this::success);
    }
}
