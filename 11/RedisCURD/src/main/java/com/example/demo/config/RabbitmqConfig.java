package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

/**
 * @author fangjiulin
 * @date 2023/6/22 21:37
 */
@Configuration
public class RabbitmqConfig {
    @Bean
    public Queue objectQueue() {
        return new Queue("object");
    }

    @Bean
    public Queue objectQueue1() {
        return new Queue("object1");
    }



}