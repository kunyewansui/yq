package com.xiaosuokeji.server.dao.article;

import com.xiaosuokeji.server.model.article.Article;

import java.util.List;

/**
 * 文章Dao
 * Created by xuxiaowei on 2017/10/28.
 */
public interface ArticleDao {

    int save(Article article);

    int remove(Article article);

    int update(Article article);

    Article get(Article article);

    List<Article> list(Article article);

    List<Article> listCombo(Article article);

    Long count(Article article);
}
