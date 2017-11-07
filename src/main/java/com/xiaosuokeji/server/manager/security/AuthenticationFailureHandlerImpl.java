package com.xiaosuokeji.server.manager.security;

import com.xiaosuokeji.framework.constant.XSStatusConstant;
import com.xiaosuokeji.server.constant.security.SecStaffConsts;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统用户登录失败处理器
 * Created by xuxiaowei on 2017/10/27.
 */
@Service("authenticationFailureHandler")
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String exceptionMsg = exception.getMessage();
        Integer code = XSStatusConstant.FAILURE.getCode();
        String msg = XSStatusConstant.FAILURE.getMsg();

        if (exception instanceof UsernameNotFoundException) {
            if (exception.getMessage().equals(XSStatusConstant.FAILURE.getMsg())) {
                code = XSStatusConstant.FAILURE.getCode();
                msg = XSStatusConstant.FAILURE.getMsg();
            }
            else {
                code = SecStaffConsts.SEC_STAFF_NOT_EXIST.getCode();
                msg = SecStaffConsts.SEC_STAFF_NOT_EXIST.getMsg();
            }
        }
        else if (exception instanceof BadCredentialsException) {
            code = SecStaffConsts.SEC_STAFF_PASSWORD_ERROR.getCode();
            msg = SecStaffConsts.SEC_STAFF_PASSWORD_ERROR.getMsg();
        }
        else if (exception instanceof InternalAuthenticationServiceException) {
            code = SecStaffConsts.SEC_STAFF_NOT_ENABLE.getCode();
            msg = SecStaffConsts.SEC_STAFF_NOT_ENABLE.getMsg();
        }
        else if (exceptionMsg.contains("principal exceeded")) {
            code = SecStaffConsts.SEC_STAFF_CONCURRENT_BEYOND.getCode();
            msg = SecStaffConsts.SEC_STAFF_CONCURRENT_BEYOND.getMsg();
        }
        String errorMsg = "{\"status\":false,\"code\":" + code + ",\"msg\":\"" + msg + "\"}";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(errorMsg);
    }
}
