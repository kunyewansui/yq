package com.xiaosuokeji.server.dao.image;

import com.xiaosuokeji.server.model.image.ImageCategory;

import java.util.List;

/**
 * 图片分类Dao
 * Created by xuxiaowei on 2017/10/23.
 */
public interface ImageCategoryDao {

    int save(ImageCategory imageCategory);

    int remove(ImageCategory imageCategory);

    int update(ImageCategory imageCategory);

    int batchUpdate(ImageCategory imageCategory);

    int batchUpdateLock(ImageCategory imageCategory);

    ImageCategory get(ImageCategory imageCategory);

    List<ImageCategory> list(ImageCategory imageCategory);

    List<ImageCategory> listCombo(ImageCategory imageCategory);

    Long count(ImageCategory imageCategory);
}
