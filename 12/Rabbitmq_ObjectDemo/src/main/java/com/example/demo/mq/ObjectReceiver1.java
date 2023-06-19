package com.example.demo.mq;

import com.example.demo.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author fangjiulin
 * @date 2023/6/19 23:38
 */
@Component
@RabbitListener(queues = "object1")
public class ObjectReceiver1 {
    @RabbitHandler
    public void process(User user) {
        System.out.println("Receiver object1 : " + user);
    }
}
