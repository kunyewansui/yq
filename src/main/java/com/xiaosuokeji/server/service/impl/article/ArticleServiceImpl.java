package com.xiaosuokeji.server.service.impl.article;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSUuidUtil;
import com.xiaosuokeji.server.constant.article.ArticleConsts;
import com.xiaosuokeji.server.dao.article.ArticleDao;
import com.xiaosuokeji.server.model.article.Article;
import com.xiaosuokeji.server.service.intf.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文章ServiceImpl
 * Created by xuxiaowei on 2017/10/28.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    
    @Autowired
    private ArticleDao articleDao;

    @Override
    public void save(Article article) {
        article.setId(XSUuidUtil.generate());
        articleDao.save(article);
    }

    @Override
    public void remove(Article article) {
        articleDao.remove(article);
    }

    @Override
    public void update(Article article) {
        articleDao.update(article);
    }

    @Override
    public Article get(Article article) throws XSBusinessException {
        Article existent = articleDao.get(article);
        if (existent == null) {
            throw new XSBusinessException(ArticleConsts.ARTICLE_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public XSPageModel<Article> listAndCount(Article article) {
        article.setDefaultSort("create_time", "DESC");
        return XSPageModel.build(articleDao.list(article), articleDao.count(article));
    }

    @Override
    public List<Article> listCombo(Article article) {
        article.setDefaultSort("seq", "DESC");
        return articleDao.listCombo(article);
    }
}
