package cn.edu.xmu.wwf.opus.common.utils.jwt;

import com.auth0.jwt.JWT;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenDecodeUtil {
    public static String get(String token) {
        List<String> list= JWT.decode(token).getAudience();
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }
}

