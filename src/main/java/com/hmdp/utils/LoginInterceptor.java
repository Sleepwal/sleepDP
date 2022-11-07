package com.hmdp.utils;

import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @package: com.hmdp.utils
 * @className: LoginInterceptor
 * @author: SleepWalker
 * @description: TODO
 * @date: 21:04
 * @version: 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.判断是否需要拦截（ThreadLocal中是否有用户）
        if(UserHolder.getUser() == null) {
            //不存在就拦截
            response.setStatus(401);
            return false;
        }

        //存在就放行
        return true;
    }
}
