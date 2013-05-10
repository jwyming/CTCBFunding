package com.eds.ctcb.form.deal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.eds.ctcb.biz.deal.SellTypeRadio;
import com.eds.ctcb.form.BaseForm;
import com.eds.ctcb.util.DataUtil;

public class SellingForm extends BaseForm {

	private String sellFundCode;
	private int sellTypeRadio;
	private String partSellAmount;
	private String sellValue;
	private Long currency;
	private String investmentDate;


	public String getSellFundCode() {
		return sellFundCode;
	}


	public void setSellFundCode(String sellFundCode) {
		this.sellFundCode = sellFundCode;
	}


	public String getInvestmentDate() {
		return investmentDate;
	}


	public void setInvestmentDate(String investmentDate) {
		this.investmentDate = investmentDate;
	}


	public Long getCurrency() {
		return currency;
	}


	public void setCurrency(Long currency) {
		this.currency = currency;
	}


	public String getPartSellAmount() {
		return partSellAmount;
	}


	public void setPartSellAmount(String partSellAmount) {
		this.partSellAmount = partSellAmount;
	}


	public String getSellValue() {
		return sellValue;
	}


	public void setSellValue(String sellValue) {
		this.sellValue = sellValue;
	}


	public int getSellTypeRadio() {
		return sellTypeRadio;
	}


	public void setSellTypeRadio(int sellTypeRadio) {
		this.sellTypeRadio = sellTypeRadio;
	}






	public Long getCurrencyRadio() {
		return currency;
	}


	public void setCurrencyRadio(Long currency) {
		this.currency = currency;
	}

	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		//check whether the currency is empty
		if(currency == null){
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.currency.isNull"));
		}
		if(sellTypeRadio == SellTypeRadio.TWO && DataUtil.isStrEqual(partSellAmount, null)){
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.partAmount.error"));
		}else if (sellTypeRadio == SellTypeRadio.THREE && DataUtil.isStrEqual(sellValue, null))
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.sellValue.error"));
		
		return errors;
	}


}
