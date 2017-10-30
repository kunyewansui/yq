package com.xiaosuokeji.server.service.intf.image;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.server.model.image.ImageCategory;

import java.util.List;

/**
 * 图片分类Service
 * Created by xuxiaowei on 2017/10/23.
 */
public interface ImageCategoryService {

    void save(ImageCategory imageCategory) throws XSBusinessException;

    void remove(ImageCategory imageCategory) throws XSBusinessException;

    void update(ImageCategory imageCategory) throws XSBusinessException;

    void updateLock(ImageCategory imageCategory) throws XSBusinessException;

    ImageCategory get(ImageCategory imageCategory) throws XSBusinessException;

    List tree(ImageCategory imageCategory);
}
