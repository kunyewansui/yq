package com.xiaosuokeji.server.service.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.system.CountryConsts;
import com.xiaosuokeji.server.dao.system.CountryDao;
import com.xiaosuokeji.server.model.system.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 国家ServiceImpl
 * Created by xuxiaowei on 2018/5/3.
 */
@Service
public class CountryService {
    
    @Autowired
    private CountryDao countryDao;

    public void save(Country country) throws XSBusinessException {
        try {
            countryDao.save(country);
        } catch (DuplicateKeyException e) {
            throw new XSBusinessException(CountryConsts.COUNTRY_EXIST);
        }
    }

    public void remove(Country country) throws XSBusinessException {
        Country existent = get(country);
        countryDao.remove(existent);
    }

    public void update(Country country) throws XSBusinessException {
        try {
            countryDao.update(country);
        } catch (DuplicateKeyException e) {
            throw new XSBusinessException(CountryConsts.COUNTRY_EXIST);
        }
    }

    public void updateStatus(Country country) {
        countryDao.updateStatus(country);
    }

    public Country get(Country country) throws XSBusinessException {
        Country existent = countryDao.get(country);
        if (existent == null) {
            throw new XSBusinessException(CountryConsts.COUNTRY_NOT_EXIST);
        }
        return existent;
    }

    public XSPageModel<Country> listAndCount(Country country) {
        country.setDefaultSort(new String[]{"seq", "id"}, new String[]{"DESC", "ASC"});
        return XSPageModel.build(countryDao.list(country), countryDao.count(country));
    }

    public List<Country> listCombo(Country country) {
        country.setDefaultSort("seq", "DESC");
        return countryDao.listCombo(country);
    }
}
