package cn.edu.xmu.wwf.opus.userservice.dao;

import cn.edu.xmu.wwf.opus.userservice.mapper.UserMapper;
import cn.edu.xmu.wwf.opus.userservice.model.po.UserPo;
import cn.edu.xmu.wwf.opus.userservice.model.vo.UserRegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    UserMapper userMapper;
    public UserPo getUserByUsernameFromDB(String username){
        return userMapper.selectUserPoByUsername(username);
    }
    public UserPo addUserIntoDB(UserRegisterVo userRegisterVo){
        UserPo userPo=new UserPo();
        BeanUtils.copyProperties(userRegisterVo,userPo);
        userPo.setName(userRegisterVo.getUsername());
        userMapper.insertUserPo(userPo);
        return userPo;
    }
    public UserPo getUserByPrimaryId(int id){
        return userMapper.selectUserPoById(id);
    }
}
