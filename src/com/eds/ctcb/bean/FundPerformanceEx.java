package com.eds.ctcb.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class FundPerformanceEx implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer index;

	private String fundName;

	private String fundCode;

	private String fundType;

	private String fundCompany;

	private String fundArea;

	private String fundIndustry;

	private BigDecimal oldEquity;

	private BigDecimal todayEquity;

	private String fundCurrency;

	private BigDecimal performanceValue;

	public FundPerformanceEx(Integer index, FundPerformance fundPerformance) {
		this.fundName = fundPerformance.getFundName();
		this.fundCode = fundPerformance.getFundCode();
		this.fundType = fundPerformance.getFundType();
		this.fundCompany = fundPerformance.getFundCompany();
		this.fundArea = fundPerformance.getFundArea();
		this.fundIndustry = fundPerformance.getFundIndustry();
		this.oldEquity = fundPerformance.getOldEquity();
		this.todayEquity = fundPerformance.getTodayEquity();
		this.fundCurrency = fundPerformance.getFundCurrency();
		this.performanceValue = fundPerformance.getPerformanceValue();
		this.index = index;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getFundArea() {
		return fundArea;
	}

	public void setFundArea(String fundArea) {
		this.fundArea = fundArea;
	}

	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getFundCompany() {
		return fundCompany;
	}

	public void setFundCompany(String fundCompany) {
		this.fundCompany = fundCompany;
	}

	public String getFundCurrency() {
		return fundCurrency;
	}

	public void setFundCurrency(String fundCurrency) {
		this.fundCurrency = fundCurrency;
	}

	public String getFundIndustry() {
		return fundIndustry;
	}

	public void setFundIndustry(String fundIndustry) {
		this.fundIndustry = fundIndustry;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public BigDecimal getOldEquity() {
		return oldEquity;
	}

	public void setOldEquity(BigDecimal oldEquity) {
		this.oldEquity = oldEquity;
	}

	public BigDecimal getPerformanceValue() {
		return performanceValue;
	}

	public void setPerformanceValue(BigDecimal performanceValue) {
		this.performanceValue = performanceValue;
	}

	public BigDecimal getTodayEquity() {
		return todayEquity;
	}

	public void setTodayEquity(BigDecimal todayEquity) {
		this.todayEquity = todayEquity;
	}
	public int compareTo(Object o) {
		FundPerformance obj = (FundPerformance) o;
		return this.performanceValue.compareTo(obj.getPerformanceValue());
	}

}
