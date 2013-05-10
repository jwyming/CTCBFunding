package com.eds.ctcb.tag;

import java.io.IOException;
import com.eds.ctcb.util.DataUtil;
import javax.servlet.jsp.JspException;
public class OptionsSelectTag extends OptionsTag{
	private static final long serialVersionUID = 1L;
	private String name;
	private String id;

	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();
		String idStr = "";
		if(!DataUtil.isEmptyStr(this.id)){
			idStr = "id=\""+this.id+"\"";
		}
		sb.append("<select "+idStr+" name=\""+this.name+"\" >");		
		sb.append(this.generateHtml());
		sb.append("</select>");		
		try {
			pageContext.getOut().print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// not Allow text in the body of the tag.
		return SKIP_BODY;

	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = this.expressionHandle("name", name);
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = this.expressionHandle("id", id);
	}


}
