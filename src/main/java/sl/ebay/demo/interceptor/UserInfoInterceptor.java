package sl.ebay.demo.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sl.ebay.demo.common.Constants;
import sl.ebay.demo.common.UserIdThreadLocalUtil;
import sl.ebay.demo.pojo.AuthenticationInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Base64;

public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(Constants.REQUEST_HEADER_AUTHENTICATION_INFO);
        // 获取并设置UserId
        if (!Strings.isNullOrEmpty(header)) {
            byte[] decodeInfo = Base64.getDecoder().decode(header);
            String decodeAuthenticationInfo = new String(decodeInfo);
            AuthenticationInfo authenticationInfo = new ObjectMapper().readValue(decodeAuthenticationInfo,
                    AuthenticationInfo.class);
            UserIdThreadLocalUtil.USER_ID_THREAD_LOCAL.set(authenticationInfo);
            // 打印本次谁访问了那个接口,这里因为是demo 简单实用system.out打印
            System.out.println(LocalDateTime.now() + " " + authenticationInfo + " " + request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserIdThreadLocalUtil.USER_ID_THREAD_LOCAL.remove();
    }
}
