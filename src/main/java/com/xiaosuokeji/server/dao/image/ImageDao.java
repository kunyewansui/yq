package com.xiaosuokeji.server.dao.image;

import com.xiaosuokeji.server.model.image.Image;

import java.util.List;

/**
 * 图片Dao
 * Created by xuxiaowei on 2017/10/25.
 */
public interface ImageDao {

    int save(Image image);

    int remove(Image image);

    int update(Image image);

    Image get(Image image);

    List<Image> list(Image image);

    List<Image> listCombo(Image image);

    Long count(Image image);
}
