package com.eds.ctcb.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

public class PopSelectScriptTag extends BaseTag {
  private static final long serialVersionUID = 1L;
  private String scriptFuncName;
  private String popUrl;
  private String valueAssignTargets;

  public int doStartTag() throws JspException {
    StringBuffer sb = new StringBuffer();
    sb.append("<script type=\"text/javascript\">");
    sb.append("	function " + this.scriptFuncName + "() {");
    sb.append("		var returnValue = window.showModalDialog('" + this.popUrl + "','','scrollbars=yes;resizable=no;help=no;status=no;dialogWidth=800px;dialogHeight=600px');");
    String[] targetArray = valueAssignTargets.split(this.SEPARATOR);
    if (targetArray != null && targetArray.length > 0) {
      for (int i = 0; i < targetArray.length; i++) {
        sb.append("	document.getElementById('" + targetArray[i] + "').value=returnValue[" + i + "];");
      }
    }
    sb.append("	}");
    sb.append("</script>");
    try {
      pageContext.getOut().print(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return SKIP_BODY;

  }

  public String getPopUrl() {
    return popUrl;
  }

  public void setPopUrl(String popUrl) {
    this.popUrl = this.expressionHandle("popUrl", popUrl);
  }

  public String getScriptFuncName() {
    return scriptFuncName;
  }

  public void setScriptFuncName(String scriptFuncName) {
    this.scriptFuncName = this.expressionHandle("scriptFuncName", scriptFuncName);
  }

  public String getValueAssignTargets() {
    return valueAssignTargets;
  }

  public void setValueAssignTargets(String valueAssignTargets) {
    this.valueAssignTargets = this.expressionHandle("valueAssignTargets", valueAssignTargets);
  }


}
