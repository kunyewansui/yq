package com.xiaosuokeji.server.model.system;

import com.xiaosuokeji.framework.model.XSGenericModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by gustinlau on 11/1/17.
 */
public class Feedback extends XSGenericModel {

    private Long id;
    /**
     * 用户名
     */
    @Length(max = 255, message = "名称长度为1-255个字符", groups = Save.class)
    private String name;

    /**
     * 手机号
     */
    @Pattern(regexp = "^1[0-9]{10}$", message = "手机号格式为11位数字且第一位为1", groups = Save.class)
    private String mobile;

    /**
     * 标题
     */
    @NotNull(message = "标题不能为空", groups = Save.class)
    @Length(min = 1, max = 255, message = "名称长度为1-255个字符", groups = Save.class)
    private String title;
    /**
     * 内容
     */
    @Length( max = 500, message = "内容长度最多500个字符", groups = Save.class)
    private String content;

    public interface Save {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
