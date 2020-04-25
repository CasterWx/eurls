package com.antzuhl.mall.controller;

import com.antzuhl.mall.common.Const;
import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.pojo.Product;
import com.antzuhl.mall.pojo.User;
import com.antzuhl.mall.service.ProductService;
import com.antzuhl.mall.vo.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("/saveProduct")
    @ResponseBody
    public ServiceResponse<String> saveProduct(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createErrorResponse("请登录");
        }
        if (user.getRole() != Const.ROLE_ADMIN) {
            return ServiceResponse.createErrorResponse("权限不足");
        }
        return productService.saveProduct(product);
    }

    @RequestMapping("/setSaleStatus")
    @ResponseBody
    public ServiceResponse<String> setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createErrorResponse("请登录");
        }
        if (user.getRole() != Const.ROLE_ADMIN) {
            return ServiceResponse.createErrorResponse("权限不足");
        }
        return productService.setSaleStatus(productId, status);
    }

    @RequestMapping("/getDetail")
    @ResponseBody
    public ServiceResponse<ProductDetail> getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createErrorResponse("请登录");
        }
        if (user.getRole() != Const.ROLE_ADMIN) {
            return ServiceResponse.createErrorResponse("权限不足");
        }
        return productService.getDetail(productId);
    }
}