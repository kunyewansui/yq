package com.xiaosuokeji.server.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * 保存URL到session中切面
 * Created by gustinlau on 08/11/2017.
 */
@Service
@Aspect
public class SaveURLAspect {

    @Before("@annotation(com.xiaosuokeji.server.annotation.SaveURL)")
    public void doBefore() throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String url = String.valueOf(request.getRequestURL());
        if (request.getQueryString() != null)
            url += "?" + request.getQueryString();
        session.setAttribute("redirectURL", URLEncoder.encode(url, "UTF-8"));
    }
}

