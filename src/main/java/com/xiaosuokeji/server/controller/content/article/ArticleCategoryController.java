package com.xiaosuokeji.server.controller.content.article;

import com.xiaosuokeji.framework.annotation.XSExceptionHandler;
import com.xiaosuokeji.framework.annotation.XSLog;
import com.xiaosuokeji.server.service.intf.article.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

    public String index(){

    }
}
