package com.eds.ctcb.form.deal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.eds.ctcb.biz.deal.FundIdRadio;
import com.eds.ctcb.form.BaseForm;
import com.eds.ctcb.util.DataUtil;

public class SingleInvestmentForm extends BaseForm {
	private int fundIdRadio;
	private String fundTypeId;
	private Long fundCompanyId;
	private String fundCode1;
	private String fundCode2;
	private String fundCode3;
	private Long currency;
	private String tradeAmount;
	private String handlingTarrif;
	private String investmentDate;
	private String investmentWarningRate;

	public String getHandlingTarrif() {
		return handlingTarrif;
	}

	public void setHandlingTarrif(String handlingTarrif) {
		this.handlingTarrif = handlingTarrif;
	}

	public String getInvestmentWarningRate() {
		return investmentWarningRate;
	}

	public void setInvestmentWarningRate(String investmentWarningRate) {
		this.investmentWarningRate = investmentWarningRate;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

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

	public String getInvestmentDate() {
		return investmentDate;
	}

	public void setInvestmentDate(String investmentDate) {
		this.investmentDate = investmentDate;
	}

	@Override
	public ActionErrors doValidate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if(fundIdRadio == FundIdRadio.ONE && DataUtil.isStrEqual(fundCode1, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.fundCode"));
		} else if(fundIdRadio == FundIdRadio.TWO && DataUtil.isStrEqual(fundCode2, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.fundCode"));
		} else if(fundIdRadio == FundIdRadio.THREE && DataUtil.isStrEqual(fundCode3, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.fundCode"));
		}
		if(currency == null){
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.currency.isNull"));
		}
		
		return errors;
	}
	
}
