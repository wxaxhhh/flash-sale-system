package com.example.flashsalesystem.entity;

import java.util.Date;

public class SeckillOrder {
    private Long id;
    private Long userId;
    private Long activityId;
    private Long productId;
    private Integer status; // 0=未支付, 1=已支付, 2=已取消
    private Date createTime;

    // getter / setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}