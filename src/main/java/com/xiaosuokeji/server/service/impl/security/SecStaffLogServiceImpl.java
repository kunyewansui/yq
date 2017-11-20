package com.xiaosuokeji.server.service.impl.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.framework.log.XSLogger;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.security.SecStaffLogConsts;
import com.xiaosuokeji.server.dao.security.SecStaffLogDao;
import com.xiaosuokeji.server.model.security.SecResource;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.model.security.SecStaffLog;
import com.xiaosuokeji.server.service.intf.security.SecResourceService;
import com.xiaosuokeji.server.service.intf.security.SecStaffLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 系统用户日志ServiceImpl
 * Created by xuxiaowei on 2017/11/2.
 */
@Service
public class SecStaffLogServiceImpl implements SecStaffLogService {

    private static final Logger logger = LoggerFactory.getLogger(SecStaffLogServiceImpl.class);

    @Autowired
    private SecStaffLogDao secStaffLogDao;

    @Autowired
    private SecResourceService secResourceService;

    @Override
    public void save(SecStaffLog secStaffLog) {
        secStaffLogDao.save(secStaffLog);
    }

    @Override
    public void remove(SecStaffLog secStaffLog) throws XSBusinessException {
        SecStaffLog existent = get(secStaffLog);
        secStaffLogDao.remove(existent);
    }

    @Override
    public SecStaffLog get(SecStaffLog secStaffLog) throws XSBusinessException {
        SecStaffLog existent = secStaffLogDao.get(secStaffLog);
        if (existent == null) {
            throw new XSBusinessException(SecStaffLogConsts.SEC_STAFF_LOG_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public XSPageModel<SecStaffLog> listAndCount(SecStaffLog secStaffLog) {
        secStaffLog.setDefaultSort("sl.id", "DESC");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecStaff secStaff = (SecStaff) authentication.getPrincipal();
            //非超级管理员不能查看超级管理员日志
            if (!secStaff.getId().equals(1L)) {
                if (secStaffLog.getDynamic() == null) {
                    secStaffLog.setDynamic(new HashMap<>());
                }
                secStaffLog.getDynamic().put("notSuperior", "true");
            }
        }
        return XSPageModel.build(secStaffLogDao.list(secStaffLog), secStaffLogDao.count(secStaffLog));
    }

    @Override
    public void handle(XSLogger xsLogger) {
        if (xsLogger.getRequestUrl().contains("admin")) {
            try {
                SecResource criteria = new SecResource();
                criteria.setUrl(xsLogger.getRequestUrl().substring(xsLogger.getRequestUrl().indexOf("admin") - 1));
                criteria.setMethod(xsLogger.getRequestMethod());
                SecResource resource = secResourceService.getByRequest(criteria);
                if (resource != null && resource.getLog().equals(1)) {
                    SecStaffLog latest = new SecStaffLog();
                    SecStaff secStaff = (SecStaff) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    latest.setStaff(secStaff);
                    latest.setIp(xsLogger.getRequestIp());
                    latest.setTime(xsLogger.getRequestTime());
                    latest.setOperation(resource.getDesc());
                    latest.setStatus(xsLogger.getStatus() ? 1 : 0);
                    try {
                        latest.setParameters(XSJackson.toJsonString(xsLogger.getParameters()));
                    } catch (JsonProcessingException e) {}
                    latest.setResult(xsLogger.getResult());
                    save(latest);
                }
            } catch (Exception e) {
                logger.error("error : ", e);
            }
        }
    }
}
