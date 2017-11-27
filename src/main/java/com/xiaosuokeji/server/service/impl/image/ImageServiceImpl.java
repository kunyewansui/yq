package com.xiaosuokeji.server.service.impl.image;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.framework.util.XSUuidUtil;
import com.xiaosuokeji.server.constant.image.ImageConsts;
import com.xiaosuokeji.server.dao.image.ImageCategoryDao;
import com.xiaosuokeji.server.dao.image.ImageDao;
import com.xiaosuokeji.server.model.image.Image;
import com.xiaosuokeji.server.model.image.ImageCategory;
import com.xiaosuokeji.server.service.intf.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 图片ServiceImpl
 * Created by xuxiaowei on 2017/10/26.
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ImageCategoryDao imageCategoryDao;

    @Override
    public void save(Image image) {
        image.setId(XSUuidUtil.generate());
        imageDao.save(image);
    }

    @Override
    public void remove(Image image) throws XSBusinessException {
        Image existent = get(image);
        imageDao.remove(existent);
    }

    @Override
    public void update(Image image) throws XSBusinessException {
        get(image);
        imageDao.update(image);
    }

    @Override
    public Image get(Image image) throws XSBusinessException {
        Image existent = imageDao.get(image);
        if (existent == null) {
            throw new XSBusinessException(ImageConsts.IMAGE_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public XSPageModel<Image> listAndCount(Image image) {
        image.setDefaultSort("create_time", "DESC");
        if (image.getCategory() != null && image.getCategory().getId() != null) {
            List<ImageCategory> list = imageCategoryDao.listCombo(new ImageCategory());
            Map<String, ImageCategory> map = XSTreeUtil.buildTree(list);
            List<ImageCategory> subTreeList = XSTreeUtil.listSubTree(map.get(image.getCategory().getId()));
            image.setCategoryList(subTreeList);
        }
        return XSPageModel.build(imageDao.list(image), imageDao.count(image));
    }

    @Override
    public List<Image> listCombo(Image image) {
        image.setDefaultSort("seq", "DESC");
        return imageDao.listCombo(image);
    }
}
