package com.eds.ctcb.db;

// default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Account generated by MyEclipse - Hibernate Tools
 */

public class Account implements java.io.Serializable {

	// Fields

	private Long id;

	private User user;

	private Integer type;

	private Date createTime;

	
	private String remark;

	private CashAccount cashAccount;

	private Set<SavingPlan> savingPlans = new HashSet<SavingPlan>(0);

	private FundAccount fundAccount;

	// Constructors

	/** default constructor */
	public Account() {
	}

	/** minimal constructor */
	public Account(User user, Integer type, Date createTime) {
		this.user = user;
		this.type = type;
		this.createTime = createTime;
	}

	/** full constructor */
	public Account(User user, Integer type, Date createTime, String remark,
			CashAccount cashAccount, Set<SavingPlan> savingPlans, FundAccount fundAccount) {
		this.user = user;
		this.type = type;
		this.createTime = createTime;
		this.remark = remark;
		this.cashAccount = cashAccount;
		// this.tradesForDaccountid = tradesForDaccountid;
		// this.tradesForSaccountid = tradesForSaccountid;
		this.savingPlans = savingPlans;
		this.fundAccount = fundAccount;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public CashAccount getCashAccount() {
		return cashAccount;
	}

	public void setCashAccount(CashAccount cashAccount) {
		this.cashAccount = cashAccount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public FundAccount getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(FundAccount fundAccount) {
		this.fundAccount = fundAccount;
	}

	public Set<SavingPlan> getSavingPlans() {
		return savingPlans;
	}

	public void setSavingPlans(Set<SavingPlan> savingPlans) {
		this.savingPlans = savingPlans;
	}

	public String getRemark() {
		return remark;
	}

	

}