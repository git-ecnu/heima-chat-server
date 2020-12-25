package com.itheima.hchat.service.impl;

import com.itheima.hchat.mapper.TbUserMapper;
import com.itheima.hchat.pojo.TbUser;
import com.itheima.hchat.pojo.TbUserExample;
import com.itheima.hchat.pojo.vo.User;
import com.itheima.hchat.service.UserService;
import com.itheima.hchat.util.FastDFSClient;
import com.itheima.hchat.util.IdWorker;
import com.sun.crypto.provider.PBEWithMD5AndDESCipher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private IdWorker idWroker;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private Environment env;

    @Override
    public List<TbUser> findAll() {
        return userMapper.selectByExample(null);
    }

    @Override
    public User login(String username, String password) {
        if(StringUtils.isNoneBlank(username) && StringUtils.isNotBlank(password)) {
            TbUserExample example = new TbUserExample();
            TbUserExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameEqualTo(username);
            List<TbUser> userList = userMapper.selectByExample(example);
            if(userList != null && userList.size() == 1){
                String encondingPassword = DigestUtils.md5DigestAsHex(password.getBytes());
                if(encondingPassword.equals(userList.get(0).getPassword())){
                    User user = new User();
                    BeanUtils.copyProperties(userList.get(0), user);
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public void register(TbUser user) {
        try {
            TbUserExample example = new TbUserExample();
            TbUserExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameEqualTo(user.getUsername());
            List<TbUser> userList = userMapper.selectByExample(example);
            if(userList != null & userList.size()>0){
                throw new RuntimeException("用户已存在");
            }
            user.setId(idWroker.nextId());
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user.setPicNormal("");
            user.setPicNormal("");
            user.setNickname(user.getUsername());
            user.setCreatetime(new Date());

            userMapper.insert(user);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw  new RuntimeException("注册失败");
        }
    }

    @Override
    public User upload(MultipartFile file, String userid) {
        try {
            String url = fastDFSClient.uploadFace(file);
            String[] fileNameList = url.split("\\.");
            String fileName = fileNameList[0];
            String ext = fileNameList[1];
            String picSmallUrl = fileName + "_150x150." + ext;

            TbUser tbUser = userMapper.selectByPrimaryKey(userid);

            String prefix = env.getProperty("fdfs.httpurl");

            tbUser.setPicNormal(prefix+url);
            tbUser.setPicSmall(prefix+picSmallUrl);

            userMapper.updateByPrimaryKey(tbUser);

            User user = new User();
            BeanUtils.copyProperties(tbUser, user);

            return user;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateNickname(String id, String nickname) {
        if(StringUtils.isNotBlank(nickname)){
            TbUser tbUser = userMapper.selectByPrimaryKey(id);
            tbUser.setNickname(nickname);
            userMapper.updateByPrimaryKey(tbUser);

        }
        else {
            throw new RuntimeException("昵称不能为空");
        }

    }

    @Override
    public User findByID(String userid) {
        TbUser tbUser = userMapper.selectByPrimaryKey(userid);
        User user = new User();
        BeanUtils.copyProperties(tbUser, user);
        return user;
    }
}
