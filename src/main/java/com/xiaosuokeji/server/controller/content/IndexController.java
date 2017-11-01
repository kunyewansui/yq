package com.xiaosuokeji.server.controller.content;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by gustinlau on 11/1/17.
 */
@Controller
@XSLog
@XSExceptionHandler
public class IndexController {

    @RequestMapping(value = "/admin/content", method = RequestMethod.GET)
    public String index() {
        return "admin/content/index";
    }
}
