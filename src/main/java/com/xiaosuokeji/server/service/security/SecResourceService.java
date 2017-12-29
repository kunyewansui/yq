package com.xiaosuokeji.server.service.security;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.framework.util.XSTreeUtil;
import com.xiaosuokeji.server.constant.security.SecResourceConsts;
import com.xiaosuokeji.server.dao.security.SecResourceDao;
import com.xiaosuokeji.server.model.security.SecResource;
import com.xiaosuokeji.server.model.security.SecRole;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 系统资源ServiceImpl
 * Created by xuxiaowei on 2017/10/27.
 */
@Service
public class SecResourceService {

    private static final Logger logger = LoggerFactory.getLogger(SecResourceService.class);

    @Autowired
    private SecResourceDao secResourceDao;

    private Cache<String, Map<String, Map<String, SecResource>>> cache = null;

    @PostConstruct
    public void init() throws Exception {
        cache = CacheBuilder.newBuilder().expireAfterAccess(1800L, TimeUnit.SECONDS).maximumSize(1).build();
        mapAll();
    }

    @Transactional
    public void save(SecResource secResource) throws XSBusinessException {
        SecResource criteria = new SecResource();
        criteria.setKey(secResource.getKey());
        Long count = secResourceDao.count(criteria);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_EXIST);
        }
        if (!secResource.getType().equals(2)) {
            //如果不是url资源则置空url和method
            secResource.setUrl(null);
            secResource.setMethod(null);
        }
        else {
            criteria = new SecResource();
            criteria.setUrl(secResource.getUrl());
            criteria.setMethod(secResource.getMethod());
            count = secResourceDao.count(criteria);
            if (count.compareTo(0L) > 0) {
                throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_EXIST);
            }
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
        secResourceDao.save(secResource);
        secResourceDao.saveSuperiorRes(secResource);
        cache.invalidateAll();
    }

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
        if (secResource.getType() != null && secResource.getType().equals(2)) {
            SecResource criteria = new SecResource();
            criteria.setUrl(secResource.getUrl());
            criteria.setMethod(secResource.getMethod());
            List<SecResource> existents = secResourceDao.list(criteria);
            if (existents.size() > 0) {
                boolean isSelf = existents.get(0).getId().equals(existent.getId());
                if (!isSelf) {
                    throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_EXIST);
                }
            }
        }
        secResourceDao.update(secResource);
        if (secResource.getAssign() != null) {
            List<SecResource> list = secResourceDao.listCombo(new SecResource());
            Map<Long, SecResource> map = XSTreeUtil.buildTree(list);
            SecResource latest = new SecResource();
            latest.setAssign(secResource.getAssign());
            //不可分配则所有子级也不可分配，可分配则直属父级和所有子级也可分配
            if (secResource.getAssign().equals(0)) {
                List<SecResource> subTreeList = XSTreeUtil.listSubTree(map.get(existent.getId()));
                latest.setList(subTreeList);
                //回收所有角色拥有的不可分配资源
                if (subTreeList.size() > 0) {
                    secResourceDao.batchRemoveRoleRes(latest);
                }
            }
            else {
                List<SecResource> treePath = XSTreeUtil.getTreePath(map, map.get(existent.getId()));
                latest.setList(treePath);
                List<SecResource> subTreeList = XSTreeUtil.listSubTree(map.get(existent.getId()));
                latest.getList().addAll(subTreeList);
            }
            secResourceDao.batchUpdate(latest);
        }
        cache.invalidateAll();
    }

    public SecResource get(SecResource secResource) throws XSBusinessException {
        SecResource existent = secResourceDao.get(secResource);
        if (existent == null) {
            throw new XSBusinessException(SecResourceConsts.SEC_RESOURCE_NOT_EXIST);
        }
        return existent;
    }

    public SecResource getByRequest(SecResource secResource) throws Exception {
        Map<String, SecResource> map = mapAll().get("request");
        return map.get(secResource.getUrl() + "_" + StringUtils.lowerCase(secResource.getMethod()));
    }

    public SecResource getByKey(SecResource secResource) throws Exception {
        Map<String, SecResource> map = mapAll().get("key");
        return map.get(secResource.getKey());
    }

    public XSPageModel listAndCount(SecResource secResource) {
        secResource.setDefaultSort(new String[]{"type", "seq"}, new String[]{"ASC", "DESC"});
        return XSPageModel.build(secResourceDao.list(secResource), secResourceDao.count(secResource));
    }

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
                    //构造逗号分隔的角色列表字符串
                    StringBuilder rolesStr = new StringBuilder();
                    for (SecRole role : item.getRoleList()) {
                        rolesStr.append("ROLE_" + String.valueOf(role.getId())).append(",");
                    }
                    if (rolesStr.length() > 0) {
                        rolesStr.deleteCharAt(rolesStr.length() - 1);
                    }
                    item.setRolesStr(rolesStr.toString());
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
