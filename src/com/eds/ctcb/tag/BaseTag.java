package com.eds.ctcb.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import com.eds.ctcb.util.DataUtil;
public abstract class BaseTag extends TagSupport {
	protected final String SEPARATOR = ";"; 
	
	protected String upperCaseFirstLetter(String s){
		if(DataUtil.isEmptyStr(s)){
			return s;
		}
		
		String s1 = s.substring(0,1);
		s1 = s1.toUpperCase();
		String s2 = s.substring(1,s.length());
		s=s1+s2;
		return s;
	}
	
	
	
	
	protected String expressionHandle(String attributeName, String expression) {
		if (expression != null) {
			try {
				return (String) (ExpressionEvaluatorManager.evaluate(
						attributeName, expression.toString(), String.class,
						this, pageContext));
			} catch (JspException e) {
				return expression;
			}
		} else {
			return null;
		}
	}
	
	protected HttpServletRequest getRequest(){
		HttpServletRequest request = (HttpServletRequest)(this.pageContext.getRequest());
		return request;
	}

}
