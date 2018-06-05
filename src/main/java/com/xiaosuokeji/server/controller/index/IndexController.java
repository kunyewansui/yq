package com.xiaosuokeji.server.controller.index;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.server.model.article.Article;
import com.xiaosuokeji.server.model.article.ArticleCategory;
import com.xiaosuokeji.server.service.article.ArticleService;
import com.xiaosuokeji.server.service.merchant.MerchantService;
import com.xiaosuokeji.server.service.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * Created by gustinlau on 26/10/2017.
 */
@Controller("adminIndexController")
public class IndexController {

    @Autowired
    private StatisticsService statisticsService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MerchantService merchantService;

    @RequestMapping(value = "admin/index", method = RequestMethod.GET)
    public String index(Model model) throws ParseException, JsonProcessingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession()
                .getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContextImpl == null) {
            return "admin/login";
        }

        model.addAttribute("result", statisticsService.orderFlow());
        model.addAllAttributes(statisticsService.statisticsCount());

        List<Article> artlist;
        ArticleCategory cate = new ArticleCategory();
        cate.setKey("notice_board");
        Article article = new Article();
        article.setCategory(cate);
        article.setDisplay(1);
        article.setOffset(0L);
        article.setLimit(1L);
        artlist = articleService.list(article);
        model.addAttribute("noticeBoard", artlist.size()==0?null:artlist.get(0));

        model.addAttribute("messages", merchantService.debtList());
        return "admin/index";
    }

}
