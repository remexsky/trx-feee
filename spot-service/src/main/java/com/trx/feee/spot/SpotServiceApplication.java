package com.trx.feee.spot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Spot Service Application
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotServiceApplication.class, args);
    }

}