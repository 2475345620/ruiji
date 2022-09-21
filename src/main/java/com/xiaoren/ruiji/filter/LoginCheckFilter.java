package com.xiaoren.ruiji.filter;

import com.alibaba.fastjson.JSON;
import com.xiaoren.ruiji.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginCheckFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       if (request.getSession().getAttribute("employee")==null){
           response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
           log.info("拦截");
           return false;
       }
       else {

           return true;

       }
//        return true;
    }
}
