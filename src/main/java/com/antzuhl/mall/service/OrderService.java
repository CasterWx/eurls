package com.antzuhl.mall.service;

import com.antzuhl.mall.common.ServiceResponse;

public interface OrderService {
    ServiceResponse pay(Long orderNo, Integer userId, String path);
}
