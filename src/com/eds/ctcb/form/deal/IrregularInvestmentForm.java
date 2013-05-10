package com.eds.ctcb.form.deal;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.eds.ctcb.biz.deal.FundIdRadio;
import com.eds.ctcb.biz.deal.FundIncrementRadio;
import com.eds.ctcb.form.BaseForm;
import com.eds.ctcb.util.DataUtil;

public class IrregularInvestmentForm extends BaseForm {
	private int fundIdRadio;
	private Long fundTypeId;
	private Long fundCompanyId;
	private String fundCode1;
	private String fundCode2;
	private String fundCode3;
	private Long currencyId;
	private String monthlyAmount;
	private int fundIncrementRadio;
	private String incrementValue;
	private String incrementPercentage;
	private String handlingTariff;
	private int investmentDay;
	private String investmentWarningRate;
 
	
	public String getHandlingTariff() {
		return handlingTariff;
	}


	public void setHandlingTariff(String handlingTariff) {
		this.handlingTariff = handlingTariff;
	}


	public String getIncrementPercentage() {
		return incrementPercentage;
	}


	public void setIncrementPercentage(String incrementPercentage) {
		this.incrementPercentage = incrementPercentage;
	}


	public String getIncrementValue() {
		return incrementValue;
	}


	public void setIncrementValue(String incrementValue) {
		this.incrementValue = incrementValue;
	}


	public String getInvestmentWarningRate() {
		return investmentWarningRate;
	}


	public void setInvestmentWarningRate(String investmentWarningRate) {
		this.investmentWarningRate = investmentWarningRate;
	}


	public String getMonthlyAmount() {
		return monthlyAmount;
	}


	public void setMonthlyAmount(String monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}


	public Long getCurrencyId() {
		return currencyId;
	}


	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
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



	public Long getFundCompanyId() {
		return fundCompanyId;
	}


	public void setFundCompanyId(Long fundCompanyId) {
		this.fundCompanyId = fundCompanyId;
	}


	public Long getFundTypeId() {
		return fundTypeId;
	}


	public void setFundTypeId(Long fundTypeId) {
		this.fundTypeId = fundTypeId;
	}


	public void setFundTypeId(long fundTypeId) {
		this.fundTypeId = fundTypeId;
	}


	public int getFundIncrementRadio() {
		return fundIncrementRadio;
	}


	public void setFundIncrementRadio(int fundIncrementRadio) {
		this.fundIncrementRadio = fundIncrementRadio;
	}


	public int getInvestmentDay() {
		return investmentDay;
	}


	public void setInvestmentDay(int investmentDay) {
		this.investmentDay = investmentDay;
	}


	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		//check whether the fundCode is empty
		if(fundIdRadio == FundIdRadio.ONE && DataUtil.isStrEqual(fundCode1, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.fundCode"));
		} else if(fundIdRadio == FundIdRadio.TWO && DataUtil.isStrEqual(fundCode2, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.fundCode"));
		} else if(fundIdRadio == FundIdRadio.THREE && DataUtil.isStrEqual(fundCode3, null)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.fundCode"));
		}
		
		//check whether the currency is empty
		if(currencyId == null){
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.currency.isNull"));
		}
		
		//check whether the increment rate is over -100% 
        BigDecimal rate = null;
        if(fundIncrementRadio == FundIncrementRadio.ONE){
        	rate = BigDecimal.valueOf(Double.parseDouble(incrementValue)).divide(BigDecimal.valueOf(Double.parseDouble(monthlyAmount)), 10, BigDecimal.ROUND_HALF_UP);
        }else if (fundIncrementRadio == FundIncrementRadio.TWO){
        	rate = BigDecimal.valueOf(Double.parseDouble(incrementPercentage)).divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP);
        }
        if(rate.compareTo(BigDecimal.valueOf(-1)) < 0){
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.increment.overtop"));
        }

		return errors;
	}

}
