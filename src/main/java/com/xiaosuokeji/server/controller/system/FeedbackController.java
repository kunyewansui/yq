package com.xiaosuokeji.server.controller.system;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.annotation.XSPagination;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.system.Feedback;
import com.xiaosuokeji.server.model.system.Version;
import com.xiaosuokeji.server.service.intf.system.FeedbackService;
import com.xiaosuokeji.server.service.intf.system.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gustinlau on 11/1/17.
 */
@Controller
@XSLog
@XSExceptionHandler
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @RequestMapping(value = "/admin/system/feedback", method = RequestMethod.GET)
    @XSPagination
    public String index(Model model,HttpServletRequest request, Feedback feedback) {
        model.addAttribute("search", feedback);
        model.addAttribute("pageModel", feedbackService.listAndCount(feedback));
        return "admin/system/feedback";
    }


    @RequestMapping(value = "/admin/system/feedback/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(Feedback feedback) throws XSBusinessException {
        feedbackService.remove(feedback);
        return XSServiceResult.build();
    }

}
