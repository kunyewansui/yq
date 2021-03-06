package com.xiaosuokeji.server.controller.index;

import com.xiaosuokeji.server.constant.security.SecStaffConsts;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gustinlau on 26/10/2017.
 */
@Controller("adminLoginController")
public class LoginController {

    @RequestMapping(value = {"admin",""})
    public String admin(HttpServletRequest request){
        if (request.getSession().getAttribute("SPRING_SECURITY_CONTEXT")==null) {
            return "redirect:/admin/login";
        } else {
            return "redirect:/admin/index";
        }
    }

    @RequestMapping("admin/login")
    public ModelAndView loginPage(HttpServletRequest request) throws IOException {
        if (request.getHeader("X-Requested-With") == null) {
            return new ModelAndView("admin/login");
        }
        else {
            MappingJackson2JsonView view = new MappingJackson2JsonView();
            Map<String, Object> result = new HashMap<>();
            result.put("status", false);
            result.put("code", SecStaffConsts.SEC_STAFF_SESSION_TIMEOUT.getCode());
            result.put("msg", SecStaffConsts.SEC_STAFF_SESSION_TIMEOUT.getMsg());
            view.setAttributesMap(result);
            return new ModelAndView(view);
        }
    }

}
