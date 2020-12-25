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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<TbUser> findAll() {
        System.out.println("Debug");
        return userService.findAll();
    }

    @RequestMapping("/login")
    public Result login(@RequestBody TbUser user) {
        User _user = userService.login(user.getUsername(), user.getPassword());
        try {
            if (_user == null) {
                return new Result(false, "登录失败，请检查用户名和密码");
            } else {
                return new Result(true, "登录成功", _user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "登录错误");
        }

    }

    @RequestMapping("register")
    public Result register(@RequestBody TbUser user) {
        try {
            userService.register(user);
            return new Result(true, "注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping("upload")
    public Result upload(MultipartFile file, String userid) {
        try {
            User user = userService.upload(file, userid);
            if (user != null) {
                System.out.println(user);
                return new Result(true, "上传成功", user);
            } else {
                return new Result(false, "上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
    }

    @RequestMapping("/updateNickname")
    public Result updateNickname(@RequestBody TbUser user) {
        try {
            userService.updateNickname(user.getId(), user.getNickname());
            return new Result(true, "更新成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"更新失败");
        }
    }

    @RequestMapping("/findById")
    public User findById(String userid){
        return userService.findByID(userid);
    }

}

