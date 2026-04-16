package com.example.flashsalesystem.mapper;

import com.example.flashsalesystem.entity.SeckillOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeckillOrderMapper {

    @Insert("INSERT INTO seckill_order(user_id, activity_id, product_id, status, create_time) VALUES(#{userId}, #{activityId}, #{productId}, 0, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SeckillOrder order);

    @Select("SELECT * FROM seckill_order WHERE id = #{id}")
    SeckillOrder selectById(Long id);

    @Update("UPDATE seckill_order SET status = #{status} WHERE id = #{id}")
    void updateById(SeckillOrder order);

    @Select("SELECT * FROM seckill_order WHERE user_id = #{userId} AND activity_id = #{activityId} AND product_id = #{productId}")
    SeckillOrder findByUserActivityProduct(@Param("userId") Long userId, @Param("activityId") Long activityId, @Param("productId") Long productId);

    @Select("SELECT * FROM seckill_order WHERE user_id = #{userId}")
    List<SeckillOrder> selectByUserId(Long userId);
}