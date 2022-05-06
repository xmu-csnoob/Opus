package cn.edu.xmu.wwf.opus.userservice.interceptor;

import cn.edu.xmu.wwf.opus.userservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String token = tokenUtil.getToken(request);
        if (StringUtils.isEmpty(token)) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
