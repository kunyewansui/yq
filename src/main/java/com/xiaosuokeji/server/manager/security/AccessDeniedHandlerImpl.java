package com.xiaosuokeji.server.manager.security;

import com.xiaosuokeji.server.constant.security.SecStaffConsts;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 系统用户访问拒绝处理器
 * Created by xuxiaowei on 2017/10/27.
 */
@Service("accessDeniedHandler")
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ade)
            throws IOException, ServletException {
        if (request.getHeader("X-Requested-With") == null) {
            String referer = request.getHeader("Referer");
            if (referer == null || referer.endsWith("/admin/error/704")) {
                if (referer == null) {
                    response.sendRedirect(request.getContextPath() + "/admin/error/704");
                }
                else {
                    response.sendRedirect(request.getContextPath() + "/admin/login");
                }
            }
            else {
                response.setHeader("Referer", referer);
                response.sendRedirect(request.getContextPath() + "/admin/error/704");
            }
        }
        else {
            String errorMsg = "{\"status\":false,\"code\":" + SecStaffConsts.SEC_STAFF_ACCESS_DENY.getCode()
                    + ",\"msg\":\"" + SecStaffConsts.SEC_STAFF_ACCESS_DENY.getMsg() + "\"}";
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            try {
                response.getWriter().write(errorMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
