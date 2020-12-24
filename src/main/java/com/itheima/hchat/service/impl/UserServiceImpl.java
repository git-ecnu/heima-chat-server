package com.itheima.hchat.service.impl;

import com.itheima.hchat.mapper.TbUserMapper;
import com.itheima.hchat.pojo.TbUser;
import com.itheima.hchat.pojo.TbUserExample;
import com.itheima.hchat.pojo.vo.User;
import com.itheima.hchat.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

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

    @Override
    public User login(String username, String password) {
//        if(StringUtils.isNoneBlank(username) && StringUtils.isNotBlank(password)) {
//            TbUserExample example = new TbUserExample();
//            TbUserExample.Criteria criteria = example.createCriteria();
//            criteria.andUsernameEqualTo(username);
//            List<TbUser> userList = userMapper.selectByExample(example);
//            if(userList != null && userList.size() == 1){
//                String encondingPassword = DigestUtils.md5DigestAsHex(password.getBytes());
//                if(encondingPassword.equals(userList.get(0).getPassword())){
//                    User user = new User();
//                    BeanUtils.copyProperties(userList.get(0), user);
//                    return user;
//                }
//            }
//        }
        System.out.println("debug");
        return null;
    }
}
