package com.antzuhl.mall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.antzuhl.mall.common.Const;
import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.pojo.User;
import com.antzuhl.mall.service.OrderService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

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

    @RequestMapping("/payCallback")
    @ResponseBody
    public Object payCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String []values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i=0; i<values.length; i++) {
                valueStr = (i == values.length - 1)?valueStr+values[i]:valueStr+valueStr+values[i]+",";
            }
            params.put(name, valueStr);
        }
        logger.info("支付宝回调, sign:{}, trade_status:{}, 参数:{}",
                params.get("sign"), params.get("trade_status"), params.toString());
        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!alipayRSACheckedV2) {
                return ServiceResponse.createErrorResponse("请求非法,验证不通过,再恶意请求就报警了");
            }
        } catch (AlipayApiException e) {
            logger.info("支付宝回调异常",e);
        }

        ServiceResponse response = orderService.payCallback(params);

        if (response.isSuccess()) {
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    @RequestMapping("/queryOrderStatus")
    @ResponseBody
    public ServiceResponse<Boolean> queryOrderStatus(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user==null) {
            return ServiceResponse.createErrorResponse("用户未登录");
        }

        ServiceResponse response = orderService.queryOrderStatus(user.getId(), orderNo);
        if(response.isSuccess()) {
            return ServiceResponse.createSuccessResponse(true);
        }
        return ServiceResponse.createSuccessResponse(false);
    }
}
