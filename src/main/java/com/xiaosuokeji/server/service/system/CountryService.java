package com.xiaosuokeji.server.service.system;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.model.XSPageModel;
import com.xiaosuokeji.server.constant.system.CountryConsts;
import com.xiaosuokeji.server.dao.system.CountryDao;
import com.xiaosuokeji.server.model.security.SecStaff;
import com.xiaosuokeji.server.model.system.Country;
import com.xiaosuokeji.server.util.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
        Country criteria = new Country();
        criteria.setId(country.getId());
        Long count = countryDao.count(criteria);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(CountryConsts.COUNTRY_EXIST);
        }
        criteria = new Country();
        criteria.setName(country.getName());
        count = countryDao.count(criteria);
        if (count.compareTo(0L) > 0) {
            throw new XSBusinessException(CountryConsts.COUNTRY_EXIST);
        }
        country.setStatus(0);
        countryDao.save(country);
    }

    public void remove(Country country) throws XSBusinessException {
        Country existent = get(country);
        countryDao.remove(existent);
    }

    public void update(Country country) throws XSBusinessException {
        Country existent = get(country);
        Country criteria = new Country();
        criteria.setName(country.getName());
        List<Country> existents = countryDao.list(criteria);
        if (existents.size() > 0) {
            boolean isSelf = existents.get(0).getId().equals(existent.getId());
            if (!isSelf) {
                throw new XSBusinessException(CountryConsts.COUNTRY_EXIST);
            }
        }
        countryDao.update(country);
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

    @Transactional
    public void importTemplate(MultipartFile multipartFile) throws Exception {
        //获取工作簿对象
        Workbook work = ExcelUtil.getWorkbook(multipartFile);
        Sheet sheet = work.getSheetAt(0);
        if (sheet == null) return;

        List<Country> suppliers = new ArrayList<>();
        Cell cell;
        Country country;
        for (int i=1;i<=sheet.getLastRowNum();i++) {
            Row row = sheet.getRow(i);
            if(row==null) continue;
            country = new Country();
            country.setName(ExcelUtil.getCellValue(row.getCell(1)).trim());
            country.setEnName(ExcelUtil.getCellValue(row.getCell(2)).trim());
            country.setCode2(ExcelUtil.getCellValue(row.getCell(3)).trim());
            country.setCode3(ExcelUtil.getCellValue(row.getCell(4)).trim());
            country.setCode(ExcelUtil.getCellValue(row.getCell(5)).trim());
            String str = ExcelUtil.getCellValue(row.getCell(6)).trim();
            String[] split = str.split("\\s+", 2);
            country.setFullName(split[0]);
            country.setEnFullName(split[1]);
            suppliers.add(country);
        }
        if(suppliers.size()>0) countryDao.batchUpdate(suppliers);
        work.close();
    }
}
