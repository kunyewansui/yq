package com.xiaosuokeji.server.service.image.impl;

import com.xiaosuokeji.server.dao.image.ImageCategoryDao;
import com.xiaosuokeji.server.model.image.ImageCategory;
import com.xiaosuokeji.server.service.image.intf.ImageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图片分类ServiceImpl<br/>
 * Created by xuxiaowei on 2017/10/23.
 */
@Service
public class ImageCategoryServiceImpl implements ImageCategoryService {

    @Autowired
    private ImageCategoryDao imageCategoryDao;

    @Override
    public void save(ImageCategory imageCategory) {
        imageCategoryDao.save(imageCategory);
    }

    @Override
    public void remove(ImageCategory imageCategory) {
        imageCategoryDao.remove(imageCategory);
    }

    @Override
    public void update(ImageCategory imageCategory) {
        imageCategoryDao.update(imageCategory);
    }

    @Override
    public void updateChildren(ImageCategory imageCategory) {
        imageCategoryDao.updateChildren(imageCategory);
    }

    @Override
    public ImageCategory get(ImageCategory imageCategory) {
        return imageCategoryDao.get(imageCategory);
    }

    @Override
    public List<ImageCategory> listCombo(ImageCategory imageCategory) {
        return imageCategoryDao.listCombo(imageCategory);
    }
}
