package com.xiaosuokeji.server.controller.index;

import com.xiaosuokeji.framework.annotation.XSLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gustinlau on 26/10/2017.
 */
@Controller("adminLoginController")
@XSLog
public class LoginController {

    @RequestMapping("admin/login")
    public String loginPage() {
        return "admin/login";
    }

    @RequestMapping(value = "admin/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        return "admin/index";
    }
}
