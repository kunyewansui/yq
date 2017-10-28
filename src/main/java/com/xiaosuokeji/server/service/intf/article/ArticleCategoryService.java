package com.xiaosuokeji.server.service.intf.article;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.server.model.article.ArticleCategory;

import java.util.List;

/**
 * 文章分类Service
 * Created by xuxiaowei on 2017/10/28.
 */
public interface ArticleCategoryService {

    void save(ArticleCategory articleCategory) throws XSBusinessException;

    void remove(ArticleCategory articleCategory) throws XSBusinessException;

    void update(ArticleCategory articleCategory) throws XSBusinessException;

    void updateLock(ArticleCategory articleCategory) throws XSBusinessException;

    ArticleCategory get(ArticleCategory articleCategory) throws XSBusinessException;

    List<ArticleCategory> tree(ArticleCategory articleCategory);
}
