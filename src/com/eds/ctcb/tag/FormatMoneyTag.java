package com.eds.ctcb.tag;

import java.io.IOException;
import java.text.NumberFormat;

import javax.servlet.jsp.JspException;
public class FormatMoneyTag extends BaseTag {
  private static final long serialVersionUID = 1L;
  private String value;

  public int doStartTag() throws JspException {
	  
	  	double d = 0;
	  	try{
	  		d = Double.parseDouble(this.value); 
	  	}catch(Exception e){
	  		//
	  	}
		NumberFormat nf = NumberFormat.getInstance();		
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(true);
		String s = nf.format(d);
	    try {
	      pageContext.getOut().print(s);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return SKIP_BODY;

  }

public String getValue() {
	return value;
}

public void setValue(String value) {
	this.value = this.expressionHandle("value", value);
}








}
