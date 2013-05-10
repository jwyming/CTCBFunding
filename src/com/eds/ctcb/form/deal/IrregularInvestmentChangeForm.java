
package com.eds.ctcb.form.deal;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.eds.ctcb.biz.deal.FundIncrementRadio;
import com.eds.ctcb.form.BaseForm;

public class IrregularInvestmentChangeForm extends BaseForm {
	private String fundName;
	private int investmentDate;
	private String investmentAmount;
	private int incrementRadio;
	private String incrementValue;
	private String incrementPercentage;
	private Long savingPlanId;
	

	public Long getSavingPlanId() {
		return savingPlanId;
	}


	public void setSavingPlanId(Long savingPlanId) {
		this.savingPlanId = savingPlanId;
	}


	public String getFundName() {
		return fundName;
	}


	public void setFundName(String fundName) {
		this.fundName = fundName;
	}




	public int getIncrementRadio() {
		return incrementRadio;
	}


	public void setIncrementRadio(int incrementRadio) {
		this.incrementRadio = incrementRadio;
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


	public String getInvestmentAmount() {
		return investmentAmount;
	}


	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}



	public int getInvestmentDate() {
		return investmentDate;
	}


	public void setInvestmentDate(int investmentDate) {
		this.investmentDate = investmentDate;
	}




	@Override
	public ActionErrors doValidate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
//		check whether the increment rate is over -100% 
        BigDecimal rate = new BigDecimal(0);
        if(incrementRadio == FundIncrementRadio.ONE){
        	rate = BigDecimal.valueOf(Double.parseDouble(incrementValue)).divide(BigDecimal.valueOf(Double.parseDouble(investmentAmount)), 10, BigDecimal.ROUND_HALF_UP);
        }else if (incrementRadio == FundIncrementRadio.TWO){
        	rate = BigDecimal.valueOf(Float.parseFloat(incrementPercentage)).divide(BigDecimal.valueOf(100), 10, BigDecimal.ROUND_HALF_UP);
        }
        if(rate.compareTo(BigDecimal.valueOf(-1)) < 0){
        	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.increment.overtop"));
        }
		return errors;
		
		
		
	}



}
