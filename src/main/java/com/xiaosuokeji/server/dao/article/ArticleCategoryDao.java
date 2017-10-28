package com.xiaosuokeji.server.dao.article;

import com.xiaosuokeji.server.model.article.ArticleCategory;

import java.util.List;

/**
 * 文章分类Dao
 * Created by xuxiaowei on 2017/10/28.
 */
public interface ArticleCategoryDao {

    int save(ArticleCategory articleCategory);

    int remove(ArticleCategory articleCategory);

    int update(ArticleCategory articleCategory);

    int batchUpdate(ArticleCategory articleCategory);

    int batchUpdateLock(ArticleCategory articleCategory);

    ArticleCategory get(ArticleCategory articleCategory);

    List<ArticleCategory> list(ArticleCategory articleCategory);

    List<ArticleCategory> listCombo(ArticleCategory articleCategory);

    Long count(ArticleCategory articleCategory);
}
