package com.xiaosuokeji.server.controller.index;

import com.xiaosuokeji.framework.annotation.XSLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gustinlau on 26/10/2017.
 */
@Controller("adminLoginController")
@XSLog
public class LoginController {

    @RequestMapping("/login")
    public String loginPage() {
        return "admin/login";
    }
}
