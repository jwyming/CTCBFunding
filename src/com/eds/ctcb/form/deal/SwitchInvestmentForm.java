package com.eds.ctcb.form.deal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.eds.ctcb.biz.deal.FundIdRadio;
import com.eds.ctcb.biz.deal.SellTypeRadio;
import com.eds.ctcb.form.BaseForm;
import com.eds.ctcb.util.DataUtil;

public class SwitchInvestmentForm extends BaseForm {
	
	private String sellFundCode;
	private int fundIdRadio;
	private String fundTypeId;
	private Long fundCompanyId;
	private String fundCode1;
	private String fundCode2;
	private String fundCode3;
	private int sellTypeRadio;
	private String allSellAmount;
	private String partSellAmount;
	private String sellValue;
	private String investmentDate;
	private Long currency;
	
	
	public Long getFundCompanyId() {
		return fundCompanyId;
	}


	public void setFundCompanyId(Long fundCompanyId) {
		this.fundCompanyId = fundCompanyId;
	}


	public String getFundTypeId() {
		return fundTypeId;
	}


	public void setFundTypeId(String fundTypeId) {
		this.fundTypeId = fundTypeId;
	}


	public Long getCurrency() {
		return currency;
	}


	public void setCurrency(Long currency) {
		this.currency = currency;
	}


	public String getFundCode1() {
		return fundCode1;
	}


	public void setFundCode1(String fundCode1) {
		this.fundCode1 = fundCode1;
	}


	public String getFundCode2() {
		return fundCode2;
	}


	public void setFundCode2(String fundCode2) {
		this.fundCode2 = fundCode2;
	}


	public String getFundCode3() {
		return fundCode3;
	}


	public void setFundCode3(String fundCode3) {
		this.fundCode3 = fundCode3;
	}


	public int getFundIdRadio() {
		return fundIdRadio;
	}


	public void setFundIdRadio(int fundIdRadio) {
		this.fundIdRadio = fundIdRadio;
	}


	public String getSellFundCode() {
		return sellFundCode;
	}


	public void setSellFundCode(String sellFundCode) {
		this.sellFundCode = sellFundCode;
	}


	public int getSellTypeRadio() {
		return sellTypeRadio;
	}


	public void setSellTypeRadio(int sellTypeRadio) {
		this.sellTypeRadio = sellTypeRadio;
	}





	public String getAllSellAmount() {
		return allSellAmount;
	}


	public void setAllSellAmount(String allSellAmount) {
		this.allSellAmount = allSellAmount;
	}


	public String getInvestmentDate() {
		return investmentDate;
	}


	public void setInvestmentDate(String investmentDate) {
		this.investmentDate = investmentDate;
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


	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		String fundCode = null;
		//check whether the fundCode is null
		if(fundIdRadio == FundIdRadio.ONE && DataUtil.isStrEqual(fundCode1, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.inFund"));
		} else if(fundIdRadio == FundIdRadio.TWO && DataUtil.isStrEqual(fundCode2, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.inFund"));
		} else if(fundIdRadio == FundIdRadio.THREE && DataUtil.isStrEqual(fundCode3, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.fundCode"));
		}
		//check whether the inFund and outFund are the same
		if(fundIdRadio == FundIdRadio.ONE && !DataUtil.isStrEqual(fundCode1, null)) {
			fundCode = fundCode1;
		} else if(fundIdRadio == FundIdRadio.TWO && !DataUtil.isStrEqual(fundCode2, null)) {
			fundCode = fundCode2;
		} else if(fundIdRadio == FundIdRadio.THREE && !DataUtil.isStrEqual(fundCode3, null)) {
			fundCode = fundCode3;
		}
		if(fundCode != null && fundCode.equals(sellFundCode)){
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.ioFundSame"));
		}
		
		//check whether the currency is null
		if(currency == null){
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.currency.isNull"));
		}
		
		//check whether the sellType is null
		if(sellTypeRadio == SellTypeRadio.TWO && DataUtil.isStrEqual(partSellAmount, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.partAmount.error"));
		} else if(sellTypeRadio == SellTypeRadio.THREE && DataUtil.isStrEqual(sellValue, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.sellValue.error"));
		
		}
		
		return errors;
	}

}
