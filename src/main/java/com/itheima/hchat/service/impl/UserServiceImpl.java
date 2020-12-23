package com.itheima.hchat.service.impl;

import com.itheima.hchat.mapper.TbUserMapper;
import com.itheima.hchat.pojo.TbUser;
import com.itheima.hchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Override
    public List<TbUser> findAll() {
        return userMapper.selectByExample(null);
    }
}
