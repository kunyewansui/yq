package com.xiaosuokeji.server.manager.security;

import com.xiaosuokeji.framework.constant.XSStatusConstant;
import com.xiaosuokeji.framework.util.XSIpUtil;
import com.xiaosuokeji.server.constant.security.SecStaffConsts;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.model.security.SecStaffLog;
import com.xiaosuokeji.server.service.intf.security.SecStaffLogService;
import com.xiaosuokeji.server.service.intf.security.SecStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * 系统用户登录Service
 * Created by xuxiaowei on 2017/10/27.
 */
@Service("userDetailService")
public class DetailsService implements UserDetailsService {

    @Autowired
    private SecStaffService secStaffService;

    @Autowired
    private SecStaffLogService secStaffLogService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecStaff staff = null;
        try {
            staff = secStaffService.getByUsername(new SecStaff(username));
        } catch (Exception e) {
            throw new UsernameNotFoundException(XSStatusConstant.FAILURE.getMsg());
        }
        if (staff == null) {
            throw new UsernameNotFoundException(SecStaffConsts.SEC_STAFF_NOT_EXIST.getMsg());
        }
        if (!staff.isEnabled()) {
            throw new DisabledException(SecStaffConsts.SEC_STAFF_NOT_ENABLE.getMsg());
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (SecRole role : staff.getRoleList()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + String.valueOf(role.getId())));
        }
        staff.setAuthorityList(authorities);
        //记录登录日志
        SecStaffLog latest = new SecStaffLog();
        latest.setStaff(staff);
        latest.setIp("");
        latest.setTime(new Date());
        latest.setOperation("登录系统");
        latest.setStatus(1);
        latest.setParameters("");
        latest.setResult("");
        secStaffLogService.save(latest);
        return staff;
    }
}
