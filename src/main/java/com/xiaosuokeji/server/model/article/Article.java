package com.xiaosuokeji.server.model.article;

import com.xiaosuokeji.server.model.base.BaseModel;
import com.xiaosuokeji.server.model.image.Image;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * 文章
 * Created by xuxiaowei on 2017/10/28.
 */
public class Article extends BaseModel {

    /**
     * id
     */
    private String id;

    /**
     * 标题
     */
    @NotNull(message = "标题不能为空", groups = Save.class)
    @Length(min = 1, max = 255, message = "标题长度为1-255个字符", groups = {Save.class, Update.class})
    private String title;

    /**
     * 类型，0富文本，1链接
     */
    private Integer type;

    /**
     * 内容
     */
    @Length(message = "标题长度最多为2147483647个字符", groups = {Save.class, Update.class})
    private String content;

    /**
     * url
     */
    @URL(message = "url格式错误", groups = {Save.class, Update.class})
    @Length(max = 255, message = "url长度最多为255个字符", groups = {Save.class, Update.class})
    private String url;

    /**
     * 图片链接
     */
    @URL(message = "图片链接格式错误", groups = {Save.class, Update.class})
    @Length(max = 255, message = "图片链接长度最多为255个字符", groups = {Save.class, Update.class})
    private String image;

    /**
     * 分类
     */
    private ArticleCategory category;

    /**
     * 顺序
     */
    @NotNull(message = "顺序不能为空", groups = Save.class)
    private Integer seq;

    /**
     * 展示，0否，1是
     */
    private Integer display;

    /**
     * 锁定，0否，1是
     */
    private Integer lock=0;

    public interface Save {}

    public interface Update {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArticleCategory getCategory() {
        return category;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }
}
