package com.eds.ctcb.form.report;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.form.BaseForm;

public class FundPerformanceForm extends BaseForm {

	private String fundType;

	private String fundArea;

	private String fundIndustry;

	private String timeRank;

	private String indexRank;

	private String currency;



	public String getCurrency() {
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public String getFundArea() {
		return fundArea;
	}



	public void setFundArea(String fundArea) {
		this.fundArea = fundArea;
	}



	public String getFundIndustry() {
		return fundIndustry;
	}



	public void setFundIndustry(String fundIndustry) {
		this.fundIndustry = fundIndustry;
	}



	public String getFundType() {
		return fundType;
	}



	public void setFundType(String fundType) {
		this.fundType = fundType;
	}



	public String getIndexRank() {
		return indexRank;
	}



	public void setIndexRank(String indexRank) {
		this.indexRank = indexRank;
	}



	public String getTimeRank() {
		return timeRank;
	}



	public void setTimeRank(String timeRank) {
		this.timeRank = timeRank;
	}



	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
