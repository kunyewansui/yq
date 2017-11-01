package com.xiaosuokeji.server.controller.index;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页Controller
 * Created by gustinlau on 11/1/17.
 */
@Controller
@XSLog
@XSExceptionHandler
public class IndexController {

    /**
     * 图文模块
     *
     * @return
     */
    @RequestMapping(value = "/admin/content", method = RequestMethod.GET)
    public String contentIndex() {
        return "admin/content/index";
    }

    /**
     * 系统模块
     *
     * @return
     */
    @RequestMapping(value = "/admin/system", method = RequestMethod.GET)
    public String systemIndex() {
        return "admin/system/index";
    }
}
