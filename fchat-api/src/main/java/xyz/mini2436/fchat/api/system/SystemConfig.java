package xyz.mini2436.fchat.api.system;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 系统配置类
 *
 * @author mini2436
 * @date 2021-07-15 11:32
 **/
@Configurable
@RequiredArgsConstructor
public class SystemConfig {
    private final ConnectionFactory connectionFactory;
    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }
    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory){
        return new R2dbcEntityTemplate(connectionFactory);
    }
}
