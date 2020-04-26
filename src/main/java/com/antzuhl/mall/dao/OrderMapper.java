package com.antzuhl.mall.dao;

import com.antzuhl.mall.pojo.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByUserIdAndOrdNo(Integer userId, Long orderNo);

    Order selectByOrderNo(Long orderNo);
}