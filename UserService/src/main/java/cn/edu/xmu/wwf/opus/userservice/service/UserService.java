package cn.edu.xmu.wwf.opus.userservice.service;

import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnNo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import cn.edu.xmu.wwf.opus.userservice.dao.UserDao;
import cn.edu.xmu.wwf.opus.userservice.model.po.UserPo;
import cn.edu.xmu.wwf.opus.userservice.model.vo.GetUserRetVo;
import cn.edu.xmu.wwf.opus.userservice.model.vo.UserLoginRetVo;
import cn.edu.xmu.wwf.opus.userservice.model.vo.UserLoginVo;
import cn.edu.xmu.wwf.opus.userservice.model.vo.UserRegisterVo;
import cn.edu.xmu.wwf.opus.userservice.util.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    TokenUtil tokenUtil;

    public ReturnObject checkUserLogin(UserLoginVo userLoginVo, HttpServletResponse httpServletResponse){
        UserPo userPo=userDao.getUserByUsernameFromDB(userLoginVo.getUsername());
        if(userPo==null){
            return new ReturnObject(ReturnNo.RESOURCE_NOT_FOUND,"该用户不存在");
        }
        if(!userLoginVo.getPassword().equals(userPo.getPassword())){
            return new ReturnObject(ReturnNo.FORBIDDEN,"密码错误");
        }
        String token=generateToken(userPo);
        ResponseCookie cookie = ResponseCookie.from("token",token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("None")
                .build();
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE,cookie.toString());
        return new ReturnObject(new UserLoginRetVo(true,token));
    }
    public String generateToken(UserPo userPo){
        return tokenUtil.generateToken(userPo);
    }
    public ReturnObject registerNewUser(UserRegisterVo userRegisterVo){
        return new ReturnObject(userDao.addUserIntoDB(userRegisterVo));
    }
    public ReturnObject getUserInfo(int id){
        GetUserRetVo getUserRetVo=new GetUserRetVo();
        UserPo userPo=userDao.getUserByPrimaryId(id);
        BeanUtils.copyProperties(userPo,getUserRetVo);
        return new ReturnObject(getUserRetVo);
    }
}
