package com.itheima.hchat.service;

import com.itheima.hchat.pojo.TbUser;
import com.itheima.hchat.pojo.vo.User;

import java.util.List;

public interface UserService {
    List<TbUser> findAll();

    User login(String username, String password);
}
