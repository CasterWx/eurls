package com.antzuhl.mall.service;

import com.antzuhl.mall.common.ServiceResponse;
import com.antzuhl.mall.pojo.User;

public interface UserService {

    ServiceResponse<User> login(String username, String password);

    ServiceResponse<String> registor(User user);

    ServiceResponse<String> forget(String username);

    ServiceResponse<String> answer(String username, String answer);

    ServiceResponse<String> resetPassword(String token, String username, String password );
}
