package com.xiaosuokeji.server.controller.content.article;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.annotation.XSPagination;
import com.xiaosuokeji.server.model.article.Article;
import com.xiaosuokeji.server.model.article.ArticleCategory;
import com.xiaosuokeji.server.service.intf.article.ArticleCategoryService;
import com.xiaosuokeji.server.service.intf.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 文章管理
 * Created by gustinlau on 10/30/17.
 */
@Controller
@XSLog
@XSExceptionHandler
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleCategoryService articleCategoryService;

    //region Admin
    @RequestMapping(value = "/admin/content/article/index", method = RequestMethod.GET)
    @XSPagination
    public String index(HttpServletRequest request, Model model, Article article) {
        model.addAttribute("pageModel", articleService.listAndCount(article));
        model.addAttribute("categoryTree", articleCategoryService.tree(new ArticleCategory()));
        return "admin/content/article";
    }

    //endregion
}
