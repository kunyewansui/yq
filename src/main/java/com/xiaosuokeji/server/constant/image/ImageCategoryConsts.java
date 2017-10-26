package com.xiaosuokeji.server.constant.image;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 图片分类Constants
 * Created by xuxiaowei on 2017/10/23.
 */
public class ImageCategoryConsts {

    public static XSStatusPair IMAGE_CATEGORY_EXIST = XSStatusPair.build(10000, "图片分类已存在");

    public static XSStatusPair IMAGE_CATEGORY_LOCKED = XSStatusPair.build(10001, "图片分类被锁定");

    public static XSStatusPair IMAGE_CATEGORY_USED = XSStatusPair.build(10002, "图片分类被使用");

    public static XSStatusPair IMAGE_CATEGORY_NOT_EXIST = XSStatusPair.build(10003, "图片分类不存在");
}
