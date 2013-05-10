package com.eds.ctcb.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvestmentOrder implements Comparable,Serializable {

	private Integer order;

	private String userNickName;

	private BigDecimal cashAsset;

	private BigDecimal investmentAsset;

	private BigDecimal currentInvestmentAsset;

	private BigDecimal totalAsset;

	public InvestmentOrder() {
	}

	/**
	 * 
	 * @param userNickName
	 * @param cashAsset
	 * @param investmentAsset
	 * @param currentInvestmentAsset
	 * @param totalAsset
	 */
	public InvestmentOrder(String userNickName, BigDecimal cashAsset,
			BigDecimal investmentAsset, BigDecimal currentInvestmentAsset,
			BigDecimal totalAsset) {
		super();
		this.order = 0;
		this.userNickName = userNickName;
		this.cashAsset = cashAsset;
		this.investmentAsset = investmentAsset;
		this.currentInvestmentAsset = currentInvestmentAsset;
		this.totalAsset = totalAsset;
	}

	public BigDecimal getCashAsset() {
		return cashAsset;
	}

	public void setCashAsset(BigDecimal cashAsset) {
		this.cashAsset = cashAsset;
	}

	public BigDecimal getCurrentInvestmentAsset() {
		return currentInvestmentAsset;
	}

	public void setCurrentInvestmentAsset(BigDecimal currentInvestmentAsset) {
		this.currentInvestmentAsset = currentInvestmentAsset;
	}

	public BigDecimal getInvestmentAsset() {
		return investmentAsset;
	}

	public void setInvestmentAsset(BigDecimal investmentAsset) {
		this.investmentAsset = investmentAsset;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public BigDecimal getTotalAsset() {
		return totalAsset;
	}

	public void setTotalAsset(BigDecimal totalAsset) {
		this.totalAsset = totalAsset;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public int compareTo(Object o) {
		InvestmentOrder obj = (InvestmentOrder) o;
		if (this.totalAsset.compareTo(obj.getTotalAsset()) > 0) {
			return 1;
		} else if (this.totalAsset.compareTo(obj.getTotalAsset()) == 0) {
			if (this.currentInvestmentAsset.compareTo(obj
					.getCurrentInvestmentAsset()) > 0) {
				return 1;
			} else if (this.currentInvestmentAsset.compareTo(obj.getCurrentInvestmentAsset()) == 0) {
				if (this.investmentAsset.compareTo(obj
						.getInvestmentAsset()) > 0) {
					return 1;
				} else if (this.investmentAsset.compareTo(obj
						.getInvestmentAsset())== 0) {
					if(this.userNickName.compareToIgnoreCase(obj.getUserNickName())>0){
						return 1;
					}else if(this.userNickName.compareToIgnoreCase(obj.getUserNickName())==0){
						return 0;
					}else{
					    return -1;	
					}
					
				} else {
					return -1;
				}
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

}
