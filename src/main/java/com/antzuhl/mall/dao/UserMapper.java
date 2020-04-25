package com.antzuhl.mall.dao;

import com.antzuhl.mall.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    User selectLogin(String username, String password);

    int checkEmail(String email);

    String selectQuestion(String username);

    int checkAnswer(String username, String answer);

    int resetPassword(String username, String password);
}