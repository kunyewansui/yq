package com.xiaosuokeji.server.manager.security;

import com.xiaosuokeji.framework.constant.XSStatusConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统用户登出成功处理器
 * Created by xuxiaowei on 2017/10/27.
 */
@Service("logoutSuccessHandler")
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        String successMsg = "{\"status\":true,\"code\":" + String.valueOf(XSStatusConstant.SUCCESS.getCode()) + "}";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(successMsg);
    }
}
