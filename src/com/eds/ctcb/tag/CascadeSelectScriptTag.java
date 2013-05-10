package com.eds.ctcb.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import com.eds.ctcb.util.DataUtil;
public class CascadeSelectScriptTag extends BaseTag {
  private static final long serialVersionUID = 1L;
  private static final String HAS_IMPORT_AJAX_JS = "HAS_IMPORT_AJAX_JS"; 
  
  private String selectBoxIds;
  private String urls;

  public int doStartTag() throws JspException {
	//1.check param
	if(DataUtil.isEmptyStr(this.selectBoxIds) || DataUtil.isEmptyStr(urls)){
		return SKIP_BODY;
	}
	
	String[] selectBoxArray = this.selectBoxIds.split(this.SEPARATOR);
	String[] urlArray = this.urls.split(this.SEPARATOR);
	if(
		selectBoxArray==null 
		||
		selectBoxArray.length<=1 
		|| 
		urlArray==null 
		|| 
		urlArray.length<=0 
		|| 
		selectBoxArray.length != urlArray.length+1
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
    String firstOnChangeFunc = "";
    String firstSelectBox = "";
    for(int i=0;i<selectBoxArray.length-1;i++){
    	String selectBox = selectBoxArray[i];
    	String nextSelectBox = selectBoxArray[i+1];
    	String onChangeFunc = "on"+this.upperCaseFirstLetter(selectBox)+"Change";
    	String nextOnChangeFunc = "on"+this.upperCaseFirstLetter(nextSelectBox)+"Change";
    	if(i==0){
    		firstSelectBox = selectBox;
    		firstOnChangeFunc = onChangeFunc;
    	}
    	String url ="'" +urlArray[i]+"?param='+document.getElementById('"+selectBox+"').value";
    	String handleStateChangeFunc = "handleStateChangeFor"+this.upperCaseFirstLetter(selectBox);
    	
    	sb.append("function "+onChangeFunc+"() {");
    	sb.append("  doAjax("+url+","+handleStateChangeFunc+");");
    	sb.append("}");
    	
    	sb.append("function "+handleStateChangeFunc+"() {");
        sb.append("  if(xmlHttpRequest.readyState == 4) {");
        sb.append("	   if(xmlHttpRequest.status == 200) {");
        sb.append("      document.getElementById('"+nextSelectBox+"').outerHTML = xmlHttpRequest.responseText;");
        if(i+1<=selectBoxArray.length-2){
            sb.append("document.getElementById('"+nextSelectBox+"').onchange="+nextOnChangeFunc+";");
            sb.append(nextOnChangeFunc+"();");
        }
        sb.append("    }");
        sb.append("  }");
        sb.append("}");
    }


    sb.append("document.getElementById('"+firstSelectBox+"').onchange="+firstOnChangeFunc+";");
    sb.append(firstOnChangeFunc+"();");  
    
    sb.append("</script>");
    
    try {
      pageContext.getOut().print(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return SKIP_BODY;

  }

public String getUrls() {
	return urls;
}

public void setUrls(String urls) {
	this.urls = this.expressionHandle("urls", urls);
}

public String getSelectBoxIds() {
	return selectBoxIds;
}

public void setSelectBoxIds(String selectBoxIds) {
	this.selectBoxIds = this.expressionHandle("selectBoxIds", selectBoxIds);
}


}
