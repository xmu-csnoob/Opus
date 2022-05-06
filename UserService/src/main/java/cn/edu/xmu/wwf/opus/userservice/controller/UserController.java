package cn.edu.xmu.wwf.opus.userservice.controller;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import cn.edu.xmu.wwf.opus.userservice.model.vo.UserLoginVo;
import cn.edu.xmu.wwf.opus.userservice.model.vo.UserRegisterVo;
import cn.edu.xmu.wwf.opus.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public ReturnObject login(@RequestBody UserLoginVo userLoginVo, HttpServletResponse response){
        return userService.checkUserLogin(userLoginVo,response);
    }
    @PostMapping("/register")
    public ReturnObject register(@RequestBody UserRegisterVo userRegisterVo){
        return userService.registerNewUser(userRegisterVo);
    }
    @GetMapping("/user/{id}")
    public ReturnObject getUserInfo(@PathVariable int id){
        return userService.getUserInfo(id);
    }
}
