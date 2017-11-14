package com.xiaosuokeji.server.service.impl.image;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSUuidUtil;
import com.xiaosuokeji.server.constant.image.ImageConsts;
import com.xiaosuokeji.server.dao.image.ImageDao;
import com.xiaosuokeji.server.model.image.Image;
import com.xiaosuokeji.server.service.intf.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图片ServiceImpl
 * Created by xuxiaowei on 2017/10/26.
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao imageDao;

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
        return XSPageModel.build(imageDao.list(image), imageDao.count(image));
    }

    @Override
    public List<Image> listCombo(Image image) {
        image.setDefaultSort("seq", "DESC");
        return imageDao.listCombo(image);
    }
}
