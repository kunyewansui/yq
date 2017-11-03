package com.xiaosuokeji.server.tag;

import com.xiaosuokeji.framework.spring.XSSpringContext;
import com.xiaosuokeji.server.model.system.DictData;
import com.xiaosuokeji.server.service.intf.system.DictDataService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 根据字典数组输出<option></option>标签
 * Created by gustinlau on 11/2/17.
 */
public class DictOptionsTag extends SimpleTagSupport {

    private static DictDataService dictDataService=XSSpringContext.getBean("dictDataServiceImpl");

    /**
     * 字典key
     */
    private String key;

    /**
     * 默认选择值
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
        Map<String, String> items = dictDataService.mapByDict(key);
        if (items != null && items.size() > 0) {
            JspWriter out = getJspContext().getOut();
            for (Map.Entry<String, String> entry : items.entrySet()) {
                if(entry.getKey().equals(value)){
                    out.println("<option value=\"" + entry.getKey() + "\" selected>" + entry.getValue() + "</option>");
                }else{
                    out.println("<option value=\"" + entry.getKey() + "\">" + entry.getValue() + "</option>");
                }

            }
        }
    }


}
