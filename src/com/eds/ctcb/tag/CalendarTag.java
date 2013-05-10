package com.eds.ctcb.tag;

import java.io.IOException;
import com.eds.ctcb.util.I18NUtil;

import javax.servlet.jsp.JspException;
public class CalendarTag extends BaseTag {
  private static final long serialVersionUID = 1L;

  private String targetTexBoxId;
  private String includeTime;
  

  public int doStartTag() throws JspException {  
	String formatTip = "";
	if(this.includeTime!=null && "true".equals(this.includeTime.toLowerCase())){
		formatTip = I18NUtil.getResourceBundle(this.getRequest()).getString("common.label.dateTimeFormat");
	}else{
		this.includeTime="false";
		formatTip = I18NUtil.getResourceBundle(this.getRequest()).getString("common.label.dateFormat");
	}
	
	String imgId = targetTexBoxId + "CalendarImg";
	StringBuffer sb = new StringBuffer();
	sb.append("<a href=\"javascript:popUpCalendar(document.getElementById('"+imgId+"'),document.getElementById('"+this.targetTexBoxId+"'),"+this.includeTime+")\" >");
	sb.append("<img id='"+imgId+"' src='images/calendar/calendar.jpg' border='0'/>");
	sb.append("</a>");	
	sb.append("&nbsp;"+formatTip);
	

    try {
      pageContext.getOut().print(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return SKIP_BODY;

  }


public String getIncludeTime() {
	return includeTime;
}


public void setIncludeTime(String includeTime) {
	this.includeTime = this.expressionHandle("includeTime", includeTime);
}


public String getTargetTexBoxId() {
	return targetTexBoxId;
}


public void setTargetTexBoxId(String targetTexBoxId) {
	this.targetTexBoxId = this.expressionHandle("targetTexBoxId", targetTexBoxId);
}

}
