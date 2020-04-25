package com.antzuhl.mall.service;

import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.pojo.Product;
import com.antzuhl.mall.vo.ProductDetail;

public interface ProductService {

    ServiceResponse<String> saveProduct(Product product);

    ServiceResponse<String> setSaleStatus(Integer productId, Integer status);

    ServiceResponse<ProductDetail> getDetail(Integer productId);
}
