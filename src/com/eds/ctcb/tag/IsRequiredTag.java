package com.eds.ctcb.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
public class IsRequiredTag extends BaseTag {
  private static final long serialVersionUID = 1L;

  public int doStartTag() throws JspException {    
    try {
      pageContext.getOut().print("<FONT class=\"isRequired\">*</FONT>");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return SKIP_BODY;

  }








}
