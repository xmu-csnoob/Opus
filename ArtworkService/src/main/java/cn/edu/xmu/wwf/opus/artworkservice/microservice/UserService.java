package cn.edu.xmu.wwf.opus.artworkservice.microservice;

import cn.edu.xmu.wwf.opus.artworkservice.microservice.model.user.GetUserInfoVo;
import cn.edu.xmu.wwf.opus.common.utils.ret.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="UserService")
public interface UserService {
    @GetMapping("/user/{id}")
    ReturnObject<GetUserInfoVo> getUserInfo(@PathVariable int id);
}
