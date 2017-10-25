package com.xiaosuokeji.server.model.image;

import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

/**
 * 图片
 * Created by xuxiaowei on 2017/10/25.
 */
public class Image extends BaseModel {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    @NotNull(message = "名称不能为空", groups = Save.class)
    @Length(min = 1, max = 255, message = "名称长度为1-255个字符", groups = {Save.class, Update.class})
    private String name;

    /**
     * url
     */
    @NotNull(message = "url不能为空", groups = Save.class)
    @URL(message = "url格式错误", groups = {Save.class, Update.class})
    @Length(min = 1, max = 255, message = "url长度为1-255个字符", groups = {Save.class, Update.class})
    private String url;

    /**
     * 链接
     */
    @URL(message = "链接格式错误", groups = {Save.class, Update.class})
    @Length(max = 255, message = "链接长度最多255个字符", groups = {Save.class, Update.class})
    private String link;

    /**
     * 分类
     */
    private ImageCategory category;

    /**
     * 顺序
     */
    @NotNull(message = "顺序不能为空", groups = Save.class)
    private Integer seq;

    /**
     * 展示，0否，1是
     */
    private Integer display;

    public interface Save{}

    public interface Update{}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ImageCategory getCategory() {
        return category;
    }

    public void setCategory(ImageCategory category) {
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
}
