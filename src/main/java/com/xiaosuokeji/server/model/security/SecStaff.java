package com.xiaosuokeji.server.model.security;

import com.xiaosuokeji.framework.annotation.XSAutoDesc;
import com.xiaosuokeji.server.model.base.BaseModel;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;

/**
 * 系统用户
 * Created by xuxiaowei on 2017/10/23.
 */
public class SecStaff extends BaseModel implements UserDetails {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空", groups = Save.class)
    @Length(min = 1, max = 191, message = "用户名长度为1-191个字符", groups = {Save.class, Update.class})
    private String username;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空", groups = Save.class)
    @Length(min = 6, max = 20, message = "密码长度为6-20个字符", groups = {Save.class, Update.class})
    private String password;

    /**
     * 状态，0禁用，1启用
     */
    @XSAutoDesc("secStaffStatus")
    private Integer status;

    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空", groups = Save.class)
    @Length(min = 1, max = 255, message = "姓名长度为1-255个字符", groups = {Save.class, Update.class})
    private String name;

    /**
     * 手机
     */
    @NotNull(message = "手机不能为空", groups = {Save.class, Update.class})
    @Pattern(regexp = "^1[0-9]{10}$", message = "手机号格式为11位数字且第一位为1", groups = {Save.class, Update.class})
    private String mobile;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式错误", groups = {Save.class, Update.class})
    @Length(max = 255, message = "邮箱长度最多为255个字符", groups = {Save.class, Update.class})
    private String email;

    /**
     * qq号
     */
    @Length(max = 255, message = "qq长度最多为255个字符", groups = {Save.class, Update.class})
    private String qq;

    /**
     * 角色列表
     */
    private List<SecRole> roleList;

    /**
     * 组织列表
     */
    private List<SecOrganization> organizationList;

    /**
     * 权限列表
     */
    private Collection<GrantedAuthority> authorityList;

    public interface Save {}

    public interface Update {}

    public SecStaff() {}

    public SecStaff(Long id) {
        this.id = id;
    }

    public SecStaff(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(1);
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SecStaff) {
            return this.username.equals(((SecStaff) obj).username);
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public List<SecRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SecRole> roleList) {
        this.roleList = roleList;
    }

    public List<SecOrganization> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<SecOrganization> organizationList) {
        this.organizationList = organizationList;
    }

    public Collection<GrantedAuthority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(Collection<GrantedAuthority> authorityList) {
        this.authorityList = authorityList;
    }
}
