package com.xiaosuokeji.server;

import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.server.model.image.Image;
import com.xiaosuokeji.server.model.image.ImageCategory;
import com.xiaosuokeji.server.model.order.Payment;
import com.xiaosuokeji.server.service.article.ArticleService;
import com.xiaosuokeji.server.service.image.ImageService;
import com.xiaosuokeji.server.service.order.PaymentService;
import com.xiaosuokeji.server.service.system.DictDataService;
import com.xiaosuokeji.server.service.system.SystemConfigService;
import com.xiaosuokeji.server.util.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * User: guobaikun
 * Date: 2018/4/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class serviceTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    public void test() throws XSBusinessException {
        Payment payment = new Payment();
        payment.setCreator("kunye");
        payment.setMerchantId(3L);
        payment.setOrderno("20180515241395521");
        payment.setRemark("没什么可说的");
        for(int i=151;i<=200;i++){
            int amount = (int)(100*Math.random()+1);
            payment.setAmount(BigDecimal.valueOf(amount*1000));
            payment.setOrderno("201805152413955"+i);
            paymentService.save(payment);
        }
    }


}
