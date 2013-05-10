package com.eds.ctcb.form.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.form.BaseForm;

public class MiscSettingForm extends BaseForm {
	
	private String minAmt;
	private String minUnit;
	
	
	
	

	public MiscSettingForm() {

	}



	public MiscSettingForm(String minAmt, String minUnit) {
		this.minAmt = minAmt;
		this.minUnit = minUnit;
	}



	public String getMinAmt() {
		return minAmt;
	}



	public void setMinAmt(String minAmt) {
		this.minAmt = minAmt;
	}



	public String getMinUnit() {
		return minUnit;
	}



	public void setMinUnit(String minUnit) {
		this.minUnit = minUnit;
	}



	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
