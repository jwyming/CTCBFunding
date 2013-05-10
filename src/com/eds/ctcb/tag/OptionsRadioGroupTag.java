package com.eds.ctcb.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import com.eds.ctcb.util.DataUtil;
public class OptionsRadioGroupTag extends OptionsBaseTag{
	private static final long serialVersionUID = 1L;
	private static final String STYLE_HORIZONTAL="horizontal".toUpperCase();
	private static final String STYLE_VERTICAL="vertical".toUpperCase();
	private String name;
	private String style=STYLE_HORIZONTAL;
	

	public int doStartTag() throws JspException {
		StringBuffer sb = new StringBuffer();		
		HashMap map = this.getOptionsMap();
		
		if(map != null){
			for(Iterator i = map.keySet().iterator();i.hasNext();){
				if(sb.toString().length()>0){					
					this.style = DataUtil.getRegularStr(this.style);
					if(STYLE_VERTICAL.equals(this.style.toUpperCase())){
						sb.append("<br>");
					}else{
						sb.append("&nbsp;&nbsp;&nbsp;");
					}
					
				}
			   	Object key = i.next();//???????????
			   	String value = String.valueOf(map.get(key));//???????????
			   	boolean isSelected = DataUtil.isStrEqual(String.valueOf(key), selectedKey);
		   		sb.append("<input type=\"radio\" name=\""+this.name+"\" value=\""+String.valueOf(key)+"\" title=\""+value+"\"");
			   	if(isSelected){
			   		sb.append(" checked=\"checked\" ");
			   	}					   	
			   	sb.append(">");
			   	sb.append(value);
			   	sb.append("</input>");
			   	
			}
		}

		
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


	public String getStyle() {
		return style;
	}


	public void setStyle(String style) {
		this.style = this.expressionHandle("style", style);
	}



}
