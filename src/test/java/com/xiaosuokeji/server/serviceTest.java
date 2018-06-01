package com.xiaosuokeji.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xiaosuokeji.framework.exception.XSBusinessException;
import com.xiaosuokeji.framework.json.XSJackson;
import com.xiaosuokeji.server.model.image.Image;
import com.xiaosuokeji.server.model.image.ImageCategory;
import com.xiaosuokeji.server.model.order.Payment;
import com.xiaosuokeji.server.model.product.Product;
import com.xiaosuokeji.server.service.article.ArticleService;
import com.xiaosuokeji.server.service.image.ImageService;
import com.xiaosuokeji.server.service.order.PaymentService;
import com.xiaosuokeji.server.service.product.ProductService;
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
    @Autowired
    private ProductService productService;

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


    @Test
    public void test2() throws XSBusinessException {
        String str = "津巴布韦共和国 Republic of Zimbabwe";
        String[] split = str.split("\\s+", 2);
        System.out.println(split[0]);
        System.out.println(split[1]);

    }
    @Test
    public void generateProduct() {
        for(int i=28;i<200;i++){
            Product product = new Product();
            product.setCode("BK"+((int)(10000*Math.random())+10000));
            product.setCost(BigDecimal.valueOf(100.01));
            product.setManuPrice(BigDecimal.valueOf(200.00));
            product.setName("产品"+(1000+i));
            product.setCateId((long)(10*Math.random())+1);
            product.setShopStock((long)(1000*Math.random()));
            product.setFactoryStock((long)(100*Math.random())*10);
            product.setImage("http://aocai-home.oss-cn-shenzhen.aliyuncs.com/yq/product/201805070714231525691663030.jpg");
            product.setPicList(CollectionUtils.toList("http://aocai-home.oss-cn-shenzhen.aliyuncs.com/ldbk/officer/201803210605081521626708728.jpg","http://aocai-home.oss-cn-shenzhen.aliyuncs.com/ldbk/officer/201803210605081521626708728.jpg"));
            try {
                productService.save(product);
            } catch (Exception e) { }
        }

    }


}
