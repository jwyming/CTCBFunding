package com.eds.ctcb.db;

import java.math.BigDecimal;
// default package





public class FundAccount  implements java.io.Serializable {


    // Fields    

     private Long accountId;
     private Account account;
     private Fund fund;
     private BigDecimal count;
     private Float incomingSet;
     private BigDecimal initCount;


    // Constructors

    /** default constructor */
    public FundAccount() {
    }

	/** minimal constructor */
    public FundAccount(Account account, Fund fund, BigDecimal count, BigDecimal initCount) {
        this.account = account;
        this.fund = fund;
        this.count = count;
        this.initCount = initCount;
    }
    
    /** full constructor */
    public FundAccount(Account account, Fund fund, BigDecimal count, Float incomingSet,BigDecimal initCount) {
        this.account = account;
        this.fund = fund;
        this.count = count;
        this.incomingSet = incomingSet;
        this.initCount = initCount;
    }

   
    // Property accessors

   
    public Account getAccount() {
        return this.account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }

    public Fund getFund() {
        return this.fund;
    }
    
    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public BigDecimal getCount() {
        return this.count;
    }
    
    public void setCount(BigDecimal count) {
        this.count = count;
    }

	public Long getAccountId() {
		return accountId;
	}

	private void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Float getIncomingSet() {
		return incomingSet;
	}

	public void setIncomingSet(Float incomingSet) {
		this.incomingSet = incomingSet;
	}

	public BigDecimal getInitCount() {
		return initCount;
	}

	public void setInitCount(BigDecimal initCount) {
		this.initCount = initCount;
	}

   
   








}