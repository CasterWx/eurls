package com.antzuhl.mall.service.impl;

import com.antzuhl.mall.common.Const;
import com.antzuhl.mall.common.HResult;
import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.common.TokenCache;
import com.antzuhl.mall.dao.UserMapper;
import com.antzuhl.mall.pojo.User;
import com.antzuhl.mall.service.UserService;
import com.antzuhl.mall.util.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public ServiceResponse<User> login(String username, String password) {
        if (!StringUtils.isNotBlank(username)) {
            return ServiceResponse.createErrorResponse("用户名或密码为空");
        }
        if (!StringUtils.isNotBlank(password)) {
            return ServiceResponse.createErrorResponse("用户名或密码为空");
        }

        int count = userMapper.checkUsername(username);
        if (count == 0) {
            return ServiceResponse.createErrorResponse("用户名不存在");
        }

        // todo MD5加密
        String pwd = MD5Utils.string2MD5(password);

        User user = userMapper.selectLogin(username, pwd);
        if (user == null) {
            return ServiceResponse.createErrorResponse("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServiceResponse.createSuccessResponse("登录成功", user);
    }

    @Override
    public ServiceResponse<String> registor(User user) {
        if (!user.vaild()) {
            return ServiceResponse.createErrorResponse("用户注册信息规范错误");
        }
        int resultCount = userMapper.checkUsername(user.getUsername());
        if (resultCount > 0) {
            return ServiceResponse.createErrorResponse("用户名存在");
        }
        // todo checkEmail
        resultCount = userMapper.checkEmail(user.getEmail());
        if (resultCount>0) {
            return ServiceResponse.createErrorResponse("邮箱存在");
        }

        user.setRole(Const.ROLE_CUSTOMER);
        user.setPassword(MD5Utils.string2MD5(user.getPassword()));
        user.setCreateTime(new Date());
        resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServiceResponse.createErrorResponse("注册失败");
        }
        return ServiceResponse.createSuccessResponse("注册成功");
    }

    @Override
    public ServiceResponse<String> forget(String username) {
        if (!StringUtils.isNotBlank(username)) {
            return ServiceResponse.createErrorResponse("用户名不能为空");
        }
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServiceResponse.createErrorResponse("没有该用户");
        }
        String questionResult = userMapper.selectQuestion(username);
        if (!StringUtils.isNotBlank(questionResult)) {
            return ServiceResponse.createErrorResponse("用户未设置密保问题");
        }
        return ServiceResponse.createSuccessResponse(HResult.R_OK.getMsg(), questionResult);
    }

    @Override
    public ServiceResponse<String> answer(String username, String answer) {
        if (!StringUtils.isNotBlank(username)) {
            return ServiceResponse.createErrorResponse("用户名不能为空");
        }
        if (!StringUtils.isNotBlank(answer)) {
            return ServiceResponse.createErrorResponse("答题不能为空");
        }
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServiceResponse.createErrorResponse("用户名不存在");
        }
        int answerCount = userMapper.checkAnswer(username, answer);
        if (answerCount == 0) {
            return ServiceResponse.createErrorResponse("回答错误");
        }
        // 生成一个修改密码的token
        String tokenValue = UUID.randomUUID().toString();
        TokenCache.setKey("token_"+username, tokenValue);
        return ServiceResponse.createSuccessResponse(HResult.R_OK.getMsg(), tokenValue);
    }

    @Override
    public ServiceResponse<String> resetPassword(String token, String username, String password) {
        if (StringUtils.isBlank(token)) {
            return ServiceResponse.createErrorResponse("令牌不能为空");
        }
        if (StringUtils.isBlank(username)) {
            return ServiceResponse.createErrorResponse("用户名不能为空");
        }
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServiceResponse.createErrorResponse("用户名不存在");
        }
        String tokenValue = TokenCache.get("token_"+username);
        if (StringUtils.isBlank(tokenValue)) {
            return ServiceResponse.createErrorResponse("请回答密保问题");
        }
        if (!StringUtils.equals(token, tokenValue)) {
            return ServiceResponse.createErrorResponse("令牌错误");
        }
        String pwd = MD5Utils.string2MD5(password);
        int rowCount = userMapper.resetPassword(username, pwd);
        if (rowCount>0) {
            return ServiceResponse.createSuccessResponse("密码修改成功");
        }
        return ServiceResponse.createErrorResponse("密码修改失败");
    }
}
