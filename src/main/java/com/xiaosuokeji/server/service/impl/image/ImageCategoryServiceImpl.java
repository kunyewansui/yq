package com.xiaosuokeji.server.service.impl.image;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.intf.XSTreeable;
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
        ImageCategory existent = new ImageCategory();
        existent.setKey(imageCategory.getKey());
        Long count = imageCategoryDao.count(existent);
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
        get(imageCategory);
        if (imageCategory.getKey() != null) {
            ImageCategory existent = new ImageCategory();
            existent.setKey(imageCategory.getKey());
            List<ImageCategory> existents = imageCategoryDao.list(existent);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(imageCategory.getId());
                if (!isSelf) {
                    throw new XSBusinessException(ImageCategoryConsts.IMAGE_CATEGORY_EXIST);
                }
            }
        }
        imageCategoryDao.update(imageCategory);
        if (imageCategory.getDisplay() != null) {
            List<ImageCategory> list = imageCategoryDao.listCombo(new ImageCategory());
            Map<String, XSTreeable<String>> map = XSTreeUtil.buildTree(list);
            ImageCategory existent = new ImageCategory();
            existent.setDisplay(imageCategory.getDisplay());
            //取消展示则所有子级也取消，开启展示则所有父级也开启
            if (imageCategory.getDisplay().equals(0)) {
                List<XSTreeable<String>> subTreeList = XSTreeUtil.listSubTree(map.get(imageCategory.getId()));
                existent.setList(subTreeList);
            }
            else {
                List<XSTreeable<String>> treePath = XSTreeUtil.getTreePath(map, map.get(imageCategory.getId()));
                existent.setList(treePath);
            }
            imageCategoryDao.batchUpdate(existent);
        }
    }

    @Override
    public void updateLock(ImageCategory imageCategory) throws XSBusinessException {
        ImageCategory existent = get(imageCategory);
        List<ImageCategory> list = imageCategoryDao.listCombo(new ImageCategory());
        Map<String, XSTreeable<String>> map = XSTreeUtil.buildTree(list);
        //解锁则所有子级也解锁，锁定则所有父级也锁定
        ImageCategory latest = new ImageCategory();
        if (existent.getLock().equals(0)) {
            latest.setLock(1);
            List<XSTreeable<String>> treePath = XSTreeUtil.getTreePath(map, map.get(imageCategory.getId()));
            latest.setList(treePath);
        }
        else {
            latest.setLock(0);
            List<XSTreeable<String>> subTreeList = XSTreeUtil.listSubTree(map.get(imageCategory.getId()));
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
        return XSTreeUtil.getSubTrees(list, imageCategory.getParent());
    }
}
