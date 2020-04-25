package com.antzuhl.mall.controller;

import com.antzuhl.mall.common.Const;
import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.pojo.User;
import com.antzuhl.mall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping("/pay")
    @ResponseBody
    public ServiceResponse pay(HttpSession session, Long orderNo, HttpServletRequest request) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServiceResponse.createErrorResponse("请登录");
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        logger.info("OrderController Pay orderNo:{}, userId:{}, path:{}", orderNo, user.getId(), path);
        return orderService.pay(orderNo, user.getId(), path);
    }
}
