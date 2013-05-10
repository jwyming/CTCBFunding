package com.eds.ctcb.tag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;

import com.eds.ctcb.util.DataUtil;
public class FormatDateTag extends BaseTag {
  private static final long serialVersionUID = 1L;
  private Date value;
  private String includeTime;

  public int doStartTag() throws JspException {
	  String s = "";
	  if(this.includeTime!=null && "true".equals(this.includeTime.toLowerCase().trim())){
		  s = DataUtil.date2Str(this.value, true);
	  }else{
		  s = DataUtil.date2Str(this.value, false);
	  }
		
	    try {
	      pageContext.getOut().print(s);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return SKIP_BODY;

  }

public Date getValue() {
	return value;
}

public void setValue(Date value) {
	this.value = value;
}

public String getIncludeTime() {
	return includeTime;
}

public void setIncludeTime(String includeTime) {
	this.includeTime = this.expressionHandle("includeTime", includeTime);
}








}
