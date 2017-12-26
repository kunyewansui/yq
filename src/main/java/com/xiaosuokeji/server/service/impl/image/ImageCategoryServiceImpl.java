package com.xiaosuokeji.server.service.impl.image;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.framework.util.XSUuidUtil;
import com.xiaosuokeji.server.constant.image.ImageCategoryConsts;
import com.xiaosuokeji.server.dao.image.ImageCategoryDao;
import com.xiaosuokeji.server.dao.image.ImageDao;
import com.xiaosuokeji.server.model.image.Image;
import com.xiaosuokeji.server.model.image.ImageCategory;
import com.xiaosuokeji.server.service.intf.image.ImageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 图片分类ServiceImpl
 * Created by xuxiaowei on 2017/10/23.
 */
@Service
public class ImageCategoryServiceImpl implements ImageCategoryService {

    @Autowired
    private ImageCategoryDao imageCategoryDao;

    @Autowired
    private ImageDao imageDao;

    @Override
    public void save(ImageCategory imageCategory) throws XSBusinessException {
        ImageCategory criteria = new ImageCategory();
        criteria.setKey(imageCategory.getKey());
        Long count = imageCategoryDao.count(criteria);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(ImageCategoryConsts.IMAGE_CATEGORY_EXIST);
        }
        //父级隐藏则子级必须隐藏，父级锁定则子级必须锁定
        if (imageCategory.getParent() != null) {
            ImageCategory parent = imageCategoryDao.get(imageCategory.getParent());
            if (parent != null) {
                if (parent.getDisplay().equals(0)) {
                    imageCategory.setDisplay(0);
                }
                if (parent.getLock().equals(1)) {
                    imageCategory.setLock(1);
                }
            }
        }
        imageCategory.setId(XSUuidUtil.generate());
        imageCategoryDao.save(imageCategory);
    }

    @Override
    public void remove(ImageCategory imageCategory) throws XSBusinessException {
        ImageCategory existent = get(imageCategory);
        if (existent.getLock().equals(1)) {
            throw new XSBusinessException(ImageCategoryConsts.IMAGE_CATEGORY_LOCKED);
        }
        //校验该分类下是否存在子级
        ImageCategory children = new ImageCategory();
        children.setParent(existent);
        Long childrenCount = imageCategoryDao.count(children);
        if (childrenCount.compareTo(0L) > 0) {
            throw new XSBusinessException(ImageCategoryConsts.IMAGE_CATEGORY_USED);
        }
        //校验该分类下是否存在图片
        Image image = new Image();
        image.setCategory(existent);
        Long imageCount = imageDao.count(image);
        if (imageCount.compareTo(0L) > 0) {
            throw new XSBusinessException(ImageCategoryConsts.IMAGE_CATEGORY_USED);
        }
        imageCategoryDao.remove(existent);
    }

    @Override
    @Transactional
    public void update(ImageCategory imageCategory) throws XSBusinessException {
        ImageCategory existent = get(imageCategory);
        if (imageCategory.getKey() != null) {
            ImageCategory criteria = new ImageCategory();
            criteria.setKey(imageCategory.getKey());
            List<ImageCategory> existents = imageCategoryDao.list(criteria);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(existent.getId());
                if (!isSelf) {
                    throw new XSBusinessException(ImageCategoryConsts.IMAGE_CATEGORY_EXIST);
                }
            }
        }
        //不能选择自己或自己的下级作为父级
        List<ImageCategory> list = imageCategoryDao.listCombo(new ImageCategory());
        Map<String, ImageCategory> map = XSTreeUtil.buildTree(list);
        List<ImageCategory> subList = XSTreeUtil.listSubTree(map.get(imageCategory.getId()));
        if(imageCategory.getParent().getId() != null){
            boolean isSelfSub = subList.stream().anyMatch(s -> s.getId().equals(imageCategory.getParent().getId()));
            if(isSelfSub){
                throw new XSBusinessException(ImageCategoryConsts.IMAGE_CATEGORY_NOT_SELF_OR_SUB);
            }
        }
        imageCategoryDao.update(imageCategory);
        if (imageCategory.getDisplay() != null) {
            ImageCategory latest = new ImageCategory();
            latest.setDisplay(imageCategory.getDisplay());
            //取消展示则所有子级也取消，开启展示则直属父级和所有子级也开启
            if (imageCategory.getDisplay().equals(0)) {
                List<ImageCategory> subTreeList = XSTreeUtil.listSubTree(map.get(imageCategory.getId()));
                latest.setList(subTreeList);
            }
            else {
                List<ImageCategory> treePath = XSTreeUtil.getTreePath(map, map.get(imageCategory.getId()));
                latest.setList(treePath);
                List<ImageCategory> subTreeList = XSTreeUtil.listSubTree(map.get(imageCategory.getId()));
                latest.getList().addAll(subTreeList);
            }
            imageCategoryDao.batchUpdate(latest);
        }
    }

    @Override
    public void updateLock(ImageCategory imageCategory) throws XSBusinessException {
        ImageCategory existent = get(imageCategory);
        List<ImageCategory> list = imageCategoryDao.listCombo(new ImageCategory());
        Map<String, ImageCategory> map = XSTreeUtil.buildTree(list);
        //解锁则所有子级也解锁，锁定则直属父级和所有子级也锁定
        ImageCategory latest = new ImageCategory();
        if (existent.getLock().equals(0)) {
            latest.setLock(1);
            List<ImageCategory> treePath = XSTreeUtil.getTreePath(map, map.get(imageCategory.getId()));
            latest.setList(treePath);
            List<ImageCategory> subTreeList = XSTreeUtil.listSubTree(map.get(imageCategory.getId()));
            latest.getList().addAll(subTreeList);
        }
        else {
            latest.setLock(0);
            List<ImageCategory> subTreeList = XSTreeUtil.listSubTree(map.get(imageCategory.getId()));
            latest.setList(subTreeList);
        }
        imageCategoryDao.batchUpdateLock(latest);
    }

    @Override
    public ImageCategory get(ImageCategory imageCategory) throws XSBusinessException {
        ImageCategory existent = imageCategoryDao.get(imageCategory);
        if (existent == null) {
            throw new XSBusinessException(ImageCategoryConsts.IMAGE_CATEGORY_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public List tree(ImageCategory imageCategory) {
        imageCategory.setDefaultSort("seq", "DESC");
        List<ImageCategory> list = imageCategoryDao.listCombo(imageCategory);
        XSTreeUtil.buildTree(list);
        return XSTreeUtil.getSubTrees(list, new ImageCategory(""));
    }
}
