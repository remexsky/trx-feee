package com.trx.feee.tg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Telegram Bot Service Application
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class TgBotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TgBotServiceApplication.class, args);
    }

}