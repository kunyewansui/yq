package com.xiaosuokeji.server.service.image.intf;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.model.image.Image;

import java.util.List;

/**
 * 图片Service
 * Created by xuxiaowei on 2017/10/26.
 */
public interface ImageService {

    void save(Image image);

    void remove(Image image);

    void update(Image image);

    Image get(Image image) throws XSBusinessException;

    XSPageModel<Image> listAndCount(Image image);

    List<Image> listCombo(Image image);
}
