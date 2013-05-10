package com.eds.ctcb.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import com.eds.ctcb.util.DataUtil;
public class AjaxScriptTag extends BaseTag {
  private static final long serialVersionUID = 1L;
  private static final String HAS_IMPORT_AJAX_JS = "HAS_IMPORT_AJAX_JS"; 
  
  private String triggerElementId;
  private String targetElementId;
  private String triggerEvent;
  private String url;


  public int doStartTag() throws JspException {
	//1.check param
	if(
		DataUtil.isEmptyStr(this.triggerElementId) 
		|| 
		DataUtil.isEmptyStr(this.targetElementId)
		|| 
		DataUtil.isEmptyStr(this.triggerEvent)
		|| 
		DataUtil.isEmptyStr(this.url)
	
	){
		return SKIP_BODY;
	}

	//2.new StringBuffer	
    StringBuffer sb = new StringBuffer();
    
    //3.import java script
    if(!DataUtil.isStrEqual("true", String.valueOf(this.pageContext.getAttribute(HAS_IMPORT_AJAX_JS)))){
    	sb.append(" <script language='JavaScript' type='text/JavaScript'src='./js/ajax.js'></script>");
    	this.pageContext.setAttribute(HAS_IMPORT_AJAX_JS,"true");
    }
    //4.
    sb.append("<script language='JavaScript' type='text/JavaScript' >");
    String triggerFuncName = this.triggerElementId+this.upperCaseFirstLetter(this.triggerEvent);
    String handleStateChangeFunc = "handleStateChange4"+this.upperCaseFirstLetter(this.triggerElementId);
    String urlWithParam ="'" +this.url+"?param='+document.getElementById('"+this.triggerElementId+"').value";
    	
	sb.append("function "+triggerFuncName+"() {");
	sb.append("  doAjax("+urlWithParam+","+handleStateChangeFunc+");");
	sb.append("}");
	
	sb.append("function "+handleStateChangeFunc+"() {");
    sb.append("  if(xmlHttpRequest.readyState == 4) {");
    sb.append("	   if(xmlHttpRequest.status == 200) {");
    sb.append("      document.getElementById('"+this.targetElementId+"').outerHTML = xmlHttpRequest.responseText;");
    sb.append("    }");
    sb.append("  }");
    sb.append("}");
    
    sb.append("document.getElementById('"+this.triggerElementId+"')."+this.triggerEvent+"="+triggerFuncName+";");
    
    sb.append("</script>");
    
    try {
      pageContext.getOut().print(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return SKIP_BODY;

  }


public String getUrl() {
	return url;
}


public void setUrl(String url) {
	this.url = this.expressionHandle("url", url);
}


public String getTargetElementId() {
	return targetElementId;
}


public void setTargetElementId(String targetElementId) {
	this.targetElementId = this.expressionHandle("targetElementId", targetElementId);
}


public String getTriggerElementId() {
	return triggerElementId;
}


public void setTriggerElementId(String triggerElementId) {
	this.triggerElementId = this.expressionHandle("triggerElementId", triggerElementId);
}


public String getTriggerEvent() {
	return triggerEvent;
}


public void setTriggerEvent(String triggerEvent) {
	this.triggerEvent = this.expressionHandle("triggerEvent", triggerEvent);
}







}
