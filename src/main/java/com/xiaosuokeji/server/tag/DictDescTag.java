package com.xiaosuokeji.server.tag;

import com.xiaosuokeji.framework.spring.XSSpringContext;
import com.xiaosuokeji.server.model.system.DictData;
import com.xiaosuokeji.server.service.intf.system.DictDataService;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by gustinlau on 11/2/17.
 */
public class DictNameTag extends SimpleTagSupport {

    private String key;

    private String value;

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        List<DictData> items = ((DictDataService) XSSpringContext.getBean("dictDataService")).listByDict(key);
        if (items != null && items.size() > 0) {
            JspWriter out = getJspContext().getOut();
            for (DictData data : items) {
                if (data.getValue().equals(value)) {
                    out.println(data.getDesc());
                    break;
                }
            }
        }
    }
}
