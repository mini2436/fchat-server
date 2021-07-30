package xyz.mini2436.fchat.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author mini2436
 */
@SpringBootApplication
@EnableTransactionManagement
public class FchatApi {

	public static void main(String[] args) {
		SpringApplication.run(FchatApi.class, args);
	}

}
