package com.xiaosuokeji.server.tag;

import com.xiaosuokeji.framework.spring.XSSpringContext;
import com.xiaosuokeji.server.service.intf.system.DictDataService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * 根据value查询desc的标签逻辑
 * Created by gustinlau on 11/2/17.
 */
public class DictDescTag extends SimpleTagSupport {

    private static DictDataService dictDataService=XSSpringContext.getBean("dictDataServiceImpl");

    /**
     * 字典的Key
     */
    private String key;

    /**
     * 查询的值
     */
    private String value;

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println(dictDataService.getDesc(key, value));
    }
}
