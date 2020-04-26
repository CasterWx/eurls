package com.antzuhl.mall.service;

import com.antzuhl.mall.common.ServiceResponse;

import java.util.Map;

public interface OrderService {
    ServiceResponse pay(Long orderNo, Integer userId, String path);
    ServiceResponse payCallback(Map<String,String> params);
    ServiceResponse<Boolean> queryOrderStatus(Integer userId, Long orderNo);
}
