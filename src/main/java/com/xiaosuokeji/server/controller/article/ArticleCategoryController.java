package com.xiaosuokeji.server.controller.article;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSServiceResult;
import com.xiaosuokeji.server.model.article.ArticleCategory;
import com.xiaosuokeji.server.service.intf.article.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文章分类
 * Created by gustinlau on 10/30/17.
 */
@Controller
@XSLog
@XSExceptionHandler
public class ArticleCategoryController {
    @Autowired
    private ArticleCategoryService articleCategoryService;

    //region Admin
    @RequestMapping(value = "/admin/content/article/category", method = RequestMethod.GET)
    public String index() {
        return "admin/content/articleCategory";
    }

    @RequestMapping(value = "/admin/content/article/category/tree", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminTree(ArticleCategory articleCategory) {
        articleCategory = articleCategory == null ? new ArticleCategory() : articleCategory;
        return XSServiceResult.build().data(articleCategoryService.tree(articleCategory));
    }

    @RequestMapping(value = "/admin/content/article/category/get", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminGet(ArticleCategory articleCategory) throws XSBusinessException {
        return XSServiceResult.build().data(articleCategoryService.get(articleCategory));
    }

    @RequestMapping(value = "/admin/content/article/category/save", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminSave(@Validated(ArticleCategory.Save.class) ArticleCategory articleCategory) throws XSBusinessException {
        articleCategoryService.save(articleCategory);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/content/article/category/remove", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminRemove(ArticleCategory articleCategory) throws XSBusinessException {
        articleCategoryService.remove(articleCategory);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/content/article/category/update", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminUpdate(@Validated(ArticleCategory.Update.class) ArticleCategory articleCategory)
            throws XSBusinessException {
        if (articleCategory.getParent() == null)
            articleCategory.setParent(new ArticleCategory());
        articleCategoryService.update(articleCategory);
        return XSServiceResult.build();
    }

    @RequestMapping(value = "/admin/content/article/category/lock", method = RequestMethod.POST)
    @ResponseBody
    public XSServiceResult adminLock( ArticleCategory articleCategory)
            throws XSBusinessException {
        articleCategoryService.updateLock(articleCategory);
        return XSServiceResult.build();
    }
    //endregion
}
