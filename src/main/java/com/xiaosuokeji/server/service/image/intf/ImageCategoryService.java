package com.xiaosuokeji.server.service.image.intf;

import com.xiaosuokeji.server.model.image.ImageCategory;

import java.util.List;

/**
 * 图片分类Service<br/>
 * Created by xuxiaowei on 2017/10/23.
 */
public interface ImageCategoryService {

    void save(ImageCategory imageCategory);

    void remove(ImageCategory imageCategory);

    void update(ImageCategory imageCategory);

    void updateChildren(ImageCategory imageCategory);

    ImageCategory get(ImageCategory imageCategory);

    List<ImageCategory> listCombo(ImageCategory imageCategory);
}
