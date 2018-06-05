package com.xiaosuokeji.server.service.statistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.server.dao.statistics.StatisticsDao;
import com.xiaosuokeji.server.model.order.Order;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: guobaikun
 * Date: 2018/2/6
 * Time: 20:03
 */

@Service
public class StatisticsService {

    @Autowired
    private StatisticsDao statisticsDao;

    public Map<String, String> orderFlow() throws ParseException, JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH,1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));

        List<Map<String, Object>> list = statisticsDao.orderFlow(sdf.format(startDate.getTime()), sdf.format(endDate.getTime()));
        List<String> labels = new ArrayList<>();
        List<BigDecimal> actualDatasets = new ArrayList<>();
        while (!startDate.after(endDate)) {
            String date = sdf.format(startDate.getTime());
            labels.add(sdf1.format(startDate.getTime()));
            int i = 0;
            for (;i < list.size(); i++) {
                if (list.get(i).get("label").equals(date)) {
                    actualDatasets.add(((BigDecimal)list.get(i).get("amount")));
                    break;
                }
            }
            if (i >= list.size()) {
                actualDatasets.add(new BigDecimal("0"));
            }
            startDate.add(Calendar.DATE, 1);
        }
        Map<String, String> result = new HashMap<>();
        result.put("labels", XSJackson.toJsonString(labels));
        result.put("actualDatasets", XSJackson.toJsonString(actualDatasets));
        return result;
    }

    public Map<String, Object> statisticsCount() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,1);
        Date startTime = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endTime = cal.getTime();
        Map<String, Object> result = new HashMap();
        Map<String, Object> map1 = statisticsDao.monthlyOrder(sdf.format(startTime), sdf.format(endTime));
        Map<String, Object> map2 = statisticsDao.monthlyRepay(sdf.format(startTime), sdf.format(endTime));
        Map<String, Object> map3 = statisticsDao.monthPayment();
        result.putAll(map1);
        result.putAll(map2);
        result.putAll(map3);
        return result;
    }
}
