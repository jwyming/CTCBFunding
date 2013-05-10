package com.eds.ctcb.form.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.eds.ctcb.form.BaseForm;

public class TransferTariffForm extends BaseForm{
	
	private String min;
	private String max;
	private String rate;
	

	public TransferTariffForm() {

	}
	
	public TransferTariffForm(String min, String max, String rate) {
		this.min = min;
		this.max = max;
		this.rate = rate;
	}


	public String getMax() {
		return max;
	}


	public void setMax(String max) {
		this.max = max;
	}


	public String getMin() {
		return min;
	}


	public void setMin(String min) {
		this.min = min;
	}


	public String getRate() {
		return rate;
	}


	public void setRate(String rate) {
		this.rate = rate;
	}


	@Override
	public ActionErrors doValidate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors actionErrors = new ActionErrors();
		if(Float.parseFloat(min)>Float.parseFloat(max)){
			actionErrors.add("transferTariffError",new ActionMessage("sysparam.error.transferTariff"));
		}
		return actionErrors;
	}

}
