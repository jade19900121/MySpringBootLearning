package com.example.demo.mq;

import com.example.demo.model.OrderPojo;
import com.example.demo.model.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fangjiulin
 * @date 2023/6/22 21:43
 */
@Component
public class ObjectSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public Boolean send(OrderPojo orderPojo) {
        try {
            this.amqpTemplate.convertAndSend("topicExchange", "tp_spike_0", orderPojo.toString());
            System.out.println(orderPojo.toString());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
