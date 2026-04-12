package com.example.flashsalesystem.mapper;

import com.example.flashsalesystem.entity.SeckillProduct;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SeckillProductMapper {

    @Insert("insert into seckill_product(activity_id, name, seckill_price, stock, create_time) " +
            "values(#{activityId}, #{name}, #{seckillPrice}, #{stock}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(SeckillProduct product);

    @Select("select * from seckill_product where id = #{id}")
    SeckillProduct findById(Long id);

    @Select("select * from seckill_product")
    List<SeckillProduct> findAll();

    @Select("select * from seckill_product where activity_id = #{activityId}")
    List<SeckillProduct> findByActivityId(Long activityId);

    @Update("update seckill_product set stock = #{stock} where id = #{id}")
    int updateStock(@Param("id") Long id, @Param("stock") Integer stock);
}