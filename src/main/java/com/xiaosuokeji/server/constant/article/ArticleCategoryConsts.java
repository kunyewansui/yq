package com.xiaosuokeji.server.constant.article;

import com.xiaosuokeji.framework.model.XSStatusPair;

/**
 * 文章分类Consts
 * Created by xuxiaowei on 2017/10/28.
 */
public class ArticleCategoryConsts {

    public static XSStatusPair ARTICLE_CATEGORY_EXIST = XSStatusPair.build(10300, "文章分类已存在");

    public static XSStatusPair ARTICLE_CATEGORY_LOCKED = XSStatusPair.build(10301, "文章分类被锁定");

    public static XSStatusPair ARTICLE_CATEGORY_USED = XSStatusPair.build(10302, "文章分类被使用");

    public static XSStatusPair ARTICLE_CATEGORY_NOT_EXIST = XSStatusPair.build(10303, "文章分类不存在");

    public static XSStatusPair ARTICLE_CATEGORY_NOT_SELF_OR_SUB = XSStatusPair.build(10004, "不能选择自己或自己的下级作为父级");

}
