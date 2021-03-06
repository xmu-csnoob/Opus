package cn.edu.xmu.wwf.opus.userservice.controller;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import cn.edu.xmu.wwf.opus.userservice.microservice.ImageService;
import cn.edu.xmu.wwf.opus.userservice.microservice.model.image.GetImageUrlRetVo;
import cn.edu.xmu.wwf.opus.userservice.microservice.model.image.PostImageRetVo;
import cn.edu.xmu.wwf.opus.userservice.model.vo.UserLoginVo;
import cn.edu.xmu.wwf.opus.userservice.model.vo.UserRegisterVo;
import cn.edu.xmu.wwf.opus.userservice.service.UserService;
import cn.edu.xmu.wwf.opus.userservice.util.TokenUtil;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ImageService imageService;
    @PostMapping("/login")
    public ReturnObject login(@RequestBody UserLoginVo userLoginVo, HttpServletResponse response){
        return userService.checkUserLogin(userLoginVo,response);
    }
    @PostMapping("/register")
    public ReturnObject register(@RequestBody UserRegisterVo userRegisterVo){
        return userService.registerNewUser(userRegisterVo);
    }
    @GetMapping("/user")
    public ReturnObject getUserInfo(@RequestHeader String token){
        int id= Integer.parseInt(TokenUtil.get(token));
        return userService.getUserInfo(id);
    }
    @GetMapping("/user/{id}")
    public ReturnObject getUserInfo(@PathVariable int id){
        return userService.getUserInfo(id);
    }
    @PostMapping("/user/avatar")
    //先上传再修改
    //user绑定imageId作为avatar
    public ReturnObject alterAvatar(@RequestPart("file") MultipartFile multipartFile,@RequestHeader String token){
        int id= Integer.parseInt(TokenUtil.get(token));
        ReturnObject returnObject=imageService.uploadImage(multipartFile,"avatars");
        PostImageRetVo postImageRetVo=(PostImageRetVo) returnObject.data;
        System.out.println(postImageRetVo);
        if(returnObject.returnNo!= ReturnNo.OK){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERROR,"上传图像失败");
        }
        return userService.alterAvatar(id,postImageRetVo.getUrl());
    }
}
