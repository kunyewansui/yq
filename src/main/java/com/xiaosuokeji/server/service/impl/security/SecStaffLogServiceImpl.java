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
import com.xiaosuokeji.server.service.intf.security.SecStaffLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户日志ServiceImpl
 * Created by xuxiaowei on 2017/11/2.
 */
@Service
public class SecStaffLogServiceImpl implements SecStaffLogService {

    @Autowired
    private SecStaffLogDao secStaffLogDao;

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
        secStaffLog.setDefaultSort("id", "DESC");
        return XSPageModel.build(secStaffLogDao.list(secStaffLog), secStaffLogDao.count(secStaffLog));
    }

    @Override
    public void handle(XSLogger xsLogger) {
        if (xsLogger.getRequestUrl().contains("admin")) {
            SecResource criteria = new SecResource();
            criteria.setUrl(xsLogger.getRequestUrl());
            criteria.setMethod(xsLogger.getRequestMethod());
            List<SecResource> list = secStaffLogDao.listResourceByRequest(criteria);
            boolean doLog = false;
            if (list.size() > 0) {
                for (SecResource item : list) {
                    if (item.getLog().equals(1)) {
                        doLog = true;
                        break;
                    }
                }
            }
            if (doLog) {
                SecStaffLog latest = new SecStaffLog();
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    latest.setStaff((SecStaff) authentication.getPrincipal());
                }
                latest.setIp(xsLogger.getRequestIp());
                latest.setTime(xsLogger.getRequestTime());
                latest.setOperation(list.get(0).getDesc());
                latest.setStatus(xsLogger.getStatus() ? 1 : 0);
                try {
                    latest.setParameters(XSJackson.toJsonString(xsLogger.getParameters()));
                } catch (JsonProcessingException e) {}
                latest.setResult(xsLogger.getResult());
                save(latest);
            }
        }
    }
}
