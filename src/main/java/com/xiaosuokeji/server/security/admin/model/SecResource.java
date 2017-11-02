package com.xiaosuokeji.server.security.admin.model;

import com.xiaosuokeji.framework.annotation.XSAutoDesc;
import com.xiaosuokeji.framework.intf.XSTreeable;
import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统资源
 * Created by xuxiaowei on 2017/10/23.
 */
public class SecResource extends BaseModel implements XSTreeable<Long, SecResource> {

    /**
     * id
     */
    private Long id;

    /**
     * 键
     */
    @NotNull(message = "键不能为空", groups = Save.class)
    @Length(min = 1, max = 191, message = "键长度为1-191个字符", groups = {Save.class, Update.class})
    private String key;

    /**
     * 父级
     */
    private SecResource parent;

    /**
     * 类型，0菜单，1功能，2url
     */
    @XSAutoDesc("secResourceType")
    private Integer type;

    /**
     * 名称
     */
    @NotNull(message = "名称不能为空", groups = Save.class)
    @Length(min = 1, max = 255, message = "名称长度为1-255个字符", groups = {Save.class, Update.class})
    private String name;

    /**
     * url
     */
    @Length(max = 255, message = "url长度最多为255个字符", groups = {Save.class, Update.class})
    private String url;

    /**
     * 请求方法
     */
    @Length(max = 255, message = "方法长度最多为255个字符", groups = {Save.class, Update.class})
    private String method;

    /**
     * 图标链接
     */
    @URL(message = "图标链接格式错误", groups = {Save.class, Update.class})
    @Length(max = 255, message = "图标链接长度最多为255个字符", groups = {Save.class, Update.class})
    private String icon;

    /**
     * 顺序
     */
    @NotNull(message = "顺序不能为空", groups = Save.class)
    private Long seq;

    /**
     * 可分配，0否，1是
     */
    private Integer assign;

    /**
     * 记录日志，0否，1是
     */
    private Integer log;

    /**
     * 拥有，0否，1是
     */
    private Integer checked;

    /**
     * 子级列表
     */
    private List<SecResource> children;

    /**
     * 资源列表
     */
    private List<SecResource> list;

    public interface Save {}

    public interface Update {}

    public SecResource() {}

    public SecResource(Long id) {
        this.id = id;
    }

    public SecResource(String url, String method) {
        this.url = url;
        this.method = method;
    }

    @Override
    public Long getNodeId() {
        return id;
    }

    @Override
    public Long getParentNodeId() {
        if (parent != null) {
            return parent.getId();
        }
        return null;
    }

    @Override
    public void addChild(SecResource secResource) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(secResource);
    }

    @Override
    public List<SecResource> getChildren() {
        return children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SecResource getParent() {
        return parent;
    }

    public void setParent(SecResource parent) {
        this.parent = parent;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Integer getAssign() {
        return assign;
    }

    public void setAssign(Integer assign) {
        this.assign = assign;
    }

    public Integer getLog() {
        return log;
    }

    public void setLog(Integer log) {
        this.log = log;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public void setChildren(List<SecResource> children) {
        this.children = children;
    }

    public List<SecResource> getList() {
        return list;
    }

    public void setList(List<SecResource> list) {
        this.list = list;
    }
}
