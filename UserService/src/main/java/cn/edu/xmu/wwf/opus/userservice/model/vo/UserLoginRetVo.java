package cn.edu.xmu.wwf.opus.userservice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginRetVo {
    boolean state;
    String token;
}
