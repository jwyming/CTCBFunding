package com.eds.ctcb.form.deal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.eds.ctcb.biz.deal.SellTypeRadio;
import com.eds.ctcb.form.BaseForm;
import com.eds.ctcb.util.DataUtil;

public class ChangeFundForm extends BaseForm {
	private String outFundCode;
	private String inFundCode;
	private Long currency;
	private int changeFundRadio;
	private String allChangeAmount;
	private String partChangeAmount;
	private String investmentDate;

	
	


	public int getChangeFundRadio() {
		return changeFundRadio;
	}



	public void setChangeFundRadio(int changeFundRadio) {
		this.changeFundRadio = changeFundRadio;
	}


	public String getInFundCode() {
		return inFundCode;
	}



	public void setInFundCode(String inFundCode) {
		this.inFundCode = inFundCode;
	}



	public String getOutFundCode() {
		return outFundCode;
	}



	public void setOutFundCode(String outFundCode) {
		this.outFundCode = outFundCode;
	}





	public String getAllChangeAmount() {
		return allChangeAmount;
	}



	public void setAllChangeAmount(String allChangeAmount) {
		this.allChangeAmount = allChangeAmount;
	}



	public String getInvestmentDate() {
		return investmentDate;
	}



	public void setInvestmentDate(String investmentDate) {
		this.investmentDate = investmentDate;
	}



	public String getPartChangeAmount() {
		return partChangeAmount;
	}



	public void setPartChangeAmount(String partChangeAmount) {
		this.partChangeAmount = partChangeAmount;
	}



	public Long getCurrency() {
		return currency;
	}



	public void setCurrency(Long currency) {
		this.currency = currency;
	}




	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if(currency == null){
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.currency.isNull"));
		}
		if(changeFundRadio == SellTypeRadio.TWO && DataUtil.isStrEqual(partChangeAmount, null)){
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.partAmount.error"));
		}
		if(inFundCode.equals(outFundCode)){
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.ioFundSame"));
		}
			
	return errors;
	}



}
