package com.antzuhl.mall.controller;

import com.antzuhl.mall.common.Const;
import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.pojo.User;
import com.antzuhl.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public ServiceResponse<User> login(String username, String password, HttpSession session) {
        ServiceResponse<User> response = userService.login(username, password);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public ServiceResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServiceResponse.createSuccessResponse();
    }

    @RequestMapping("/register")
    @ResponseBody
    public ServiceResponse<String> register(User user) {
        return userService.registor(user);
    }

    @RequestMapping("/forget")
    @ResponseBody
    public ServiceResponse<String> forget(String username) {
        // 根据username返回question
        return userService.forget(username);
    }

    @RequestMapping("/answer")
    @ResponseBody
    public ServiceResponse<String> answer(String username, String answer) {
        return userService.answer(username, answer);
    }

    @RequestMapping("resetPassword")
    @ResponseBody
    public ServiceResponse<String> resetPassword(String token, String username, String password) {
        return userService.resetPassword(token, username, password);
    }
}
