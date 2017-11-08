package com.xiaosuokeji.server.service.impl.security;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.constant.security.SecResourceConsts;
import com.xiaosuokeji.server.dao.security.SecResourceDao;
import com.xiaosuokeji.server.model.security.SecResource;
import com.xiaosuokeji.server.model.security.SecRole;
import com.xiaosuokeji.server.service.intf.security.SecResourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 系统资源ServiceImpl
 * Created by xuxiaowei on 2017/10/27.
 */
@Service
public class SecResourceServiceImpl implements SecResourceService {

    private static final Logger logger = LoggerFactory.getLogger(SecResourceServiceImpl.class);

    @Autowired
    private SecResourceDao secResourceDao;

    private Cache<String, Map<String, Map<String, SecResource>>> cache = null;

    @PostConstruct
    public void init() throws Exception {
        cache = CacheBuilder.newBuilder().expireAfterAccess(1800L, TimeUnit.SECONDS).maximumSize(1).build();
        mapAll();
    }

    @Override
    @Transactional
    public void save(SecResource secResource) throws XSBusinessException {
        SecResource criteria = new SecResource();
        criteria.setKey(secResource.getKey());
        Long count = secResourceDao.count(criteria);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_EXIST);
        }
        //父级不可分配则子级不可分配
        if (secResource.getParent() != null && secResource.getParent().getId() != null
                && !secResource.getParent().getId().equals(0L)) {
            SecResource parent = secResourceDao.get(secResource.getParent());
            if (parent != null) {
                if (parent.getAssign().equals(0)) {
                    secResource.setAssign(0);
                }
            }
        }
        //如果不是url资源则置空url和method
        if (!secResource.getType().equals(2)) {
            secResource.setUrl(null);
            secResource.setMethod(null);
        }
        secResourceDao.save(secResource);
        secResourceDao.saveSuperiorRes(secResource);
        cache.invalidateAll();
    }

    @Override
    @Transactional
    public void remove(SecResource secResource) throws XSBusinessException {
        SecResource existent = get(secResource);
        SecResource children = new SecResource();
        children.setParent(existent);
        Long childrenCount = secResourceDao.count(children);
        if (childrenCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_USED);
        }
        //校验是否存在角色拥有该资源
        Long roleCount = secResourceDao.countRole(existent);
        if (roleCount.compareTo(0L) > 0) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_USED);
        }
        secResourceDao.removeSuperiorRes(existent);
        secResourceDao.remove(existent);
        cache.invalidateAll();
    }

    @Override
    @Transactional
    public void update(SecResource secResource) throws XSBusinessException {
        SecResource existent = get(secResource);
        if (secResource.getKey() != null) {
            SecResource criteria = new SecResource();
            criteria.setKey(secResource.getKey());
            List<SecResource> existents = secResourceDao.list(criteria);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(existent.getId());
                if (!isSelf) {
                    throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_EXIST);
                }
            }
        }
        //如果不是url资源则置空url和method
        if (!secResource.getType().equals(2)) {
            secResource.setUrl("");
            secResource.setMethod("");
        }
        secResourceDao.update(secResource);
        cache.invalidateAll();
        if (secResource.getAssign() != null) {
            List<SecResource> list = secResourceDao.listCombo(new SecResource());
            Map<Long, SecResource> map = XSTreeUtil.buildTree(list);
            SecResource latest = new SecResource();
            latest.setAssign(secResource.getAssign());
            //不可分配则所有子级也不可分配，可分配则所有父级也可分配
            if (secResource.getAssign().equals(0)) {
                List<SecResource> subTreeList = XSTreeUtil.listSubTree(map.get(existent.getId()));
                latest.setList(subTreeList);
            }
            else {
                List<SecResource> treePath = XSTreeUtil.getTreePath(map, map.get(existent.getId()));
                latest.setList(treePath);
            }
            secResourceDao.batchUpdate(latest);
        }
    }

    @Override
    public SecResource get(SecResource secResource) throws XSBusinessException {
        SecResource existent = secResourceDao.get(secResource);
        if (existent == null) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_NOT_EXIST);
        }
        return existent;
    }

    @Override
    public SecResource getByRequest(SecResource secResource) throws Exception {
        Map<String, SecResource> map = mapAll().get("request");
        return map.get(secResource.getUrl() + "_" + StringUtils.lowerCase(secResource.getMethod()));
    }

    @Override
    public SecResource getByKey(SecResource secResource) throws Exception {
        Map<String, SecResource> map = mapAll().get("key");
        return map.get(secResource.getKey());
    }

    @Override
    public XSPageModel listAndCount(SecResource secResource) {
        secResource.setDefaultSort(new String[]{"type", "seq"}, new String[]{"ASC", "DESC"});
        return XSPageModel.build(secResourceDao.list(secResource), secResourceDao.count(secResource));
    }

    @Override
    public List<SecResource> tree(SecResource secResource) {
        secResource.setDefaultSort(new String[]{"type", "seq"}, new String[]{"ASC", "DESC"});
        List<SecResource> list = secResourceDao.listCombo(secResource);
        for (Iterator<SecResource> iterator = list.iterator(); iterator.hasNext();) {
            SecResource item = iterator.next();
            //不展示类型为url的资源
            if (item.getType().equals(2)) {
                iterator.remove();
            }
        }
        XSTreeUtil.buildTree(list);
        return XSTreeUtil.getSubTrees(list, new SecResource(0L));
    }

    @Override
    public void invalidateCache() {
        cache.invalidateAll();
    }

    private Map<String, Map<String, SecResource>> mapAll() throws Exception {
        Map<String, Map<String, SecResource>> map = cache.get("cache", new Callable<Map<String, Map<String, SecResource>>>() {
            @Override
            public Map<String, Map<String, SecResource>> call() {
                logger.debug("Guava缓存未命中，从数据库中获取所有资源");
                List<SecResource> resourceList = secResourceDao.listAll();
                Map<String, Map<String, SecResource>> result = new HashMap<>();
                //构造以key为键的映射
                Map<String, SecResource> keyMap = new HashMap<>();
                for (SecResource item : resourceList) {
                    keyMap.put(item.getKey(), item);
                }
                result.put("key", keyMap);
                //构造以url和method为键的映射
                Map<String, SecResource> requestMap = new HashMap<>();
                for (SecResource item : resourceList) {
                    if (item.getType().equals(2)) {
                        requestMap.put(item.getUrl() + "_" + StringUtils.lowerCase(item.getMethod()), item);
                    }
                }
                result.put("request", requestMap);
                return result;
            }
        });
        return map;
    }
}
