package com.xiaosuokeji.server.controller.marketing;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.marketing.Feedback;
import com.xiaosuokeji.server.service.intf.marketing.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "/admin/marketing/feedback", method = RequestMethod.GET)
    public String index(Model model,HttpServletRequest request, Feedback feedback) {
        model.addAttribute("search", feedback);
        model.addAttribute("pageModel", feedbackService.listAndCount(feedback));
        return "admin/marketing/feedback";
    }


    @RequestMapping(value = "/admin/marketing/feedback/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(Feedback feedback) throws XSBusinessException {
        feedbackService.remove(feedback);
        return XSServiceResult.build();
    }

}
