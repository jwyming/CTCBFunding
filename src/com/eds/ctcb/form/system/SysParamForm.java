package com.eds.ctcb.form.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.eds.ctcb.form.BaseForm;

public class SysParamForm extends BaseForm{
	  private Long id;
	  private String value;
	  private String name;
	  private String remark;
	  
	public SysParamForm() {
		
	}
	
	

	public SysParamForm(String value, String name, String remark) {
		this.value = value;
		this.name = name;
		this.remark = remark;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	@Override
	public ActionErrors doValidate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors actionErrors = new ActionErrors();
		if(value.length()<6||value.length()>20){
			actionErrors.add("length",new ActionMessage("sysParam.error.length"));
		}
		return actionErrors;
	}
}
