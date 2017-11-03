package com.xiaosuokeji.server.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gustinlau on 05/09/2017.
 */
@Controller
@RequestMapping("/admin/error")
public class ErrorController {

    @RequestMapping("/403")
    public String deny(Model model){
        model.addAttribute("code",403);
        model.addAttribute("msg","您的访问被拒绝！");
        return "admin/common/error";
    }

    @RequestMapping("/404")
    public String notFound(Model model){
        model.addAttribute("code",404);
        model.addAttribute("msg","页面不存在或已被删除！");
        return "admin/common/error";
    }

    @RequestMapping("/500")
    public String inner(Model model){
        model.addAttribute("code",500);
        model.addAttribute("msg","服务器内部发生错误！");
        return "admin/common/error";
    }
}
