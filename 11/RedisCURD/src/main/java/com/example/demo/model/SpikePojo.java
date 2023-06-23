package com.example.demo.model;

import lombok.Data;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * @author fangjiulin
 * @date 2023/6/22 20:45
 */
@Data
public class SpikePojo {

    /**
     * 库存表id
     */
    private Integer id;
    /**
     * 库存品种
     */
    private String name;
    /**
     * 库存量
     */
    private Integer number;


    @Override
    public String toString() {
        return "SpikePojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
