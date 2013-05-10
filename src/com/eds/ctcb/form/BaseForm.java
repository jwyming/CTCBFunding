package com.eds.ctcb.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

import com.eds.ctcb.util.DataUtil;

public abstract class BaseForm extends ValidatorActionForm {	
	private static final long serialVersionUID = 1L;
	
	public final ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		request.getSession().setAttribute(this.getClass().getSimpleName(), this);
		return this.doValidate(mapping,request);
	}
	
	public abstract ActionErrors doValidate(ActionMapping mapping, HttpServletRequest request);
	
	public String toString(){
		return DataUtil.bean2String(this);
	}
}
