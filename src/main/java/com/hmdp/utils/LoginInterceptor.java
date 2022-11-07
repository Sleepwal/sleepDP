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
        //1.获取用户
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        //2.判断用户是否存在
        if(user == null) {
            //3.不存在就拦截
            response.setStatus(401);
            return false;
        }

        //4.存在就保存到ThreadLocal
        UserHolder.saveUser((UserDTO) user);

        //5.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
