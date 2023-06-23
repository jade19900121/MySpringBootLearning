package com.example.demo.controller;

import com.example.demo.model.OrderPojo;
import com.example.demo.mq.ObjectSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.websocket.SendResult;

/**
 * @author fangjiulin
 * @date 2023/6/22 21:02
 */
@RestController
@RequestMapping("/begin")
@Slf4j
@CrossOrigin(origins = "*")
public class ProducerController {
    @Autowired
    ObjectSender objectSender;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping(value = "/spike/{uuid}/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String spike(@PathVariable String uuid, @PathVariable int userId) throws InterruptedException {
        //判断链接是否正常，如果正常进行抢单操作。
        if (redisTemplate.hasKey(uuid)) {
            if (spikeOrder(userId)) {
                return "恭喜" + userId + "用户，抢单成功";
            } else {
                return "抱歉" + userId + "用户,商品已经抢光，欢迎下次再来。";
            }
        }
        return "抱歉" + userId + "用户,页面丢失了，请刷新";
    }


    public boolean spikeOrder(int uid) {
        String key = "product_number:001";
        return orderHandler(key, uid);
    }

    private synchronized boolean orderHandler(String key, int uid) {
        // 第二步：减少库存
        Long value = redisTemplate.opsForValue().decrement(key);
        // 库存充足
        if (value >= 0) {
            // 通过 rocketmq 发送创建订单的消息，并且 update 数据库中商品库存。
            boolean res = createOrder(uid, value);
            //如果下订单成功，返回。
            if (res) {
                return true;
            }
        } else {
            log.info("商品已经抢光，欢迎下次再来。");
        }
        //如果下单失败，则恢复库存。
        redisTemplate.opsForValue().increment(key);
        return false;
    }

    private boolean createOrder(int uid, Long value) {
        //创建一个订单对想
        OrderPojo orderPojo = new OrderPojo();
        //设置秒杀商品编号
        orderPojo.setOrderId(1);
        //库存
        orderPojo.setStock(value);
        //购买数量，每次只能抢购一个
        orderPojo.setNumber(1);
        //购买用户id
        orderPojo.setUserId(uid);
        //需要捕获各种异常
        try {
            return sendMsg(orderPojo);
        } catch (Exception e) {
            log.info("{}", e);
        }
        return false;
    }


    public Boolean sendMsg(OrderPojo orderPojo) {
        return objectSender.send(orderPojo);
    }

}

