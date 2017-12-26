package com.xiaosuokeji.server.service.impl.article;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.framework.util.XSUuidUtil;
import com.xiaosuokeji.server.constant.article.ArticleCategoryConsts;
import com.xiaosuokeji.server.dao.article.ArticleCategoryDao;
import com.xiaosuokeji.server.dao.article.ArticleDao;
import com.xiaosuokeji.server.model.article.Article;
import com.xiaosuokeji.server.model.article.ArticleCategory;
import com.xiaosuokeji.server.service.intf.article.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 文章分类ServiceImpl
 * Created by xuxiaowei on 2017/10/28.
 */
@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

    @Autowired
    private ArticleCategoryDao articleCategoryDao;

    @Autowired
    private ArticleDao articleDao;

    @Override
    public void save(ArticleCategory articleCategory) throws XSBusinessException {
        ArticleCategory existent = new ArticleCategory();
        existent.setKey(articleCategory.getKey());
        Long count = articleCategoryDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(ArticleCategoryConsts.ARTICLE_CATEGORY_EXIST);
        }
        //展示及锁定状态必须与父级一致
        if (articleCategory.getParent() != null) {
            ArticleCategory parent = articleCategoryDao.get(articleCategory.getParent());
            if (parent != null) {
                articleCategory.setDisplay(parent.getDisplay());
                articleCategory.setLock(parent.getLock());
            }
        }
        else {
            articleCategory.setLock(0);
        }
        articleCategory.setId(XSUuidUtil.generate());
        articleCategoryDao.save(articleCategory);
    }

    @Override
    public void remove(ArticleCategory articleCategory) throws XSBusinessException {
        ArticleCategory existent = get(articleCategory);
        if (existent.getLock().equals(1)) {
            throw new XSBusinessException(ArticleCategoryConsts.ARTICLE_CATEGORY_LOCKED);
        }
        //校验该分类下是否存在子级
        existent = new ArticleCategory();
        existent.setParent(articleCategory);
        Long count = articleCategoryDao.count(existent);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(ArticleCategoryConsts.ARTICLE_CATEGORY_USED);
        }
        //校验该分类下是否存在图片
        Article article = new Article();
        article.setCategory(articleCategory);
        Long imageCount = articleDao.count(article);
        if (imageCount.compareTo(0L) > 0) {
            throw new XSBusinessException(ArticleCategoryConsts.ARTICLE_CATEGORY_USED);
        }
        articleCategoryDao.remove(articleCategory);
    }

    @Override
    @Transactional
    public void update(ArticleCategory articleCategory) throws XSBusinessException {
        if (articleCategory.getKey() != null) {
            ArticleCategory existent = new ArticleCategory();
            existent.setKey(articleCategory.getKey());
            List<ArticleCategory> existents = articleCategoryDao.list(existent);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(articleCategory.getId());
                if (!isSelf) {
                    throw new XSBusinessException(ArticleCategoryConsts.ARTICLE_CATEGORY_EXIST);
                }
            }
        }
        get(articleCategory);
        //不能选择自己或自己的下级作为父级
        List<ArticleCategory> list = articleCategoryDao.listCombo(new ArticleCategory());
        Map<String, ArticleCategory> map = XSTreeUtil.buildTree(list);
        List<ArticleCategory> subList = XSTreeUtil.listSubTree(map.get(articleCategory.getId()));
        if(articleCategory.getParent().getId() != null){
            boolean isSelfSub = subList.stream().anyMatch(s -> s.getId().equals(articleCategory.getParent().getId()));
            if(isSelfSub){
                throw new XSBusinessException(ArticleCategoryConsts.ARTICLE_CATEGORY_NOT_SELF_OR_SUB);
            }
        }
        articleCategoryDao.update(articleCategory);

        if (articleCategory.getDisplay() != null) {
            ArticleCategory existent = new ArticleCategory();
            existent.setDisplay(articleCategory.getDisplay());
            //取消展示则所有子级也取消，开启展示则所有父级也开启
            if (articleCategory.getDisplay().equals(0)) {
                List<ArticleCategory> subTreeList = XSTreeUtil.listSubTree(map.get(articleCategory.getId()));
                existent.setList(subTreeList);
            }
            else {
                List<ArticleCategory> treePath = XSTreeUtil.getTreePath(map, map.get(articleCategory.getId()));
                existent.setList(treePath);
            }
            articleCategoryDao.batchUpdate(existent);
        }
    }

    @Override
    public void updateLock(ArticleCategory articleCategory) throws XSBusinessException {
        ArticleCategory existent = get(articleCategory);
        List<ArticleCategory> list = articleCategoryDao.listCombo(new ArticleCategory());
        Map<String, ArticleCategory> map = XSTreeUtil.buildTree(list);
        //解锁则所有子级也解锁，锁定则所有父级也锁定
        ArticleCategory latest = new ArticleCategory();
        if (existent.getLock().equals(0)) {
            latest.setLock(1);
            List<ArticleCategory> treePath = XSTreeUtil.getTreePath(map, map.get(articleCategory.getId()));
            latest.setList(treePath);
        }
        else {
            latest.setLock(0);
            List<ArticleCategory> subTreeList = XSTreeUtil.listSubTree(map.get(articleCategory.getId()));
            latest.setList(subTreeList);
        }
        articleCategoryDao.batchUpdateLock(latest);
    }

    @Override
    public ArticleCategory get(ArticleCategory articleCategory) throws XSBusinessException {
        ArticleCategory existent = articleCategoryDao.get(articleCategory);
        if (existent == null) {
            throw new XSBusinessException(ArticleCategoryConsts.ARTICLE_CATEGORY_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public List tree(ArticleCategory articleCategory) {
        articleCategory.setDefaultSort("seq", "DESC");
        List<ArticleCategory> list = articleCategoryDao.listCombo(articleCategory);
        XSTreeUtil.buildTree(list);

        List list1 = XSTreeUtil.getSubTrees(list, new ArticleCategory(""));
        return list1;
    }
}
