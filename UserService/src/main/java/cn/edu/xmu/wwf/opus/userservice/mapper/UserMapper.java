package cn.edu.xmu.wwf.opus.userservice.mapper;

import cn.edu.xmu.wwf.opus.userservice.model.po.UserPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserPo selectUserPoByUsername(String username);
    int insertUserPo(UserPo userPo);
    UserPo selectUserPoById(int id);
}
