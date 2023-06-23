package com.example.demo.controller;

import com.example.demo.mapper.SpikeMapper;
import com.example.demo.model.SpikePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author fangjiulin
 * @date 2023/6/22 20:52
 */
@RestController
@RequestMapping("/url")
@CrossOrigin(origins = "*")
public class UrlController {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SpikeMapper spikeMapper;


    @RequestMapping("/get")
    public String getUrl() {
        String uuid = UUID.randomUUID().toString();
        //保存到redis 中，设置一分钟有效。
        redisTemplate.opsForValue().set(uuid, uuid, 60L, TimeUnit.SECONDS);
        return uuid;
    }

    /**
     * 库存预热
     * @return
     */
    @RequestMapping("/setNumber")
    public String setNumber(){
        String key = "product_number:001";
        // 去查数据库的数据,并且把数据库的库存set进redis
        SpikePojo spikePojo = spikeMapper.findById(1);
        if (spikePojo.getNumber() > 0) {
            redisTemplate.opsForValue().set(key, spikePojo.getNumber() + "");
        }
        return "success";
    }

}
