package xyz.mini2436.fchat.api.api;

import xyz.mini2436.fchat.model.vo.BasicApiVO;
import xyz.mini2436.fchat.model.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 测试API
 *
 * @author mini2436
 * @date 2021-07-05 21:24
 **/
@RequestMapping("test")
@RestController
public class TestApi extends BasicApiVO {


    /**
     * 测试接口
     * @return 返回一个hello
     */
    @GetMapping
    Mono<ResultVO<String>> hello(){
        return Mono.just(success("hello"));
    }

}
