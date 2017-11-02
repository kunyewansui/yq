package com.xiaosuokeji.server.tag;

import com.xiaosuokeji.server.model.system.DictData;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by gustinlau on 11/2/17.
 */
public class DicOptionsTag extends SimpleTagSupport {

    private List<DictData> items;

    public void setItems(List<DictData> items) {
        this.items = items;
    }

    @Override
    public void doTag() throws JspException, IOException {
        if(items!=null&&items.size()>0){
            JspWriter out = getJspContext().getOut();
            for(DictData data : items){
                out.println("<option value=\""+data.getValue()+"\">"+data.getName()+"</option>");
            }
        }
    }

}
