package com.eds.ctcb.form.deal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.form.BaseForm;

public class RegularInvestmentChangeForm extends BaseForm {
	private String fundName;
	private Integer investmentDate;
	private String investmentAmount;
	private Long savingPlanId;
	
	


	public Long getSavingPlanId() {
		return savingPlanId;
	}


	public void setSavingPlanId(Long savingPlanId) {
		this.savingPlanId = savingPlanId;
	}


	public Integer getInvestmentDate() {
		return investmentDate;
	}


	public void setInvestmentDate(Integer investmentDate) {
		this.investmentDate = investmentDate;
	}


	public String getFundName() {
		return fundName;
	}


	public void setFundName(String fundName) {
		this.fundName = fundName;
	}


	public String getInvestmentAmount() {
		return investmentAmount;
	}


	public void setInvestmentAmount(String investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
