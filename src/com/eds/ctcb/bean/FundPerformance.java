package com.eds.ctcb.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class FundPerformance implements Comparable,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public BigDecimal getOldEquity() {
		return oldEquity;
	}

	public void setOldEquity(BigDecimal oldEquity) {
		this.oldEquity = oldEquity;
	}

	public BigDecimal getTodayEquity() {
		return todayEquity;
	}

	public void setTodayEquity(BigDecimal todayEquity) {
		this.todayEquity = todayEquity;
	}

	/**
	 * @param fundId
	 * @param oldEquity
	 * @param todayEquity
	 * @param performanceValue
	 */

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

	/**
	 * @param fundName
	 * @param fundCode
	 * @param fundType
	 * @param fundCompany
	 * @param fundArea
	 * @param fundIndustry
	 * @param oldEquity
	 * @param todayEquity
	 * @param fundCurrency
	 * @param performanceValue
	 */
	public FundPerformance(String fundName, String fundCode, String fundType,
			String fundCompany, String fundArea, String fundIndustry,
			BigDecimal oldEquity, BigDecimal todayEquity, String fundCurrency,
			BigDecimal performanceValue) {
		super();
		this.fundName = fundName;
		this.fundCode = fundCode;
		this.fundType = fundType;
		this.fundCompany = fundCompany;
		this.fundArea = fundArea;
		this.fundIndustry = fundIndustry;
		this.oldEquity = oldEquity;
		this.todayEquity = todayEquity;
		this.fundCurrency = fundCurrency;
		this.performanceValue = performanceValue;
	}

	public BigDecimal getPerformanceValue() {
		return performanceValue;
	}

	public void setPerformanceValue(BigDecimal performanceValue) {
		this.performanceValue = performanceValue;
	}

	public int compareTo(Object o) {
		FundPerformance obj = (FundPerformance) o;
		if(this.performanceValue.compareTo(obj.getPerformanceValue())>0){
			return 1;
		} else if(this.performanceValue.compareTo(obj.getPerformanceValue())==0){
			if(this.todayEquity.compareTo(obj.getTodayEquity())>0){
				return 1;
			}else if(this.todayEquity.compareTo(obj.getTodayEquity())==0){
				 if(this.fundCode.compareTo(obj.getFundCode())>0){
					return 1;
				    }else if(this.fundCode.compareTo(obj.getFundCode())==0){
					return 0;
				   }else{
					return -1;
				   }
				
			}else{
				return -1;
			}
					
		}else{
			return -1;
		}
		
	}

	@Override
	public boolean equals(Object obj) {
		FundPerformance temp=(FundPerformance)obj;
		if(this.fundCode.equals(temp.getFundCode())&&this.performanceValue==temp.getPerformanceValue()){
			return true;
		}else{
			return false;
		}
		
	}
	
	

}
