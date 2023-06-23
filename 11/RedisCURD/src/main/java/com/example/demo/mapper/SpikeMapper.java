package com.example.demo.mapper;

import com.example.demo.model.SpikePojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author fangjiulin
 * @date 2023/6/22 20:48
 */
@Mapper
public interface SpikeMapper {
    @Select("select * from tb_spike_data_info where id =#{id}")
    SpikePojo findById(Integer id);

    @Select("update tb_spike_data_info set `number`=`number`-1 where id =#{id}")
    void updateById(Integer id);
}
