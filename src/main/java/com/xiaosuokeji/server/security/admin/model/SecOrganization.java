package com.xiaosuokeji.server.security.admin.model;

import com.xiaosuokeji.framework.annotation.XSAutoDesc;
import com.xiaosuokeji.framework.intf.XSTreeable;
import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统组织
 * Created by xuxiaowei on 2017/10/23.
 */
public class SecOrganization extends BaseModel implements XSTreeable<Long> {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    @NotNull(message = "名称不能为空", groups = Save.class)
    @Length(min = 1, max = 191, message = "名称长度为1-191个字符", groups = {Save.class, Update.class})
    private String name;

    /**
     * 父级
     */
    private SecOrganization parent;

    /**
     * 状态，0禁用，1启用
     */
    @XSAutoDesc("secOrganizationStatus")
    private Integer status;

    /**
     * 描述
     */
    @Length(max = 255, message = "描述长度最多为255个字符", groups = {Save.class, Update.class})
    private String desc;

    /**
     * 子级列表
     */
    private List<XSTreeable<Long>> children;

    /**
     * 组织列表
     */
    private List<SecOrganization> list;

    /**
     * 角色列表
     */
    private List<SecRole> roleList;

    /**
     * 拥有，0否，1是
     */
    private Integer checked;

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
    public void addChild(XSTreeable<Long> xsTreeable) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(xsTreeable);
    }

    @Override
    public List<XSTreeable<Long>> getChildren() {
        return children;
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

    public SecOrganization getParent() {
        return parent;
    }

    public void setParent(SecOrganization parent) {
        this.parent = parent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<SecOrganization> getList() {
        return list;
    }

    public void setList(List<SecOrganization> list) {
        this.list = list;
    }

    public List<SecRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SecRole> roleList) {
        this.roleList = roleList;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public interface Save {
    }

    public interface Update {
    }
}
