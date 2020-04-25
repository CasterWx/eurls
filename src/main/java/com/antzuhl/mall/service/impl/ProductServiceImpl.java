package com.antzuhl.mall.service.impl;

import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.dao.ProductMapper;
import com.antzuhl.mall.pojo.Product;
import com.antzuhl.mall.service.ProductService;
import com.antzuhl.mall.vo.ProductDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public ServiceResponse<String> saveProduct(Product product) {
        if (product!=null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String []subImages = product.getSubImages().split(",");
                if (subImages.length>0) {
                    product.setMainImage(subImages[0]);
                }
            }
            if (product.getId() != null) {
                // update
                int rowCount =productMapper.updateByPrimaryKey(product);
                if (rowCount>0) {
                    return ServiceResponse.createSuccessResponse("更新产品成功");
                }
                return ServiceResponse.createErrorResponse("更新产品失败");
            } else {
                //insert
                int rowCount =productMapper.insert(product);
                if (rowCount>0) {
                    return ServiceResponse.createSuccessResponse("添加产品成功");
                }
                return ServiceResponse.createErrorResponse("添加产品失败");
            }
        }
        return ServiceResponse.createErrorResponse("请求参数错误");
    }

    @Override
    public ServiceResponse<String> setSaleStatus(Integer productId,
                                                 Integer status) {
        if (productId == null || status == null) {
            return ServiceResponse.createErrorResponse("请求参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServiceResponse.createErrorResponse("产品不存在");
        }
        if (product.getStatus() == status) {
            return ServiceResponse.createSuccessResponse("修改成功");
        }
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKey(product);
        if (rowCount > 0) {
            return ServiceResponse.createSuccessResponse("修改成功");
        }
        return ServiceResponse.createErrorResponse("产品状态修改失败");
    }

    @Override
    public ServiceResponse<ProductDetail> getDetail(Integer productId) {
        if (productId == null) {
            return ServiceResponse.createErrorResponse("请求参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product != null) {
            return ServiceResponse.createSuccessResponse(new ProductDetail().toData(product));
        }
        return ServiceResponse.createErrorResponse("获取产品信息失败");
    }


}
