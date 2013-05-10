package com.eds.ctcb.tag;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.jsp.JspException;

public class PercentFormatTag extends BaseTag {
	 private static final long serialVersionUID = 1L;
	 private String value;
	 public int doStartTag() throws JspException {
		    BigDecimal tempValue=BigDecimal.ZERO;
		  	
		  	try{
		  		tempValue=new BigDecimal(value);
		  		
		  	}catch(Exception e){
		  		//
		  	}
		  	
		  	BigDecimal result;
			result=tempValue.movePointRight(2);
			result=result.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			String s=result.toString()+"%";
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
