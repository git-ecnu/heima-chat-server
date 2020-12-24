package com.itheima.hchat.service;

import com.itheima.hchat.pojo.TbUser;
import com.itheima.hchat.pojo.vo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<TbUser> findAll();

    User login(String username, String password);

    void register(TbUser user);

    User upload(MultipartFile file, String userid);
}
