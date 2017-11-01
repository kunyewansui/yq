package com.xiaosuokeji.server.controller.content;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.annotation.XSPagination;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.manager.intf.GsonService;
import com.xiaosuokeji.server.model.article.Article;
import com.xiaosuokeji.server.model.article.ArticleCategory;
import com.xiaosuokeji.server.service.intf.article.ArticleCategoryService;
import com.xiaosuokeji.server.service.intf.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    private GsonService gsonService;

    //region Admin

    @RequestMapping(value = "/admin/content/article/article", method = RequestMethod.GET)
    @XSPagination
    public String index(HttpServletRequest request, Model model, Article article) {
        model.addAttribute("search", article);
        model.addAttribute("pageModel", articleService.listAndCount(article));
        model.addAttribute("categoryTree", gsonService.toJson(articleCategoryService.tree(new ArticleCategory())));
        return "admin/content/article";
    }

    @RequestMapping(value = "/admin/content/article/article/create", method = RequestMethod.GET)
    public String create(Model model, HttpServletRequest request) {
        model.addAttribute("backUrl", request.getHeader("Referer"));
        model.addAttribute("categoryTree", gsonService.toJson(articleCategoryService.tree(new ArticleCategory())));
        return "admin/content/articleCreate";
    }

    @RequestMapping(value = "/admin/content/article/article/update", method = RequestMethod.GET)
    public String update(Model model, HttpServletRequest request, Article article) {
        model.addAttribute("backUrl", request.getHeader("Referer"));
        try {
            model.addAttribute("article", articleService.get(article));
        } catch (XSBusinessException e) {
            model.addAttribute("article", null);
        }
        model.addAttribute("categoryTree", gsonService.toJson(articleCategoryService.tree(new ArticleCategory())));
        return "admin/content/articleUpdate";
    }

    @RequestMapping(value = "/admin/content/article/article/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(Article.Save.class) Article article) throws XSBusinessException {
        articleService.save(article);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/content/article/article/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(Article article) throws XSBusinessException {
        articleService.remove(article);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/content/article/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(Article.Update.class) Article article) throws XSBusinessException {
        articleService.update(article);
        return XSServiceResult.build();
    }

    //endregion


}
