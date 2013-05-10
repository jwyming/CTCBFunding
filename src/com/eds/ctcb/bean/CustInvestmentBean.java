package com.eds.ctcb.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustInvestmentBean implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	

	private String fundCode;

	private String fundName;

	private String investmentCurrency;
	private String cashCurrency;

	private BigDecimal totalCount;

	private BigDecimal  sellingEquity;
	private BigDecimal  sellingRate;
	

	private BigDecimal  totalInitCash;
	private BigDecimal  totalFundValue;
	
	

	private BigDecimal  currentComingSet;
	private BigDecimal  totalProfit;

	public CustInvestmentBean(){}
	public String getFundCode() {
		return fundCode;
	}

	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getCashCurrency() {
		return cashCurrency;
	}

	public void setCashCurrency(String cashCurrency) {
		this.cashCurrency = cashCurrency;
	}

	public BigDecimal getCurrentComingSet() {
		return currentComingSet;
	}

	public void setCurrentComingSet(BigDecimal currentComingSet) {
		this.currentComingSet = currentComingSet;
	}

	public String getInvestmentCurrency() {
		return investmentCurrency;
	}

	public void setInvestmentCurrency(String investmentCurrency) {
		this.investmentCurrency = investmentCurrency;
	}

	public BigDecimal getSellingEquity() {
		return sellingEquity;
	}

	public void setSellingEquity(BigDecimal sellingEquity) {
		this.sellingEquity = sellingEquity;
	}

	public BigDecimal getSellingRate() {
		return sellingRate;
	}

	public void setSellingRate(BigDecimal sellingRate) {
		this.sellingRate = sellingRate;
	}

	public BigDecimal getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(BigDecimal totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getTotalFundValue() {
		return totalFundValue;
	}

	public void setTotalFundValue(BigDecimal totalFundValue) {
		this.totalFundValue = totalFundValue;
	}

	public BigDecimal getTotalInitCash() {
		return totalInitCash;
	}

	public void setTotalInitCash(BigDecimal totalInitCash) {
		this.totalInitCash = totalInitCash;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}

	/**
	 * @param fundCode
	 * @param fundName
	 * @param investmentCurrency
	 * @param cashCurrency
	 * @param totalCount
	 * @param sellingEquity
	 * @param sellingRate
	 * @param totalInitCash
	 * @param totalFundValue
	 * @param currentComingSet
	 * @param totalProfit
	 */
	public CustInvestmentBean(String fundCode, String fundName, String investmentCurrency, String cashCurrency, BigDecimal totalCount, BigDecimal sellingEquity, BigDecimal sellingRate, BigDecimal totalInitCash, BigDecimal totalFundValue, BigDecimal currentComingSet, BigDecimal totalProfit) {
		super();
		this.fundCode = fundCode;
		this.fundName = fundName;
		this.investmentCurrency = investmentCurrency;
		this.cashCurrency = cashCurrency;
		this.totalCount = totalCount;
		this.sellingEquity = sellingEquity;
		this.sellingRate = sellingRate;
		this.totalInitCash = totalInitCash;
		this.totalFundValue = totalFundValue;
		this.currentComingSet = currentComingSet;
		this.totalProfit = totalProfit;
	}

	
	

}
