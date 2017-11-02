package com.xiaosuokeji.server.manager.security;

import com.xiaosuokeji.server.constant.security.SecStaffConsts;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.service.intf.security.SecStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 系统用户登录Service
 * Created by xuxiaowei on 2017/10/27.
 */
@Service("userDetailService")
public class DetailsService implements UserDetailsService {

    @Autowired
    private SecStaffService secStaffService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecStaff staff = secStaffService.getByUsername(new SecStaff(username));
        if (staff == null) {
            throw new UsernameNotFoundException(SecStaffConsts.SEC_STAFF_NOT_EXIST.getMsg());
        }
        if (!staff.isEnabled()) {
            throw new DisabledException(SecStaffConsts.SEC_STAFF_NOT_ENABLE.getMsg());
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (SecRole role : staff.getRoleList()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        staff.setAuthorityList(authorities);
        return staff;
    }
}
