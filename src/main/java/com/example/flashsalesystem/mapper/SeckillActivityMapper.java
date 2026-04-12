package com.example.flashsalesystem.mapper;


import com.example.flashsalesystem.entity.SeckillActivity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;


@Mapper
public interface SeckillActivityMapper {

    @Insert("insert into seckill_activity(name,start_time,end_time,status,create_time) values" +
            "(#{name},#{startTime},#{endTime},#{status},#{createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public abstract void insert(SeckillActivity seckillActivity);

    @Select("select * from seckill_activity where id=#{id}")
    public SeckillActivity findById(Long id);

    @Select("select * from seckill_activity")
    public List<SeckillActivity> findAll();
}
