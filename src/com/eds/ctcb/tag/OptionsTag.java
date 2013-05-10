package com.eds.ctcb.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import com.eds.ctcb.util.DataUtil;
import com.eds.ctcb.util.I18NUtil;


public class OptionsTag extends OptionsBaseTag{
	private static final long serialVersionUID = 1L;

	protected String hasEmptyOption = "true";
	protected String emptyOptionTitleKey;
	protected String generateHtml(){
		StringBuffer sb = new StringBuffer();
		HashMap map = this.getOptionsMap();
		
		if(!"false".equals(this.hasEmptyOption)){			
			sb.append("<option value=\"\">");
			if(DataUtil.isEmptyStr(this.emptyOptionTitleKey)){
				sb.append(I18NUtil.getResourceBundle(this.getRequest()).getString("common.label.emptyOption"));
			}else{
				sb.append(I18NUtil.getResourceBundle(this.getRequest()).getString(this.emptyOptionTitleKey));
			}
			sb.append("</option>");
		}
		
		if(map != null){
			for(Iterator i = map.keySet().iterator();i.hasNext();){
			   	Object key = i.next();//???????????
			   	String value = String.valueOf(map.get(key));//???????????
			   	boolean isSelected = DataUtil.isStrEqual(String.valueOf(key), selectedKey);

		   		sb.append("<option value=\""+String.valueOf(key)+"\" ");
			   	if(isSelected){
			   		sb.append(" selected=\"selected\" ");
			   	}					   	
			   	sb.append(">");
			   	sb.append(value);
			   	sb.append("</option>");
					   
			}
		}
		return sb.toString();
	}


	public int doStartTag() throws JspException {
		
		try {
			pageContext.getOut().print(this.generateHtml());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// not Allow text in the body of the tag.
		return SKIP_BODY;

	}


	public String getHasEmptyOption() {
		return hasEmptyOption;
	}
	public void setHasEmptyOption(String hasEmptyOption) {
		this.hasEmptyOption = this.expressionHandle("hasEmptyOption", hasEmptyOption);
	}

	public String getEmptyOptionTitleKey() {
		return emptyOptionTitleKey;
	}
	public void setEmptyOptionTitleKey(String emptyOptionTitleKey) {
		this.emptyOptionTitleKey = this.expressionHandle("emptyOptionTitleKey", emptyOptionTitleKey);
	}





}
