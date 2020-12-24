package com.itheima.hchat.controller;

import com.itheima.hchat.pojo.TbUser;
import com.itheima.hchat.pojo.vo.Result;
import com.itheima.hchat.pojo.vo.User;
import com.itheima.hchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<TbUser> findAll(){
        System.out.println("Debug");
        return userService.findAll();
    }
    @RequestMapping("/login")
    public Result login(@RequestBody TbUser user) {
        User _user = userService.login(user.getUsername(), user.getPassword());
        try {
            if(_user == null){
                return new Result(false,"登录失败，密码用户名错误");
            }
            else {
                return new Result(true,"登录成功", _user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "登录错误");
        }

    }


}
