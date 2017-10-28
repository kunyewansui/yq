package com.xiaosuokeji.server.service.intf.article;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.article.Article;

import java.util.List;

/**
 * 文章Service
 * Created by xuxiaowei on 2017/10/28.
 */
public interface ArticleService {

    void save(Article article);

    void remove(Article article);

    void update(Article article);

    Article get(Article article) throws XSBusinessException;

    XSPageModel<Article> listAndCount(Article article);

    List<Article> listCombo(Article article);
}
