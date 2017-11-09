package com.xiaosuokeji.server.manager.security;

import com.xiaosuokeji.framework.constant.XSStatusConstant;
import com.xiaosuokeji.framework.util.XSIpUtil;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.model.security.SecStaffLog;
import com.xiaosuokeji.server.service.intf.security.SecStaffLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 系统用户登录成功处理器
 * Created by xuxiaowei on 2017/10/27.
 */
@Service("authenticationSuccessHandler")
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    private SecStaffLogService secStaffLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String successMsg = "{\"status\":true,\"code\":" + String.valueOf(XSStatusConstant.SUCCESS.getCode()) + "}";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(successMsg);
        //记录成功登录日志
        SecStaffLog latest = new SecStaffLog();
        latest.setStaff((SecStaff) authentication.getPrincipal());
        latest.setIp(XSIpUtil.getRequestIp(request));
        latest.setTime(new Date());
        latest.setOperation("登录系统");
        latest.setStatus(1);
        latest.setParameters("");
        latest.setResult("");
        secStaffLogService.save(latest);
    }
}
