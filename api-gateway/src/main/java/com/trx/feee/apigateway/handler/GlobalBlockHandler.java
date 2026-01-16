package com.trx.feee.apigateway.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Block Handler for Sentinel
 */
public class GlobalBlockHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Handle block exceptions from Sentinel
     */
    public static Mono<Void> handleBlock(ServerWebExchange exchange, BlockException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.TOO_MANY_REQUESTS.value());
        response.put("message", "请求过于频繁，请稍后再试");
        response.put("data", null);

        try {
            byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(response);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return exchange.getResponse().setComplete();
        }
    }

    /**
     * Handle fallback exceptions
     */
    public static Mono<Void> handleFallback(ServerWebExchange exchange, Throwable ex) {
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", "服务暂时不可用，请稍后再试");
        response.put("data", null);

        try {
            byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(response);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return exchange.getResponse().setComplete();
        }
    }
}