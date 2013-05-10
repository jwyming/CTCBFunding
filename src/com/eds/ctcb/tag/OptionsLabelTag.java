package com.eds.ctcb.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.jsp.JspException;

import com.eds.ctcb.util.DataUtil;

public class OptionsLabelTag extends OptionsBaseTag{
	private static final long serialVersionUID = 1L;

	public int doStartTag() throws JspException {
		String s = "";
		HashMap map = this.getOptionsMap();

		if(map == null){
			return SKIP_BODY;
		}
		
		for(Iterator i = map.keySet().iterator();i.hasNext();){
		   	Object key = i.next();
		   	boolean isSelected = DataUtil.isStrEqual(String.valueOf(key), selectedKey);
		   	if(isSelected){
			   	s = String.valueOf(map.get(key));
		   		break;
		   	}			   
		}
		
		try {
			pageContext.getOut().print(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// not Allow text in the body of the tag.
		return SKIP_BODY;

	}




}
