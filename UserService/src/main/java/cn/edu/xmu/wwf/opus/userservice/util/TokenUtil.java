package cn.edu.xmu.wwf.opus.userservice.util;

import cn.edu.xmu.wwf.opus.userservice.model.po.UserPo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Component
public class TokenUtil{

    /**
     * 生成token
     * @param userPo
     * @return
     */
    public String generateToken(UserPo userPo) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String id = String.valueOf(userPo.getId());
        String token = JWT.create()
                .withAudience(id)
                .withIssuedAt(start)
                .withExpiresAt(end)
                .sign(Algorithm.HMAC256(userPo.getPassword()));
        return token;
    }


    /**
     *
     * @param token
     * @return userId
     * 获取制定token中某个属性值
     */
    public static String get(String token) {
        List<String> list= JWT.decode(token).getAudience();
        String userId = JWT.decode(token).getAudience().get(0);
        return userId;
    }

    /**
     * 获取request
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }


    /**
     *
     * @param request
     * @return
     * 获取token
     */
    public String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie c :
                cookies) {
            if (c.getName().equals("token")) {
                return c.getValue();
            }
        }
        return null;
    }
}

