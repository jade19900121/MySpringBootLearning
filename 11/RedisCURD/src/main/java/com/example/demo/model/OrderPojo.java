package com.example.demo.model;

import lombok.Data;

/**
 * @author fangjiulin
 * @date 2023/6/22 21:03
 */
@Data
public class OrderPojo {

    private Integer userId;
    private Integer orderId;
    private Integer number;
    private Long Stock;

    @Override
    public String toString() {
        return "OrderPojo{" +
                "userId=" + userId +
                ", orderId=" + orderId +
                ", number=" + number +
                ", Stock=" + Stock +
                '}';
    }
}
