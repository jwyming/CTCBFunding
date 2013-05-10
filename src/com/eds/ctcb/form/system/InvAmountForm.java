package com.eds.ctcb.form.system;



import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.form.BaseForm;

public class InvAmountForm extends BaseForm{
	
	private String amount;
	
	public InvAmountForm() {
		super();
		
	}
	public InvAmountForm(String amount) {
		super();
		this.amount = amount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}




	@Override
	public ActionErrors doValidate(ActionMapping mapping, HttpServletRequest request) {
		return null;
	}

}
