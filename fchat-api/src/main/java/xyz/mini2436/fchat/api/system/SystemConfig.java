package xyz.mini2436.fchat.api.system;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 系统配置类
 *
 * @author mini2436
 * @date 2021-07-15 11:32
 **/
@Configurable
public class SystemConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }
}
