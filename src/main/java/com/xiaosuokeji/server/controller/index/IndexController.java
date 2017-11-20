package com.xiaosuokeji.server.controller.index;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页Controller
 * Created by gustinlau on 11/1/17.
 */
@Controller
public class IndexController {

    /**
     * 图文模块
     *
     * @return
     */
    @RequestMapping(value = "/admin/content", method = RequestMethod.GET)
    public String contentIndex() {
        return redirectUrl("admin/content/index");
    }

    /**
     * 营销模块
     *
     * @return
     */
    @RequestMapping(value = "/admin/marketing", method = RequestMethod.GET)
    public String marketingIndex() {
        return redirectUrl("admin/marketing/index");
    }

    /**
     * 系统模块
     *
     * @return
     */
    @RequestMapping(value = "/admin/system", method = RequestMethod.GET)
    public String systemIndex() {
        return redirectUrl("admin/system/index");
    }

    private String redirectUrl(String url) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                .getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContextImpl == null) {
            return "admin/login";
        }
        return url;
    }
}
