package com.xiaosuokeji.server.constant.image;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 图片分类Consts
 * Created by xuxiaowei on 2017/10/23.
 */
public class ImageCategoryConsts {

    public static XSStatusPair IMAGE_CATEGORY_EXIST = XSStatusPair.build(10400, "图片分类已存在");

    public static XSStatusPair IMAGE_CATEGORY_LOCKED = XSStatusPair.build(10401, "图片分类被锁定");

    public static XSStatusPair IMAGE_CATEGORY_USED = XSStatusPair.build(10402, "图片分类被使用");

    public static XSStatusPair IMAGE_CATEGORY_NOT_EXIST = XSStatusPair.build(10403, "图片分类不存在");

    public static XSStatusPair IMAGE_CATEGORY_NOT_SELF_OR_SUB = XSStatusPair.build(10304, "不能选择自己或自己的下级作为父级");

}
