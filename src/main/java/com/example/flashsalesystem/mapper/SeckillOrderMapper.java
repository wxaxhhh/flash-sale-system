package com.example.flashsalesystem.mapper;

import com.example.flashsalesystem.entity.SeckillOrder;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface SeckillOrderMapper {

    @Insert("insert into seckill_order(user_id, product_id, activity_id) " +
            "values(#{userId}, #{productId}, #{activityId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(SeckillOrder order);

    @Select("select * from seckill_order where id = #{id}")
    SeckillOrder findById(Long id);

    @Select("select * from seckill_order where user_id = #{userId}")
    List<SeckillOrder> findByUserId(Long userId);

    @Select("select * from seckill_order where user_id = #{userId} and activity_id = #{activityId} " +
            "and product_id = #{productId}")
    SeckillOrder findByUserActivityProduct(@Param("userId") Long userId,
                                           @Param("activityId") Long activityId,
                                           @Param("productId") Long productId);

    @Update("update seckill_order set status = #{status} where id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}