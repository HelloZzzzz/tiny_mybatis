package com.lzb.dao;

import com.lzb.bean.User;

import java.util.List;

public interface UserDao {

    //查询所有用户
    List<User> findAll() throws Exception;

    //根据条件进行用户查询
    User findByCondition(User user) throws Exception;

    int updatePasswordById(User user) throws Exception;

    int insertUser(User user) throws Exception;

    int deleteUser(User user) throws Exception;

}
